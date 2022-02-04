package com.example.company.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (editText.isFocused) {
                    // true - 1st editText, false - 2nd editText
                    convert(true)
                }
            }
        })

        editText2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (editText2.isFocused) {
                    // true - 1st editText, false - 2nd editText
                    convert(false)
                }
            }

        })

    }

    private fun convert(lastChangedEditTextFlag: Boolean) {
        val inchesInKilometer = 39370

        if (lastChangedEditTextFlag) { // To kilometers
            val inchesOfString: String = editText.text.toString()
            val inchesOfFloat: Float? = inchesOfString.toFloatOrNull()
            if (inchesOfFloat != null) {
                status.text = ""
                val kilometers: Float = inchesOfFloat / inchesInKilometer
                editText2.setText(kilometers.toString())
            } else {
                status.text = "error"
            }
        } else { // To inches
            val kilometersOfString: String = editText2.text.toString()
            val kilometersOfFloat: Float? = kilometersOfString.toFloatOrNull()
            if (kilometersOfFloat != null) {
                status.text = ""
                val inches: Float = kilometersOfFloat * inchesInKilometer
                editText.setText(inches.toString())
            } else {
                status.text = "error"
            }
        }
    }
}
