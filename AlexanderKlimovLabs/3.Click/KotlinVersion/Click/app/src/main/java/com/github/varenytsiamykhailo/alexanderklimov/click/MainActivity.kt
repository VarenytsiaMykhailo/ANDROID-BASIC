package com.github.varenytsiamykhailo.alexanderklimov.click

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.varenytsiamykhailo.alexanderklimov.click.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var counter: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonGreeting.setOnClickListener {
            binding.textView.text = "Hello Kitty!"
        }

        binding.buttonCounter.setOnClickListener {
            binding.textView.text = "я насчитал ${++counter} ворон"
        }
    }
}