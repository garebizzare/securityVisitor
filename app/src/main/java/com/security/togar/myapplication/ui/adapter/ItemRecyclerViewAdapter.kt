package com.security.togar.myapplication.ui.adapter

import android.view.View
import androidx.core.view.isVisible
import com.security.togar.myapplication.base.BaseListAdapter
import com.security.togar.myapplication.data.model.VisitorResponse
import com.security.togar.myapplication.databinding.ItemVisitorBinding
import com.security.togar.myapplication.utils.Constant.IS_USER
import com.security.togar.myapplication.utils.Constant.IS_VISITOR

class ItemRecyclerViewAdapter(
    private val onEditClick: (VisitorResponse.DataItemVisitor) -> Unit,
    private val onDeleteClick: (VisitorResponse.DataItemVisitor) -> Unit
) : BaseListAdapter<VisitorResponse.DataItemVisitor, ItemVisitorBinding>(ItemVisitorBinding::inflate) {
    var isFromHome: Boolean = false
        set(value) {
            field = value
        }
    var isFromDashboard: Boolean = false
        set(value) {
            field = value
        }

    var isFromAllData: Boolean = false
        set(value) {
            field = value
        }

    override fun onItemBind(): (VisitorResponse.DataItemVisitor, ItemVisitorBinding, View, Int) -> Unit =
        { item, binding, itemView, position ->

            binding.llStatus.isVisible = item.statusVisitor.equals(IS_VISITOR, true)
            binding.tvStatus.text = item.statusVisitor
            binding.llStatusUser.isVisible = item.statusVisitor.equals(IS_USER, true)
            binding.tvStatusUser.text = item.statusVisitor
            binding.tvPlatNomer.text = item.noPlat
            binding.tvNameSecurity.text = item.securityName
            binding.tvReason.text = item.tujuan
            val shiftText = "Shift ${item.jadwalSatpam}"
            binding.tvShift.text = shiftText

            when {
                isFromHome -> {
                    binding.clAllData.isVisible = false
                    binding.llEdit.isVisible = false
                }

                isFromDashboard -> {
                    binding.clAllData.isVisible = false
                    binding.llEdit.isVisible = true
                    binding.ivEdit.setOnClickListener {

                    }
                    binding.ivDelete.setOnClickListener {

                    }
                }

                isFromAllData -> {
                    binding.clAllData.isVisible = true
                    binding.llEdit.isVisible = false
                }

                else -> {}
            }
            binding.tvClock.text = item.createAt
            binding.ivEdit.setOnClickListener {
                onEditClick(item)
            }
            binding.ivDelete.setOnClickListener {
                onDeleteClick(item)
            }
        }
}