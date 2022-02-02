package com.github.varenytsiamykhailo.alexanderklimov.hellokitty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var mHelloTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mHelloTextView = findViewById(R.id.textView)
        val imageButton: ImageButton = findViewById(R.id.imageButton)
        val editTextView: EditText = findViewById(R.id.editTextTextPersonName)
        // альтернативный вариант
        // val imageButton = findViewById<ImageButton>(R.id.imageButton)
        // или val imageButton = findViewById(R.id.imageButton) as ImageButton

        imageButton.setOnClickListener {
            if (editTextView.text.isEmpty()) {
                mHelloTextView.text = "Hello Kitty"
            } else {
                mHelloTextView.text = "Hi, " + editTextView.text
            }
        }

    }
}