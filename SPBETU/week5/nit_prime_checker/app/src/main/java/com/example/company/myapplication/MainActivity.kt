package com.example.company.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkBtn.setOnClickListener {
            val text: String = editText.text.toString()
            val num: Int? = text.toIntOrNull()

            if (num == null) {
                textView.text = "error"
            } else {
                if (isPrimitive(num)) {
                    textView.text = "prime"
                } else {
                    textView.text = "not prime"
                }
            }
        }
    }

    private fun isPrimitive(num: Int): Boolean {
        if (num <= 1) {
            return false
        }
        var flag = false
        for (i in 2..num / 2) {
            if (num % i == 0) {
                flag = true
                break
            }
        }

        return !flag
    }
}
