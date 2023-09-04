package com.example.erasmusbudgetapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

class Statistics : AppCompatActivity() {
    private lateinit var btnBack : Button
    private lateinit var tvFood : TextView
    private lateinit var tvEntertainment : TextView
    private lateinit var tvMisc : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)
        tvFood = findViewById(R.id.tvFood);
        tvEntertainment = findViewById(R.id.tvEntertainment);
        tvMisc = findViewById(R.id.tvMisc);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                onBackPressed()
            }
        })
        populateFields();
    }

    private fun populateFields(){
        val context = this;
        try {
            val dateFormat: DateFormat = SimpleDateFormat("MM")
            val date = Date()
            val month = dateFormat.format(date)
            var totalFood: Double = 0.0
            var totalEntertainment: Double = 0.0
            var totalMisc: Double = 0.0
            val fis = openFileInput("data.txt")
            val isr = InputStreamReader(fis)
            val br = BufferedReader(isr)
            var line: String? = br.readLine()
            while (line != null) {
                var values: List<String> = line.split(";");
                if(values[3]==month){
                    when(values[0]){
                        "Food" -> totalFood+=values[1].toDouble()
                        "Entertainment" -> totalEntertainment+=values[1].toDouble()
                        "Other" -> totalMisc+=values[1].toDouble()
                    }
                }
                line=br.readLine();
            }
            val formattedFood = String.format("%.2f",totalFood);
            val formattedEnt = String.format("%.2f",totalEntertainment);
            val formattedMisc = String.format("%.2f",totalMisc);
            tvFood.text=formattedFood;
            tvEntertainment.text=formattedEnt;
            tvMisc.text=formattedMisc;
            br.close()
            isr.close()
            fis.close()
        }catch (e: IOException) {
            Toast.makeText(context, "File does not exist", Toast.LENGTH_SHORT).show()
            tvFood.text="0.0"
            tvEntertainment.text="0.0"
            tvMisc.text="0.0"
        }
    }
}