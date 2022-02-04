package com.example.company.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        butAnswer.setOnClickListener {
            val checkedRadioButtonId = rgAnswers.checkedRadioButtonId
            if (checkedRadioButtonId == R.id.rbSphere) {
                tvAnswer.text = "Правильно!"
            } else {
                tvAnswer.text = "Неправильно!"
            }
        }

    }
}
