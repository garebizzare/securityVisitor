package com.security.togar.myapplication.data.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class SecurityResponse(

	@SerializedName("data")
	val data: List<DataItemSecurity?>? = null,

	@SerializedName("message")
	val message: String? = "",

	@SerializedName("status")
	val status: Int? = 0
) : Parcelable {

	@Parcelize
	data class DataItemSecurity(

		@SerializedName("nama")
		val nama: String? = "",

		@SerializedName("passwd")
		val passwd: String? = "",

		@SerializedName("id")
		val id: String? = "",

		@SerializedName("email")
		val email: String? = "",

		@SerializedName("jadwal_satpam")
		val jadwalSatpam: String? = ""
	) : Parcelable

}
