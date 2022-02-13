package com.github.varenytsiamykhailo.tamtech.week3.lab3

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val thread = object : Thread() {
            override fun run() {

                // TODO обращение в сеть

                this@MainActivity.runOnUiThread {

                }
            }
        }
        thread.start()

        AT(this).execute()

    }
}

class AT(val activity: MainActivity): AsyncTask<String, Int, String>() {
    override fun doInBackground(vararg p0: String?): String {
        // TODO обращение в сеть
        return ""
    }

    override fun onPostExecute(result: String?) { // Вызовится уже в UI потоке. Тут можно обработать результат и вызывать вьюхи
        super.onPostExecute(result)
        // TODO vText = ...
    }
}