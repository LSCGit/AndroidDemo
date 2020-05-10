package com.lsc.databinding.kotlin.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.lsc.databinding.R
import com.lsc.databinding.databinding.ActivityMain2Binding

class Main2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 此活动的布局是数据绑定布局，因此需要使用DataBindingUtil对其进行扩展。
        val binding: ActivityMain2Binding = DataBindingUtil.setContentView(
                this, R.layout.activity_main2)

        // The returned binding has references to all the Views with an ID.
        binding.observableFieldsActivityButton.setOnClickListener {
            startActivity(Intent(this, ObservableFieldActivity::class.java))
        }
        binding.viewmodelActivityButton.setOnClickListener {
            startActivity(Intent(this, ViewModelActivity::class.java))
        }
    }
}
