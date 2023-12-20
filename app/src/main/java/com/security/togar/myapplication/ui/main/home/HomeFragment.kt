package com.security.togar.myapplication.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.security.togar.myapplication.databinding.FragmentHomeBinding
import com.security.togar.myapplication.ui.component.FieldFragment
import com.security.togar.myapplication.ui.component.VisitorFragment
import com.security.togar.myapplication.utils.Constant.IS_HOME
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragment()
    }

    private fun initFragment() = with(binding) {
        childFragmentManager
            .beginTransaction()
            .replace(parentInput.id, FieldFragment.newInstance(IS_HOME, null))
            .commit()

        childFragmentManager
            .beginTransaction()
            .replace(parentList.id, VisitorFragment.newInstance(IS_HOME))
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}