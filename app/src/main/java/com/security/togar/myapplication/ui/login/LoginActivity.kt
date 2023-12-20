package com.security.togar.myapplication.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import com.security.togar.myapplication.base.launchAndCollectIn
import com.security.togar.myapplication.base.onError
import com.security.togar.myapplication.base.onErrorHttpException
import com.security.togar.myapplication.base.onErrorNetwork
import com.security.togar.myapplication.base.onSuccess
import com.security.togar.myapplication.base.stateUI
import com.security.togar.myapplication.databinding.ActivityLoginBinding
import com.security.togar.myapplication.ui.main.MainActivity
import com.security.togar.myapplication.ui.viewmodel.AppViewModel
import com.security.togar.myapplication.utils.Constant
import com.security.togar.myapplication.utils.Local
import com.security.togar.myapplication.utils.checkEdt
import com.security.togar.myapplication.utils.showToastError
import com.security.togar.myapplication.utils.showToastSuccess
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AppViewModel by viewModels()
    private var email = ""
    private var passwd = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val userEmail: String? = Local.getData(this, Constant.USER_EMAIL, "")
        val userName: String? = Local.getData(this, Constant.USERNAME, "")
        if (userEmail != "" && userName != "") {
            handleIntent()
        } else {
            validateEmailPasswords()
        }
    }

    private fun validateEmailPasswords() = with(binding) {
        btnSubmit.setOnClickListener {
            edtEmail.doOnTextChanged { text, _, _, _ ->
                email = text.toString()
            }

            edtEmail.doOnTextChanged { text, _, _, _ ->
                passwd = text.toString()
            }
            observeData()
        }
    }

    private fun observeData() {
        viewModel.loginSecurity(
            function = "loginSecurity",
            email = email,
            passwd = passwd
        )
        viewModel.loginSecurity.launchAndCollectIn(this@LoginActivity) { state ->
            state
                .stateUI(
                    viewLoading = binding.progressBar
                )
            state
                .onSuccess {
                    Local.saveData(
                        this@LoginActivity,
                        Constant.USER_EMAIL,
                        it?.data?.get(0)?.email.toString()
                    )
                    Local.saveData(
                        this@LoginActivity,
                        Constant.USERNAME,
                        it?.data?.get(0)?.nama.toString()
                    )
                    Local.saveData(
                        this@LoginActivity,
                        Constant.USER_SHIFT,
                        it?.data?.get(0)?.jadwalSatpam.toString()
                    )
                    showToastSuccess()
                    handleIntent()
                }
                .onError {
                    showToastError()
                }
                .onErrorNetwork {
                    showToastError()
                }
                .onErrorHttpException {
                    showToastError()
                }
        }
    }

    private fun handleIntent() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}