package com.example.budgetmanagerapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.budgetmanagerapp.databinding.ActivityBudgetBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BudgetActivity : AppCompatActivity() {

    private lateinit var deletedTransaction: Transaction
    private lateinit var binding: ActivityBudgetBinding
    private lateinit var transactions: List<Transaction>
    private lateinit var oldTransactions: List<Transaction>
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var linearlayoutManager: LinearLayoutManager
    private lateinit var db : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBudgetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        transactions = arrayListOf()

        transactionAdapter = TransactionAdapter(transactions)
        linearlayoutManager = LinearLayoutManager(this)

        db = Room.databaseBuilder(this, AppDatabase::class.java, "transactions").build()

        binding.recyclerview.apply {
            adapter = transactionAdapter
            layoutManager = linearlayoutManager
        }

        //swipe to remove(Item Touch Helper)
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteTransaction(transactions[viewHolder.adapterPosition])
            }

        }

        val swipeHelper = ItemTouchHelper(itemTouchHelper)
        swipeHelper.attachToRecyclerView(binding.recyclerview)

        binding.addBtn.setOnClickListener {
            val intent = Intent(this, AddTransactionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchAll(){
       GlobalScope.launch {
           transactions = db.transactionDao().getAll()

           runOnUiThread {
               updateDashboard()
               transactionAdapter.setData(transactions)
           }
       }
    }

    private fun updateDashboard(){
        val totalAmount = transactions.map{ it.amount }.sum()
        val budgetAmount = transactions.filter { it.amount>0 }.map { it.amount }.sum()
        val expense = totalAmount - budgetAmount

        binding.balance.text = "Rs %.2f".format(totalAmount)
        binding.budget.text = "Rs %.2f".format(budgetAmount)
        binding.expense.text = "Rs %.2f".format(expense)
    }


    private fun deleteTransaction(transaction: Transaction){
        deletedTransaction = transaction
        oldTransactions = transactions

        GlobalScope.launch {
            db.transactionDao().delete(transaction)

            transactions = transactions.filter { it.id != transaction.id }
            runOnUiThread {
                updateDashboard()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        fetchAll()
    }
}