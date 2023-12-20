package com.security.togar.myapplication.data.repository

import com.security.togar.myapplication.base.safeApiCall
import com.security.togar.myapplication.data.model.SecurityResponse
import com.security.togar.myapplication.data.model.StatusResponse
import com.security.togar.myapplication.data.model.VisitorResponse
import com.security.togar.myapplication.data.service.ApiClient

interface AppRepository {
    suspend fun getAllVisitor(function: String): VisitorResponse
    suspend fun insertVisitor(
        function: String,
        id: String,
        securityName: String,
        noPlat: String,
        createAt: String,
        updateAt: String,
        reason: String,
        jadwalSatpam: String,
        statusVisitor: String
    ): StatusResponse

    suspend fun updateVisitor(
        function: String,
        id: String,
        securityName: String,
        noPlat: String,
        createAt: String,
        updateAt: String,
        reason: String,
        jadwalSatpam: String,
        statusVisitor: String
    ): StatusResponse

    suspend fun deleteVisitor(function: String, id: String): StatusResponse

    suspend fun getAllSecurity(function: String): SecurityResponse
    suspend fun loginSecurity(function: String, email: String, passwd: String): SecurityResponse
}

class AppRepositoryImpl(
    private val apiService: ApiClient
) : AppRepository {
    override suspend fun getAllVisitor(function: String): VisitorResponse {
        return safeApiCall {
            apiService.getAllVisitor(function)
        }
    }

    override suspend fun insertVisitor(
        function: String,
        id: String,
        securityName: String,
        noPlat: String,
        createAt: String,
        updateAt: String,
        reason: String,
        jadwalSatpam: String,
        statusVisitor: String
    ): StatusResponse {
        return safeApiCall {
            apiService.insertVisitor(
                function = function,
                id = id,
                securityName = securityName,
                noPlat = noPlat,
                createAt = createAt,
                updateAt = updateAt,
                tujuan = reason,
                jadwalSatpam = jadwalSatpam,
                statusVisitor = statusVisitor
            )
        }
    }

    override suspend fun updateVisitor(
        function: String,
        id: String,
        securityName: String,
        noPlat: String,
        createAt: String,
        updateAt: String,
        reason: String,
        jadwalSatpam: String,
        statusVisitor: String
    ): StatusResponse {
        return safeApiCall {
            apiService.updateVisitor(
                function = function,
                id = id,
                securityName = securityName,
                noPlat = noPlat,
                createAt = createAt,
                updateAt = updateAt,
                tujuan = reason,
                jadwalSatpam = jadwalSatpam,
                statusVisitor = statusVisitor
            )
        }
    }


    override suspend fun deleteVisitor(function: String, id: String): StatusResponse {
        return safeApiCall {
            apiService.deleteVisitor(function, id)
        }
    }

    override suspend fun getAllSecurity(function: String): SecurityResponse {
        return safeApiCall {
            apiService.getAllSecurity(function)
        }
    }

    override suspend fun loginSecurity(
        function: String,
        email: String,
        passwd: String
    ): SecurityResponse {
        return safeApiCall {
            apiService.loginSecurity(function, email, passwd)
        }
    }

}