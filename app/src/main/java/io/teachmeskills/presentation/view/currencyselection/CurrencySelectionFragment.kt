package io.teachmeskills.presentation.view.currencyselection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.teachmeskills.an03onl_accountingoffinancesapp.databinding.FragmentCurrencySelectionBinding

class CurrencySelectionFragment : Fragment() {

    private lateinit var binding: FragmentCurrencySelectionBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrencySelectionBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}