package com.example.hellokitty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mHelloTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mHelloTextView = findViewById(R.id.textView)

        var imageButton: ImageButton = findViewById(R.id.imageButton) //или можно так:
        //val imageButton = findViewById<ImageButton>(R.id.imageButton) //или так:
        //val imageButton = findViewById(R.id.imageButton) as ImageButton
        imageButton.setOnClickListener{
            if (editText.text.isEmpty())
                mHelloTextView.setText("Hello Kitty")
            else
                textView.text = "Привет, " + editText.text
        }
    }
}

/* Или можно так (возможность котлина):

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageButton.setOnClickListener {
            textView.text = "Hello Kitty!"
        }
    }
}
**/