package io.teachmeskills.presentation.view.detailexpens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import io.teachmeskills.an03onl_accountingoffinancesapp.R
import io.teachmeskills.an03onl_accountingoffinancesapp.databinding.FragmentDetailExpenseBinding
import io.teachmeskills.data.database.entity.ExpenseEntity
import io.teachmeskills.utils.DetailState
import io.teachmeskills.presentation.viewmodel.ExpenseViewModel
import kotlinx.android.synthetic.main.fragment_detail_expense.view.*
import kotlinx.coroutines.flow.collect
import org.koin.android.viewmodel.ext.android.viewModel

class DetailExpenseFragment : Fragment() {

    private lateinit var binding: FragmentDetailExpenseBinding
    //    private val viewModel: DetailExpenseFragmentViewModel by viewModel()
    private val viewModel: ExpenseViewModel by viewModel()
    private var expenseEntity: ExpenseEntity? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailExpenseBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        expenseEntity = arguments?.getParcelable<ExpenseEntity>("Key")
        getExpense(expenseEntity!!.id)
        observeExpense()
        deleteExpense(expenseEntity!!)

//        setFragmentResultListener("") { key, bundle ->
//
//        }
//        setFragmentResult("requestKey", bundleOf("bundleKey" to "bundle"))
    }

    private fun getExpense(id: Int) {
        viewModel.getByID(id)
    }

    private fun observeExpense() = lifecycleScope.launchWhenCreated {
        viewModel.detailState.collect { detailState ->

            when (detailState) {
                DetailState.Loading -> {
                }
                is DetailState.Success -> {
                    onDetailsLoader(detailState.expense)
                }
                is DetailState.Error -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.error),
                        Toast.LENGTH_LONG
                    ).show()
                }
                DetailState.Empty -> {
                    findNavController().navigateUp()
                }
            }
        }
    }


    private fun onDetailsLoader(expenseEntity: ExpenseEntity) = with(binding.transactionDetails) {
        tv_title.text = expenseEntity.title
        tv_amount.text = expenseEntity.amount.toString()
        tv_currency.text = expenseEntity.currency
        tv_date.text = expenseEntity.date
        tv_tag.text = expenseEntity.tag
        tv_notes.text = expenseEntity.note

        binding.btnEditExpense.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelable("expense", expenseEntity)
            }
            findNavController().navigate(
                R.id.action_detailExpenseFragment_to_editExpenseFragment, bundle
            )
        }
    }

    private fun deleteExpense(expenseEntity: ExpenseEntity) {
        binding.btnDeleteExpense.setOnClickListener {
            deleteSelected(expenseEntity)
            findNavController().navigate(R.id.action_detailExpenseFragment_to_mainFragment)

        }
    }

    private fun deleteSelected(expenseEntity: ExpenseEntity) : Boolean {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(R.string.delete_expense_message)
            .setPositiveButton(R.string.yes) {_, _ -> viewModel.deleteExpense(expenseEntity)}
            .setNegativeButton(R.string.no) {_, _ -> }
            .show()
        return true
    }
}