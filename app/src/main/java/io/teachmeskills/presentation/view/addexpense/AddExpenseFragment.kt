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
import androidx.navigation.fragment.findNavController
import io.teachmeskills.an03onl_accountingoffinancesapp.R
import io.teachmeskills.an03onl_accountingoffinancesapp.databinding.FragmentAddExpenseBinding
import io.teachmeskills.data.database.entity.ExpenseEntity
import io.teachmeskills.utils.Constants
import io.teachmeskills.presentation.viewmodel.ExpenseViewModel
import kotlinx.android.synthetic.main.fragment_add_expense.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class AddExpenseFragment : Fragment() {

    private lateinit var binding: FragmentAddExpenseBinding
    private val viewModel: ExpenseViewModel by viewModel()

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
                binding.apply {
                    val (title, currency, amount, tag, date, note) = getExpenseContent()
                    when {
                        title.isEmpty() -> {
                            this.etTitle.error = "Title must note be empty"

                        }
                        currency.isEmpty() -> {
                            this.currency.error = "Currency must note be empty"
                        }
                        amount.isNaN() -> {
                            this.etAmount.error = "Amount must note be empty"
                        }
                        tag.isEmpty() -> {
                            this.tvTagExpense.error = "Tag must note be empty"
                        }
                        date.isEmpty() -> {
                            this.tvDate.error = "Date must note be empty"
                        }
                        note.isEmpty() -> {
                            this.etNotes.error = "Note must note be empty"
                        }
                        else -> {
                            viewModel.insertExpense(getExpenseContent()).run {
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

    private fun getExpenseContent(): ExpenseEntity = binding.let {
        val title = it.etTitle.text.toString()
        val currency = it.currency.text.toString()
        val amount = parseDouble(it.etAmount.text.toString())
        val tag = it.tvTagExpense.text.toString()
        val date = it.tvDate.text.toString()
        val note = it.etNotes.text.toString()

        return ExpenseEntity(title, currency, amount, tag, date, note)
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

                    binding.tvDate.text = dateFormatter.format(calendar.time)

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

    private fun exitFragment() {
        binding.toolbar.btn_exit_add_expense.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }

    companion object {
        val dateFormatter = SimpleDateFormat("EEE, MMM d, ''yy", Locale.getDefault())

        private fun parseDouble(value: String?): Double {
            return if (value == null || value.isEmpty()) Double.NaN else value.toDouble()
        }

    }

    // option 1
    // val date = ...
    // date.day
    // date.month
    // val current_date = ...
    // if()

    // option 2
    // val millis = ...
    // val millis_day_start = ...
    // val millis_day_end = ...
    // if (millis > millis_day_start && millis < millis_day_end) -> true
}