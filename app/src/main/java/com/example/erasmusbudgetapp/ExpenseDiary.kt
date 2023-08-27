package com.example.erasmusbudgetapp

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedWriter
import java.io.IOException
import java.io.OutputStreamWriter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

class ExpenseDiary : AppCompatActivity() {
    private lateinit var btnSave : ImageButton;
    private lateinit var spExpenseType : Spinner
    private lateinit var etExpenseAmount : EditText
    private lateinit var etDescription : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_diary);
        spExpenseType = findViewById(R.id.spExpenseType);
        val items = listOf("Food","Entertainment","Other");
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spExpenseType.adapter = adapter;
        etExpenseAmount = findViewById(R.id.etExpenseAmount);
        etDescription = findViewById(R.id.etDescription);
        btnSave = findViewById(R.id.btnSave);
        var context = this;
        btnSave.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                var SpinnerText = spExpenseType.selectedItem.toString();
                var Amount = etExpenseAmount.text.toString();
                var Desc = etDescription.text.toString();
                if(SpinnerText.isNullOrEmpty()){
                    Toast.makeText(context, "Empty field Spinner", Toast.LENGTH_SHORT).show()
                }else if(Amount.isNullOrEmpty()){
                    Toast.makeText(context, "Empty field Expense Amount", Toast.LENGTH_SHORT).show()
                }else if(Desc.isNullOrEmpty()){
                    Toast.makeText(context, "Empty field Desc", Toast.LENGTH_SHORT).show()
                }else {
                    val dateFormat: DateFormat = SimpleDateFormat("MM")
                    val date = Date()
                    val month = dateFormat.format(date)
                    var content = SpinnerText + ";" + Amount + ";" + Desc + ";" + month+"\n"
                    //Food,100,desc,08
                    try {
                        val fos = openFileOutput("data.txt", Context.MODE_APPEND)
                        val osw = OutputStreamWriter(fos)
                        val bw = BufferedWriter(osw)
                        bw.write(content)
                        bw.flush()
                        bw.close()
                        osw.close()
                        fos.close()
                        Toast.makeText(context, "Wrote to file", Toast.LENGTH_SHORT).show()
                    }catch (e: IOException) {
                        Toast.makeText(context, "Error writing to file", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })
    }
}