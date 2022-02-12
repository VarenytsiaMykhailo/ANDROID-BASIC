package com.github.varenytsiamykhailo.alexanderklimov.click;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.github.varenytsiamykhailo.alexanderklimov.click.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private int mCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonGreeting.setOnClickListener(view ->
                binding.textView.setText("Hello Kitty!"));

        binding.buttonCounter.setOnClickListener(view ->
                binding.textView.setText("Я насчитал " + ++mCounter + " ворон"));
    }
}