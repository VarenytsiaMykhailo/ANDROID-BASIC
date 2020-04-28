package com.example.click;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button mCrowsCounterButton;
    private int mCounter = 0;
    /*
    Google разработал целое руководство по наименованию переменных.
    Например, закрытая переменная на уровне класса должна начинаться с символа m (member),
    а далее идёт понятное название с заглавной буквы.
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCrowsCounterButton = findViewById(R.id.button_counter);
        mCrowsCounterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCounter++;
                TextView helloTextView = findViewById(R.id.textView);
                helloTextView.setText("Я насчитал " + mCounter + " ворон");
            }
        });
    }

    public void onClick(View view) {
        TextView helloTextView = findViewById(R.id.textView);
        helloTextView.setText("Hello Kitty!");
    }
}
