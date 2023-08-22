package com.example.erasmusbudgetapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var btnExpenseDiary: ImageButton
    private lateinit var btnStats: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnExpenseDiary = findViewById(R.id.btnExpenseDiary)
        btnStats = findViewById(R.id.btnStats)
        btnExpenseDiary.setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                val intent = Intent();
                intent.setClass(this@MainActivity,ExpenseDiary::class.java);
                startActivity(intent);
            }
        })
        btnStats.setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                val intent = Intent();
                intent.setClass(this@MainActivity,Statistics::class.java);
                startActivity(intent);
            }
        })



    }
}
