package io.teachmeskills.presentation.view.editexpense

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import io.teachmeskills.an03onl_accountingoffinancesapp.R
import io.teachmeskills.an03onl_accountingoffinancesapp.databinding.FragmentEditExpenseBinding
import io.teachmeskills.data.database.entity.ExpenseEntity
import io.teachmeskills.presentation.view.addexpense.AddExpenseFragment
import io.teachmeskills.utils.Constants
import io.teachmeskills.presentation.viewmodel.ExpenseViewModel
import kotlinx.android.synthetic.main.fragment_edit_expense.view.currency
import kotlinx.android.synthetic.main.fragment_edit_expense.view.et_amount
import kotlinx.android.synthetic.main.fragment_edit_expense.view.et_notes
import kotlinx.android.synthetic.main.fragment_edit_expense.view.et_title
import kotlinx.android.synthetic.main.fragment_edit_expense.view.tv_date
import kotlinx.android.synthetic.main.fragment_edit_expense.view.tv_tag_expense
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*


class EditExpenseFragment : Fragment() {

    private lateinit var binding: FragmentEditExpenseBinding
//    private val viewModel: EditExpenseViewModel by viewModel()
    private val viewModel: ExpenseViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditExpenseBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private var expenseEntity: ExpenseEntity? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        expenseEntity = arguments?.getParcelable<ExpenseEntity>("expense")
        initViews()
        loadData(expenseEntity!!)


    }

    private fun loadData(expenseEntity: ExpenseEntity) = with(binding) {
        etTitle.setText(expenseEntity.title)
        etAmount.setText(expenseEntity.amount.toString())
        currency.setText(expenseEntity.currency, false)
        tvTagExpense.setText(expenseEntity.tag, false)
        tvDate.setText(expenseEntity.date)
        etNotes.setText(expenseEntity.note)
    }

    private fun initViews() {
        setCurrency()
        setTagExpense()
        selectedDate()


        with(binding) {

            btnSaveExpense.setOnClickListener {
                binding.layoutContainer.apply {
                    val (title, currency, amount, tag, date, note)
                    = getExpenseContent()

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
                            viewModel.updateExpense(getExpenseContent()).also {
                                Toast.makeText(
                                    requireContext(),
                                    getString(R.string.success_expense_update),
                                    Toast.LENGTH_LONG
                                ).show().also { findNavController().popBackStack() }
                            }
                        }
                    }
                }
            }

        }
    }

    private fun getExpenseContent(): ExpenseEntity = binding.layoutContainer.let {
        val id = expenseEntity!!.id
        val title = it.et_title.text.toString()
        val amount = it.et_amount.text.toString().toDouble()
        val currency = it.currency.text.toString()
        val tag = it.tv_tag_expense.text.toString()
        val date = it.tv_date.text.toString()
        val note = it.et_notes.text.toString()

        return ExpenseEntity(

            title = title,
            currency = currency,
            amount = amount,
            tag = tag,
            date = date,
            note = note,
            id = id
        )
    }

    private fun selectedDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        binding.tvDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { view, year, month, dayOfMonth ->

                    val _calendar = Calendar.getInstance()
                    _calendar.set(Calendar.YEAR, year)
                    _calendar.set(Calendar.MONTH, month)
                    _calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    Log.e("Date ", "date: ${AddExpenseFragment.dateFormatter.format(calendar.time)}")
                    binding.tvDate.text = AddExpenseFragment.dateFormatter.format(calendar.time)
                    Log.e("Date ", "date: ${binding.tvDate.text}")
                }, year, month, dayOfMonth
            )
            datePickerDialog.show()
        }
    }

    private fun setCurrency() {
        val currencies = resources.getStringArray(R.array.currency)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_curr, currencies)
        binding.currency.setAdapter(arrayAdapter)
    }

    private fun setTagExpense() {
        val tags = Constants.transactionTags
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_tag1, tags)
        binding.tvTagExpense.setAdapter(arrayAdapter)
    }

}
