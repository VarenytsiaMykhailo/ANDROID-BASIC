package com.example.company.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var number: EditText
    private lateinit var systemOfCalculus: EditText
    private lateinit var convertResult: TextView
    private lateinit var convertButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        number = findViewById(R.id.number)
        systemOfCalculus = findViewById(R.id.systemOfCalculus)
        convertResult = findViewById(R.id.convertResult)
        convertButton = findViewById(R.id.convertButton)

        convertButton.setOnClickListener {
            val numberOfString = number.text.toString()
            val numberOfInt = numberOfString.toIntOrNull()
            val systemOfCalculusOfString = systemOfCalculus.text.toString()
            val systemOfCalculusOfInt = systemOfCalculusOfString.toIntOrNull()

            if (numberOfInt != null && systemOfCalculusOfInt != null && systemOfCalculusOfInt in 2..36) {
                convertResult.text = numberOfInt.toString(systemOfCalculusOfInt)
            } else {
                convertResult.text = "Error"
            }
        }

    }
}
