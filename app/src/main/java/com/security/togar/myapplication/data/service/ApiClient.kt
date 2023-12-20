package com.security.togar.myapplication.data.service

import com.security.togar.myapplication.data.model.SecurityResponse
import com.security.togar.myapplication.data.model.StatusResponse
import com.security.togar.myapplication.data.model.VisitorResponse
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiClient {

    @GET("visitior.php")
    suspend fun getAllVisitor(
        @Query("function") function: String
    ): VisitorResponse

    @FormUrlEncoded
    @POST("visitior.php")
    suspend fun insertVisitor(
        @Query("function") function: String,
        @Field("id") id: String,
        @Field("security_name") securityName: String,
        @Field("no_plat") noPlat: String,
        @Field("createAt") createAt: String,
        @Field("updateAt") updateAt: String,
        @Field("tujuan") tujuan: String,
        @Field("jadwal_satpam") jadwalSatpam: String,
        @Field("status_visitor") statusVisitor: String
    ): StatusResponse

    @FormUrlEncoded
    @POST("visitior.php")
    suspend fun updateVisitor(
        @Query("function") function: String,
        @Query("id") id: String,
        @Field("security_name") securityName: String,
        @Field("no_plat") noPlat: String,
        @Field("createAt") createAt: String,
        @Field("updateAt") updateAt: String,
        @Field("tujuan") tujuan: String,
        @Field("jadwal_satpam") jadwalSatpam: String,
        @Field("status_visitor") statusVisitor: String
    ): StatusResponse

    @DELETE("visitior.php")
    suspend fun deleteVisitor(
        @Query("function") function: String,
        @Query("id") id: String,
    ): StatusResponse

    @GET("security.php")
    suspend fun getAllSecurity(
        @Query("function") function: String
    ): SecurityResponse

    @GET("security.php")
    suspend fun loginSecurity(
        @Query("function") function: String,
        @Query("email") emailUser: String,
        @Query("passwd") password: String
    ): SecurityResponse
}