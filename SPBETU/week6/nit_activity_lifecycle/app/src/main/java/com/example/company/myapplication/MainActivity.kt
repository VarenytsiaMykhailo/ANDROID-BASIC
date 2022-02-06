package com.example.company.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            this.intent.putExtra("data", editText.text.toString())
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        if (this.intent.getStringExtra("data2") != null) {
            textView.text = this.intent.getStringExtra("data2")
        }
        if (this.intent.getStringExtra("data") == null) {
            Log.d("TAG007", "nul")
        }
        editText.setText("")
        super.onResume()
    }
}
