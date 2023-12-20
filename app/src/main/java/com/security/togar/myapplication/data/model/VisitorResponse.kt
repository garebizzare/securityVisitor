package com.security.togar.myapplication.data.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
@Parcelize
data class VisitorResponse(

	@SerializedName("data")
	val data: List<DataItemVisitor?>? = null,

	@SerializedName("message")
	val message: String? = "",

	@SerializedName("status")
	val status: Int? = 0
) : Parcelable {

	@Parcelize
	data class DataItemVisitor(

		@SerializedName("status_visitor")
		val statusVisitor: String? = "",

		@SerializedName("updateAt")
		val updateAt: String? = "",

		@SerializedName("id")
		val id: String? = "",

		@SerializedName("no_plat")
		val noPlat: String? = "",

		@SerializedName("tujuan")
		val tujuan: String? = "",

		@SerializedName("security_name")
		val securityName: String? = "",

		@SerializedName("createAt")
		val createAt:String? = ""
	) : Parcelable

}