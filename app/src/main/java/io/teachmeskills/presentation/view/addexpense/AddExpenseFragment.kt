package io.teachmeskills.presentation.view.addexpense

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import io.teachmeskills.an03onl_accountingoffinancesapp.R
import io.teachmeskills.an03onl_accountingoffinancesapp.databinding.FragmentAddExpenseBinding
import io.teachmeskills.data.database.entity.Expense
import io.teachmeskills.presentation.viewmodel.addexpense.AddExpenseFragmentViewModel
import io.teachmeskills.utils.Constants
import kotlinx.android.synthetic.main.fragment_add_expense.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class AddExpenseFragment : Fragment() {

    private lateinit var binding: FragmentAddExpenseBinding
    private val viewModel: AddExpenseFragmentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddExpenseBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        exitFragment()

    }

    private fun initViews() {
        setCurrency()
        setTagExpense()
        selectedDate()

        with(binding) {

            btnSaveExpense.setOnClickListener {
                binding.layoutContainer.apply {
                    val (title, currency, amount, tag, date, note) = getExpenseContent()

                    when {
                        title.isEmpty() -> {
                            this.et_title.error = "Title must note be empty"
                        }
                        currency.isEmpty() -> {
                            this.currency.error = "Currency must note be empty"
                        }
                        amount.isNaN() -> {
                            this.et_amount.error = "Amount must note be empty"
                        }
                        tag.isEmpty() -> {
                            this.tv_tag_expense.error = "Tag must note be empty"
                        }
                        date.isEmpty() -> {
                            this.tv_date.error = "Date must note be empty"
                        }
                        note.isEmpty() -> {
                            this.et_notes.error = "Note must note be empty"
                        }
                        else -> {
                            viewModel.addExpenseToDatabase(getExpenseContent()).run {
                                Toast.makeText(
                                    requireContext(),
                                    getString(R.string.success_expense_saved),
                                    Toast.LENGTH_LONG
                                ).show()
                                findNavController().navigate(R.id.action_addExpenseFragment_to_mainFragment)
                            }
                        }
                    }
                }
            }

        }


    }

    private fun getExpenseContent(): Expense = binding.layoutContainer.let {
        val title = it.et_title.text.toString()
        val amount = it.et_amount.text.toString().toDouble()
        val currency = it.currency.text.toString()
        val tag = it.tv_tag_expense.text.toString()
        val date = it.tv_date.text.toString()
        val note = it.et_notes.text.toString()

        return Expense(title, currency, amount, tag, date, note)
    }

    fun selectedDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        binding.tvDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { view, year, month, dayOfMonth ->
                    binding.tvDate.setText(" " + dayOfMonth + " / " + month + "/ " + year)

                }, year, month, dayOfMonth
            )
            datePickerDialog.show()
        }
    }

    fun setCurrency() {
        val currencies = resources.getStringArray(R.array.currency)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_curr, currencies)
        binding.currency.setAdapter(arrayAdapter)
    }

    fun setTagExpense() {
        val tags = Constants.transactionTags
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_tag1, tags)
        binding.tvTagExpense.setAdapter(arrayAdapter)
    }

    fun exitFragment() {
        binding.toolbar.btn_exit_add_expense.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }

}