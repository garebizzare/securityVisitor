package com.security.togar.myapplication.ui.component

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.security.togar.myapplication.R
import com.security.togar.myapplication.base.launchAndCollectIn
import com.security.togar.myapplication.base.onError
import com.security.togar.myapplication.base.onErrorHttpException
import com.security.togar.myapplication.base.onErrorNetwork
import com.security.togar.myapplication.base.onSuccess
import com.security.togar.myapplication.base.stateUI
import com.security.togar.myapplication.data.model.VisitorResponse
import com.security.togar.myapplication.databinding.FragmentFieldBinding
import com.security.togar.myapplication.ui.main.MainActivity
import com.security.togar.myapplication.ui.viewmodel.AppViewModel
import com.security.togar.myapplication.utils.Constant
import com.security.togar.myapplication.utils.Constant.IS_DETAIL
import com.security.togar.myapplication.utils.Constant.IS_HOME
import com.security.togar.myapplication.utils.Constant.IS_USER
import com.security.togar.myapplication.utils.Constant.IS_VISITOR
import com.security.togar.myapplication.utils.Local
import com.security.togar.myapplication.utils.generateRandomString
import com.security.togar.myapplication.utils.showToastError
import com.security.togar.myapplication.utils.showToastSuccess
import com.security.togar.myapplication.utils.showToastView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class FieldFragment : Fragment() {
    private var _binding: FragmentFieldBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AppViewModel by viewModels()

    private var flagging = ""
    private var data = VisitorResponse.DataItemVisitor()
    private var typeUser = ""
    private var numberPlatUser = ""
    private var nameSecurity = ""
    private var timeVisit = ""
    private var reasone = ""
    private var listNameSecurity: List<String> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFieldBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            flagging = it.getString(FLAGGING) ?: ""
            data = it.getParcelable(ID) ?: VisitorResponse.DataItemVisitor()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNameSecurity()
    }

    private fun observeNameSecurity() {
        viewModel.getAllSecurity("getAllSecurity")
        viewModel.getAllSecurity.launchAndCollectIn(viewLifecycleOwner) { dataState ->
            dataState
                .onSuccess {
                    listNameSecurity = it?.data?.mapNotNull { data -> data?.nama } ?: emptyList()
                    initViews()
                }
                .onError {
                    requireContext().showToastError()
                }
                .onErrorNetwork {
                    requireContext().showToastError()
                }
                .onErrorHttpException {
                    requireContext().showToastError()
                }
        }

    }

    private fun initViews() = with(binding) {
        when {
            (flagging.equals(IS_HOME, false)) -> {
                edtClock.isFocusable = false
                edtClock.isFocusableInTouchMode = false
                teiNameSecurity.hint = "Pilih Satpam"
                onRadioButtonClicked(radioGroup)
                setSelectTypeUser()
                checkStatusField(false)
                setNameSecurity()
                edtClock.setOnClickListener {
                    selectClock()
                }
            }

            (flagging.equals(IS_DETAIL, false)) -> {
                edtPlat.setText(data.noPlat)
                edtReason.setText(data.tujuan)
                edtClock.setText(data.createAt)
                actNameSecurity.setText(data.securityName)
                teiNameSecurity.hint = data.securityName
                typeUser = data.statusVisitor.orEmpty()
                edtClock.isFocusable = false
                edtClock.isFocusableInTouchMode = false
                onRadioButtonClicked(radioGroup)
                setSelectTypeUser()
                checkStatusField(true)
                setNameSecurity()
                if (data.statusVisitor.equals(IS_USER, true)) {
                    buttonUser.isChecked = true
                }
                if (data.statusVisitor.equals(IS_VISITOR, true)) {
                    buttonVisitor.isChecked = true
                }
                edtClock.setOnClickListener {
                    selectClock()
                }
            }
        }
    }

    private fun onRadioButtonClicked(view: View) = with(binding) {
        if (view is RadioButton) {
            val checked = view.isChecked
            when (view.getId()) {
                buttonUser.id ->
                    if (checked) {
                        buttonUser.isChecked = true
                        buttonVisitor.isChecked = false
                    }

                buttonVisitor.id ->
                    if (checked) {
                        buttonVisitor.isChecked = true
                        buttonUser.isChecked = false
                    }
            }
        }
    }

    private fun setSelectTypeUser() = with(binding) {
        buttonUser.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                typeUser = IS_USER
            }
        }

        buttonVisitor.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                typeUser = IS_VISITOR
            }
        }
    }

    private fun setNameSecurity() = with(binding) {
        val adapter =
            ArrayAdapter(
                requireContext(),
                R.layout.list_item_security,
                R.id.textViewArray,
                listNameSecurity
            )
        actNameSecurity.setAdapter(adapter)
        actNameSecurity.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            nameSecurity = selectedItem
        }
    }

    private fun checkStatusField(isDashBoard: Boolean) = with(binding) {
        edtPlat.doOnTextChanged { text, _, _, _ ->
            numberPlatUser = text.toString()
        }
        actNameSecurity.doOnTextChanged { text, _, _, _ ->
            nameSecurity = text.toString()
        }
        edtClock.doOnTextChanged { text, _, _, _ ->
            timeVisit = text.toString()
        }
        edtReason.doOnTextChanged { text, _, _, _ ->
            reasone = text.toString()
        }
        numberPlatUser = edtPlat.text.toString().trim()
        nameSecurity = actNameSecurity.text.toString().trim()
        timeVisit = edtClock.text.toString().trim()
        reasone = edtReason.text.toString().trim()
        btnSubmit.setOnClickListener {
            if (isFieldCompleate()) {
                observeData(isDashBoard)
            } else {
                Toast.makeText(requireContext(), "Data Wajib lengkap!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isFieldCompleate(): Boolean {
        return numberPlatUser != "" && timeVisit != "" && reasone != "" && typeUser != ""// && nameSecurity != ""
    }

    private fun selectClock() = with(binding) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _: TimePicker?, selectedHour: Int, selectedMinute: Int ->
                val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                edtClock.setText(selectedTime)
            },
            hour,
            minute,
            false
        )

        timePickerDialog.show()
    }

    private fun observeData(isDashBoard: Boolean) = lifecycleScope.launch {
        val userShift: String? = Local.getData(requireContext(), Constant.USER_EMAIL, "")
        if (isDashBoard) {
            viewModel.updateVisitor(
                function = "updateVisitor",
                id = data.id.orEmpty(),
                securityName = nameSecurity,
                noPlat = numberPlatUser,
                createAt = timeVisit,
                updateAt = timeVisit,
                reason = reasone,
                jadwalSatpam = userShift.orEmpty(),
                statusVisitor = typeUser
            )
            viewModel.updateVisitor.launchAndCollectIn(viewLifecycleOwner) { dataState ->
                dataState.stateUI(
                    viewLoading = binding.progressBar
                )
                dataState
                    .onSuccess {
                        requireContext().showToastSuccess()
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                    }
                    .onError {
                        requireContext().showToastError()
                    }
                    .onErrorNetwork {
                        requireContext().showToastError()
                    }
                    .onErrorHttpException {
                        requireContext().showToastError()
                    }
            }
        } else {
            viewModel.insertVisitor(
                function = "insertVisitor",
                id = generateRandomString(10),
                securityName = nameSecurity,
                noPlat = numberPlatUser,
                createAt = timeVisit,
                updateAt = timeVisit,
                reason = reasone,
                jadwalSatpam = userShift.orEmpty(),
                statusVisitor = typeUser
            )
            viewModel.insertVisitor.launchAndCollectIn(viewLifecycleOwner) { dataState ->
                dataState.stateUI(
                    viewLoading = binding.progressBar
                )
                dataState
                    .onSuccess {
                        println("dadadadada $it")
                        requireContext().showToastSuccess()
                    }
                    .onError {
                        requireContext().showToastError()
                    }
                    .onErrorNetwork {
                        requireContext().showToastError()
                    }
                    .onErrorHttpException {
                        requireContext().showToastError()
                    }
            }
        }
    }

    companion object {
        private const val FLAGGING = "flaggingData"
        private const val ID = "idData"

        @JvmStatic
        fun newInstance(flagging: String, data: VisitorResponse.DataItemVisitor? = null) =
            FieldFragment().apply {
                arguments = Bundle().apply {
                    putString(FLAGGING, flagging)
                    putParcelable(ID, data)
                }
            }
    }
}