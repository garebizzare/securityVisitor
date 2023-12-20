package com.security.togar.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.security.togar.myapplication.base.DataState
import com.security.togar.myapplication.base.asMutableStateFlow
import com.security.togar.myapplication.data.model.SecurityResponse
import com.security.togar.myapplication.data.model.StatusResponse
import com.security.togar.myapplication.data.model.VisitorResponse
import com.security.togar.myapplication.data.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {
    private val _getAllVisitor = MutableStateFlow<DataState<VisitorResponse?>>(DataState.Empty)
    val getAllVisitor = _getAllVisitor.asStateFlow()

    private val _insertVisitor = MutableStateFlow<DataState<StatusResponse?>>(DataState.Empty)
    val insertVisitor = _insertVisitor.asStateFlow()

    private val _updateVisitor = MutableStateFlow<DataState<StatusResponse?>>(DataState.Empty)
    val updateVisitor = _updateVisitor.asStateFlow()

    private val _deleteVisitor = MutableStateFlow<DataState<StatusResponse?>>(DataState.Empty)
    val deleteVisitor = _deleteVisitor.asStateFlow()

    private val _getAllSecurity = MutableStateFlow<DataState<SecurityResponse?>>(DataState.Empty)
    val getAllSecurity = _getAllSecurity.asStateFlow()

    private val _loginSecurity = MutableStateFlow<DataState<SecurityResponse?>>(DataState.Empty)
    val loginSecurity = _loginSecurity.asStateFlow()


    fun getAllVisitor(function: String) = viewModelScope.launch {
        _getAllVisitor.asMutableStateFlow {
            repository.getAllVisitor(function)
        }
    }

    fun insertVisitor(
        function: String,
        id: String,
        securityName: String,
        noPlat: String,
        createAt: String,
        updateAt: String,
        reason: String,
        jadwalSatpam: String,
        statusVisitor: String
    ) = viewModelScope.launch {
        _insertVisitor.asMutableStateFlow {
            repository.insertVisitor(
                function = function,
                id = id,
                securityName = securityName,
                noPlat = noPlat,
                createAt = createAt,
                updateAt = updateAt,
                reason = reason,
                jadwalSatpam = jadwalSatpam,
                statusVisitor = statusVisitor
            )
        }
    }

    fun updateVisitor(
        function: String,
        id: String,
        securityName: String,
        noPlat: String,
        createAt: String,
        updateAt: String,
        reason: String,
        jadwalSatpam: String,
        statusVisitor: String
    ) = viewModelScope.launch {
        _updateVisitor.asMutableStateFlow {
            repository.updateVisitor(
                function = function,
                id = id,
                securityName = securityName,
                noPlat = noPlat,
                createAt = createAt,
                updateAt = updateAt,
                reason = reason,
                jadwalSatpam = jadwalSatpam,
                statusVisitor = statusVisitor
            )
        }
    }

    fun deleteVisitor(function: String, id: String) = viewModelScope.launch {
        _insertVisitor.asMutableStateFlow {
            repository.deleteVisitor(
                function,
                id
            )
        }
    }

    fun getAllSecurity(function: String) = viewModelScope.launch {
        _getAllSecurity.asMutableStateFlow {
            repository.getAllSecurity(function)
        }
    }

    fun loginSecurity(function: String, email: String, passwd: String) = viewModelScope.launch {
        _loginSecurity.asMutableStateFlow {
            repository.loginSecurity(function, email, passwd)
        }
    }

}