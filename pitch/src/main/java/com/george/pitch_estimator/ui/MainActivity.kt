package com.george.pitch_estimator.ui

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.george.pitch_estimator.R
import com.gyf.immersionbar.ImmersionBar


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ImmersionBar.with(this).statusBarDarkFont(true) .navigationBarColor("#ffffff").navigationBarDarkIcon(true).init()

    }
}