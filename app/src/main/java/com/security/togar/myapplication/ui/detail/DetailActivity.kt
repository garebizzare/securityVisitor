package com.security.togar.myapplication.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.security.togar.myapplication.data.model.VisitorResponse
import com.security.togar.myapplication.databinding.ActivityDetailBinding
import com.security.togar.myapplication.ui.component.FieldFragment
import com.security.togar.myapplication.utils.Constant.IS_DETAIL
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var data = VisitorResponse.DataItemVisitor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        data = intent?.getParcelableExtra(DATA_PARSE) ?: VisitorResponse.DataItemVisitor()
        initFagment()
    }

    private fun initFagment() = with(binding) {
        supportFragmentManager
            .beginTransaction()
            .replace(parentEdit.id, FieldFragment.newInstance(IS_DETAIL, data))
            .commit()
    }

    companion object {
        const val DATA_PARSE = ""
    }

}