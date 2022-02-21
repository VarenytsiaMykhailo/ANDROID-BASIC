package com.github.varenytsiamykhailo.tamtech.week5.myapplication.sharedpreferences

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getSharedPreferences("config", 0).edit().putString("name", "Vasya").putString("age", "17").apply()

        val str = getSharedPreferences("config", 0).getString("name", "default_value")

        Log.d("qwerty", str ?: "null")

        val str2 = getSharedPreferences("config", 0).getString("age", "default_value")

        Log.d("qwerty2", str2 ?: "null2")

        val str3 = getSharedPreferences("config", 0).getString("asdasdad", "default_value")

        Log.d("qwerty3", str3 ?: "null3")


        val b = getSharedPreferences("config", 0).contains("name")
        Log.d("contains1", b.toString())

        val b2 = getSharedPreferences("config", 0).contains("asdasdad")
        Log.d("contains2", b2.toString())

    }
}