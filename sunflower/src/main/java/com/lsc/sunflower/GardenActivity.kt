package com.lsc.sunflower

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.lsc.sunflower.databinding.ActivityMainBinding

class GardenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

    }
}
