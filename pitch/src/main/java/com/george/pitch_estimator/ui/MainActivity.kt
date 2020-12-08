package com.george.pitch_estimator.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.george.pitch_estimator.R
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ImmersionBar.with(this).statusBarDarkFont(true) .navigationBarColor("#ffffff").navigationBarDarkIcon(true).init()

        title_name.setOnClickListener {
            startActivity(Intent(this@MainActivity,HistoryActivity::class.java))
        }

    }
}