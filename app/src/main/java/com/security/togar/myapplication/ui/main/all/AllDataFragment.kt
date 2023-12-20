package com.security.togar.myapplication.ui.main.all

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.security.togar.myapplication.R
import com.security.togar.myapplication.databinding.FragmentAllDataBinding
import com.security.togar.myapplication.databinding.FragmentHomeBinding
import com.security.togar.myapplication.ui.component.FieldFragment
import com.security.togar.myapplication.ui.component.VisitorFragment
import com.security.togar.myapplication.utils.Constant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllDataFragment : Fragment() {
    private var _binding: FragmentAllDataBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragment()
    }

    private fun initFragment() = with(binding) {
        childFragmentManager
            .beginTransaction()
            .replace(parentList.id, VisitorFragment.newInstance(Constant.IS_LIST_DATA))
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}