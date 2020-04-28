package com.example.click

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var counter: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            textView.text = "Hello Kitty!"
            it.setBackgroundColor(Color.CYAN)
        }

        button_counter.setOnClickListener{
            counter++
            textView.text = "Я насчитал ${counter} ворон"
        }
    }
}
