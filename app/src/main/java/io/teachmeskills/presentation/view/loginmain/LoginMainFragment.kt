package io.teachmeskills.presentation.view.loginmain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.teachmeskills.an03onl_accountingoffinancesapp.databinding.FragmentLoginMainBinding

class LoginMainFragment : Fragment() {

    private lateinit var binding: FragmentLoginMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginMainBinding.inflate(layoutInflater, container, false)
        return binding.root

    }
}