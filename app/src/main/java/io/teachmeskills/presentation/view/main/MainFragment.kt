package io.teachmeskills.presentation.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import io.teachmeskills.an03onl_accountingoffinancesapp.R
import io.teachmeskills.an03onl_accountingoffinancesapp.databinding.FragmentMainBinding
import io.teachmeskills.presentation.viewmodel.main.MainFragmentViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private val viewModel: MainFragmentViewModel by viewModel()

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

        val expenseAdapter = ExpenseAdapter()

        binding.recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = expenseAdapter

        viewModel.expensesListLiveData.observe(this.viewLifecycleOwner, Observer {
            expenseAdapter.submitList(it)
        })

        goToNewExpenseFragment()
        goToSettingFragment()

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

}