package com.security.togar.myapplication.data.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class StatusResponse(

	@SerializedName("message")
	val message: String? = null,

	@SerializedName("status")
	val status: Int? = null
) : Parcelable
