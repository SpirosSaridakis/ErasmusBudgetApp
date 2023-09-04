package com.example.erasmusbudgetapp

import android.animation.ArgbEvaluator
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import org.w3c.dom.Text
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

const val BUDGET=750
class MainActivity : AppCompatActivity() {
    private lateinit var btnExpenseDiary: ImageButton
    private lateinit var btnStats: ImageButton
    private lateinit var tvAmountSpent : TextView
    private lateinit var tvAvailableAmount: TextView
    private lateinit var tvBudgetResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvAmountSpent = findViewById(R.id.tvAmountSpent)
        tvAvailableAmount = findViewById(R.id.tvAvailableAmount)
        btnExpenseDiary = findViewById(R.id.btnExpenseDiary)
        tvBudgetResult = findViewById(R.id.tvBudgetResult)
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
        populateFields()
    }

    override fun onRestart() {
        super.onRestart()
        populateFields()
    }


    private fun populateFields(){
        val context = this;
        try {
            val dateFormat: DateFormat = SimpleDateFormat("MM")
            val date = Date()
            val month = dateFormat.format(date)
            var total: Double = 0.0
            val fis = openFileInput("data.txt")
            val isr = InputStreamReader(fis)
            val br = BufferedReader(isr)

            var line: String? = br.readLine()
            while (line != null) {
                var values: List<String> = line.split(";");
                if(values[3]==month) {
                    total += values[1].toDouble();
                }
                line = br.readLine();
            }
            val formatted = String.format("%.2f",total);
            tvAmountSpent.text = formatted;
            if(total < BUDGET){
                tvAvailableAmount.text = (BUDGET-total).toString();
                tvBudgetResult.text = "Under"
                tvBudgetResult.setTextColor(Color.parseColor("#35BA01"));
                tvAmountSpent.setTextColor(Color.parseColor("#35BA01"));
            }else{
                tvBudgetResult.text="Over"
                tvBudgetResult.setTextColor(Color.parseColor("#DC1E0B"));
                tvAmountSpent.setTextColor(Color.parseColor("#DC1E0B"));
            }
            br.close()
            isr.close()
            fis.close()
        }catch (e: IOException) {
            Toast.makeText(context, "File does not exist", Toast.LENGTH_SHORT).show()
        }
    }
}
