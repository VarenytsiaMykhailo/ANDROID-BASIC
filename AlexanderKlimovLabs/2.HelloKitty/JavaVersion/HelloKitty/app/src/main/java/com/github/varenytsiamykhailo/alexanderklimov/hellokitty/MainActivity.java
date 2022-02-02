package com.github.varenytsiamykhailo.alexanderklimov.hellokitty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mHelloTextView;

    private EditText editTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelloTextView = findViewById(R.id.textView);
        editTextView = findViewById(R.id.editTextTextPersonName);
    }

    public void onButtonPressed(View view) {
        if (editTextView.getText().length() == 0) {
            mHelloTextView.setText("Hello Kitty!");
        } else {
            mHelloTextView.setText("Hi, " + editTextView.getText());
        }
    }
}