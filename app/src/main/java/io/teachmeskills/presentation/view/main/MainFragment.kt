package io.teachmeskills.presentation.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import io.teachmeskills.an03onl_accountingoffinancesapp.R
import io.teachmeskills.an03onl_accountingoffinancesapp.databinding.FragmentMainBinding
import io.teachmeskills.data.database.entity.ExpenseEntity
import io.teachmeskills.presentation.viewmodel.ExpenseViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class MainFragment : Fragment(), OnItemClickListener {

    private lateinit var binding: FragmentMainBinding
    private lateinit var expenseAdapter: ExpenseAdapter
//    private val viewModel: MainFragmentViewModel by viewModel()
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

        swipeToDelete()
        setupRV()
        observeExpense()
        goToNewExpenseFragment()
        goToSettingFragment()
    }


    private fun observeExpense() = viewModel.expensesListLiveData.observe(this.viewLifecycleOwner, Observer {
        onTotalExpenseLoaded(it)
        expenseAdapter.submitList(it)

    })

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
        binding.btnSetting.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_settingFragment)
        }
    }

    override fun onClick(expenseEntity: ExpenseEntity) {
        val bundle = Bundle()
        bundle.putParcelable("Key", expenseEntity)
        findNavController().navigate(R.id.action_mainFragment_to_detailExpenseFragment, bundle)
    }

    private fun onTotalExpenseLoaded(expense: List<ExpenseEntity>) = with(binding) {

        val amountTotal = expense.sumOf { it.amount }
        containerTotalExpense.rvTotalExpense.textAmount.text = " ".plus(amountTotal)
        containerTotalExpense.rvTotalExpense.textCurrency.text = "USD"

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



}