package io.teachmeskills.screen.registration

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.teachmeskills.an03onl_accountingoffinancesapp.R
import io.teachmeskills.an03onl_accountingoffinancesapp.databinding.FragmentRegistrationBinding
import io.teachmeskills.presentation.login.registration.RegistrationPresenter
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import org.koin.android.ext.android.get

class RegistrationFragment : MvpAppCompatFragment(), RegistrationView, View.OnClickListener {

    private val auth = Firebase.auth
    private lateinit var binding: FragmentRegistrationBinding

    @InjectPresenter
    lateinit var presenter: RegistrationPresenter

    @ProvidePresenter
    fun providePresenter(): RegistrationPresenter = RegistrationPresenter(
        userManager = get(),
        getUserByUid = get(),
        registerUserOnServer = get()
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        setOnBackPressedCallback()
        setOnTextChangedListeners()


    }

//    private fun setHideKeyboardListener() {
//        binding.registrationContainer.
//    }

    private fun setOnClickListeners() {
        binding.registerBtn.setOnClickListener(this)
        binding.registrationSignInBtn.setOnClickListener(this)
        binding.registrationBackArrow.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when(view) {
            binding.registerBtn -> {
                presenter.register(binding.usernameEditTxt.text.toString(),
                    binding.emailEditTxt.text.toString(),
                    binding.passwordEditTxt.text.toString()
                )
            }
            binding.registrationSignInBtn, binding.registrationBackArrow -> findNavController().popBackStack()
        }
    }

    override fun registerWithEmailAndPassword(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Log.e("Email, Password Sign In", "complete. uid = ${user?.uid ?: "nill"}")
                    presenter.setUid(user?.uid)
                    presenter.registerUser(
                        user?.uid,
                        binding.usernameEditTxt.text.toString(),
                        binding.emailEditTxt.text.toString()
                    )
                } else {
                    Log.e("Email,Password Sign In ", "failure", task.exception)
                }
            }
    }

    private fun  setOnTextChangedListeners(){
        binding.usernameEditTxt.doOnTextChanged { text, start, before, count ->
            presenter.validateName(text.toString())
        }
        binding.emailEditTxt.doOnTextChanged { text, start, before, count ->
            presenter.validateName(text.toString())
        }
        binding.passwordEditTxt.doOnTextChanged { text, start, before, count ->
            presenter.validateName(text.toString())
        }
    }

    private fun setOnBackPressedCallback() {
        requireActivity().onBackPressedDispatcher.addCallback{
            findNavController().popBackStack()
        }
    }

    override fun showNameIsEmptyError() {
        binding.registrationUsernameLayout.error = getString(R.string.name_empty_error)
        binding.registrationUsernameLayout.isErrorEnabled = true
    }
    override fun showEmailIsEmptyError() {
        binding.registrationEmailLayout.error = getString(R.string.email_empty_error)
        binding.registrationEmailLayout.isErrorEnabled = true
    }

    override fun showInvalidEmailError() {
        binding.registrationEmailLayout.error = getString(R.string.email_invalid_error)
        binding.registrationEmailLayout.isErrorEnabled = true
    }

    override fun showPasswordIsShortError() {
        binding.registrationPasswordLayout.error = getString(R.string.password_short_error)
        binding.registrationPasswordLayout.isErrorEnabled = true
    }

    override fun openSignInScreen() {
        findNavController().popBackStack()
    }

    override fun showValidEmail() {
        binding.registrationEmailLayout.isErrorEnabled = false
    }

    override fun showValidName() {
        TODO("Not yet implemented")
    }

    override fun showValidPassword() {
        binding.registrationPasswordLayout.isErrorEnabled = false
    }


}