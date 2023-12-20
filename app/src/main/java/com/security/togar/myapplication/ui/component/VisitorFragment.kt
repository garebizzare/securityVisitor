package com.security.togar.myapplication.ui.component

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.security.togar.myapplication.base.launchAndCollectIn
import com.security.togar.myapplication.base.onError
import com.security.togar.myapplication.base.onErrorHttpException
import com.security.togar.myapplication.base.onErrorNetwork
import com.security.togar.myapplication.base.onSuccess
import com.security.togar.myapplication.base.stateUI
import com.security.togar.myapplication.databinding.FragmentVisitorBinding
import com.security.togar.myapplication.ui.adapter.ItemRecyclerViewAdapter
import com.security.togar.myapplication.ui.detail.DetailActivity
import com.security.togar.myapplication.ui.viewmodel.AppViewModel
import com.security.togar.myapplication.utils.Constant.IS_DASHBOARD
import com.security.togar.myapplication.utils.Constant.IS_HOME
import com.security.togar.myapplication.utils.showToastError
import com.security.togar.myapplication.utils.showToastView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VisitorFragment : Fragment() {
    private var _binding: FragmentVisitorBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AppViewModel by viewModels()
    private var flagging = ""
    private val adapterData by lazy {
        ItemRecyclerViewAdapter(
            onEditClick = { data ->
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.DATA_PARSE, data)
                startActivity(intent)
            },
            onDeleteClick = { data ->
                deleteData(data.id)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVisitorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            flagging = it.getString(FLAGGING) ?: ""
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun observeData() = lifecycleScope.launch {
        viewModel.getAllVisitor("getAllVisitor")
        viewModel.getAllVisitor.collectLatest { dataState ->
            dataState.stateUI(
                viewLoading = binding.progressBar
            )
            dataState
                .onSuccess {
                    binding.rvData.adapter = adapterData
                    if (flagging.equals(IS_HOME, true)) {
                        adapterData.isFromHome = true
                        adapterData.submitList(it?.data?.take(5))
                    } else if (flagging.equals(IS_DASHBOARD, true)) {
                        adapterData.isFromDashboard = true
                        adapterData.submitList(it?.data)
                    } else {
                        adapterData.isFromAllData = true
                        adapterData.submitList(it?.data)
                    }
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


    private fun deleteData(id: String?) {
        viewModel.deleteVisitor("deleteVisitor", id.orEmpty())
        viewModel.deleteVisitor.launchAndCollectIn(viewLifecycleOwner) { dataState ->
            dataState.stateUI(
                viewLoading = binding.progressBar
            )
            dataState
                .onSuccess {
                    observeData()
                    requireContext().showToastView(it?.message.orEmpty())
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

    companion object {
        private const val FLAGGING = "flaggingData"

        @JvmStatic
        fun newInstance(flagging: String) =
            VisitorFragment().apply {
                arguments = Bundle().apply {
                    putString(FLAGGING, flagging)
                }
            }
    }
}