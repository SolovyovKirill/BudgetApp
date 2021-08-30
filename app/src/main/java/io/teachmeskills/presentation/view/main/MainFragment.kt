package io.teachmeskills.presentation.view.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import io.teachmeskills.an03onl_accountingoffinancesapp.R
import io.teachmeskills.an03onl_accountingoffinancesapp.databinding.FragmentMainBinding
import io.teachmeskills.data.database.entity.ExpenseEntity
import io.teachmeskills.presentation.viewmodel.ExpenseViewModel
import kotlinx.android.synthetic.main.fragment_add_expense.*
import kotlinx.android.synthetic.main.item_currency_summary.view.*
import kotlinx.android.synthetic.main.item_expense.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import kotlin.time.days


class MainFragment : Fragment(), OnItemClickListener {

    private lateinit var binding: FragmentMainBinding
    private lateinit var expenseAdapter: ExpenseAdapter
    private lateinit var totalExpenseAdapter: TotalExpenseAdapter
    private val viewModel: ExpenseViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spinner()
        swipeToDelete()
        setupRV()
        setupRVTotal()
        observeExpense()
        goToNewExpenseFragment()
        goToSettingFragment()
        observeTotalExpense()
        observeByDateAdd()
    }

    //тут я должен обновить totalExpenseAdapter и expenseAdapter?
    private fun observeByDateAdd() = viewModel.filteredListLiveData.observe(this.viewLifecycleOwner, Observer {

//        val newExpensesList = newListExpenseEntity(it)
//        totalExpenseAdapter.submitList(newExpensesList)

    })


    private fun observeTotalExpense() =
        viewModel.expensesListLiveData.observe(this.viewLifecycleOwner, Observer {
            val newExpensesList = newListExpenseEntity(it)
            totalExpenseAdapter.submitList(newExpensesList)
        })

    private fun observeExpense() =
        viewModel.expensesListLiveData.observe(this.viewLifecycleOwner, Observer {
            expenseAdapter.submitList(it)
        })

    private fun newListExpenseEntity(expenses: List<ExpenseEntity>): List<ExpenseSum> {
        val newExpenses = mutableListOf<ExpenseSum>()
        val allCurrency = mutableListOf<String>()
        var sumAmount = 0.0
        expenses.forEach {
            if (allCurrency.isEmpty()) {
                allCurrency.add(it.currency)
            } else {
                var newCurrency = ""
                allCurrency.forEach { currency ->
                    if (it.currency != currency) {
                        newCurrency = it.currency
                        return@forEach
                    }
                }
                if (newCurrency.isNotEmpty())
                    allCurrency.add(newCurrency)
            }
        }
        allCurrency.forEach { currency ->
            expenses.forEach {
                if (it.currency == currency)
                    sumAmount += it.amount
            }
            newExpenses.add(ExpenseSum(sumAmount, currency))
            sumAmount = 0.0
        }
        return newExpenses
    }

    private fun setupRVTotal() = with(binding) {
        totalExpenseAdapter = TotalExpenseAdapter()
        containerTotalExpense.recyclerViewAmountAndCurrency.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        containerTotalExpense.recyclerViewAmountAndCurrency.adapter = totalExpenseAdapter
    }

    private fun setupRV() = with(binding) {
        expenseAdapter = ExpenseAdapter(this@MainFragment)
        recyclerView.apply {
            adapter = expenseAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun goToNewExpenseFragment() {
        binding.btnNewExpense.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addExpenseFragment)
        }
    }

    private fun goToSettingFragment() {
        binding.btnNotification.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_notificationFragment)
        }
    }

    override fun onClick(expenseEntity: ExpenseEntity) {
        val bundle = Bundle()
        bundle.putParcelable("Key", expenseEntity)
        findNavController().navigate(R.id.action_mainFragment_to_detailExpenseFragment, bundle)
    }

    private fun swipeToDelete() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // get item position & delete notes
                val position = viewHolder.adapterPosition
                val expense = expenseAdapter.currentList[position]
                val expenseItem = ExpenseEntity(
                    expense.title,
                    expense.currency,
                    expense.amount,
                    expense.tag,
                    expense.date,
                    expense.note,
                    expense.created,
                    expense.id
                )
                viewModel.deleteExpense(expenseItem)
                Snackbar.make(
                    binding.root,
                    getString(R.string.success_expense_delete),
                    Snackbar.LENGTH_LONG
                )
                    .apply {
                        setAction("Undo") {
                            viewModel.insertExpense(
                                expenseItem
                            )
                        }
                        show()
                    }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.recyclerView)
        }
    }
    private fun spinner() {
        val spinner: Spinner = binding.containerTotalExpense.dateSpinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.dateRange,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    lifecycleScope.launchWhenStarted {
                        val c = Calendar.getInstance()
                        val startOfDay = c.timeInMillis
                        when (position) {
                            0 -> {
                                // 1. Начало текущего дня в мс
                                // 2. Конец текущего дня (проще + 24ч в мс) в мс
                                val stopOfDay = startOfDay + DAY
                                viewModel.filterExpensesList(startOfDay, stopOfDay)

                                Toast.makeText(
                                    requireContext(),
                                    getString(R.string.today),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            1 -> {
                                // 1. Начало текущего дня в мс
                                // 2. + количественно 7 дней (+ 7*DAY) в мс
                                val stopOfDay = startOfDay + WEEK
                                viewModel.filterExpensesList(startOfDay, stopOfDay)
                                Toast.makeText(
                                    requireContext(),
                                    getString(R.string.this_week),
                                    Toast.LENGTH_LONG
                                ).show()


                            }
                            2 -> {
                                val stopOfDay = startOfDay + MONTH
                                viewModel.filterExpensesList(startOfDay, stopOfDay)
                                Toast.makeText(
                                    requireContext(),
                                    getString(R.string.this_month),
                                    Toast.LENGTH_LONG
                                ).show()


                            }
                            3 -> {
                                viewModel.getExpenseList()


                            }
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    lifecycleScope.launchWhenStarted {
                        viewModel.getExpenseList()
                    }
                }
            }
        }
    }


    companion object {
        const val DAY = 86400000L
        const val WEEK = 604800000L
        const val MONTH = 2592000000L
    }

}