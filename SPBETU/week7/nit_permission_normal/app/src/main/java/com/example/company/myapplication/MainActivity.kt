package com.example.company.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    val HTTP_OK = "HTTP_OK"

    val HTTP_FAIL = "HTTP_FAIL"

    var request: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener {
            val editText = findViewById<EditText>(R.id.editText)

            val url = editText.text.toString()

            // Осуществление http запроса в сеть
            val o =
                createRequest(url)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()) // subscribeOn - поток, в котором будет выполнение, observeOn - поток, в который вернуть исполнение

            request = o.subscribe({
                // TODO получение результата из Observable
                val textview = findViewById<TextView>(R.id.textView)
                textview.text = "Ok"
            }, {
                // TODO обработка ошибки
                Log.d("qwerty", it.message)
                val textview = findViewById<TextView>(R.id.textView)
                textview.text = "Failed"
            }) // 1ая лямбда - получение результата из o. 2ая лямбда - вызовится, если возникнет ошибка
        }
    }

    fun createRequest(url: String) = Observable.create<String> {
        // TODO обращение в сеть
        /*
        var url = url
        if (url.contains("http")) {
            if (!url.contains("https")) {
                url = url.replace("http", "https")
            }
        }

         */
        val urlConnection = URL(url).openConnection() as HttpURLConnection
        try {
            urlConnection.connect()

            if (urlConnection.responseCode != HttpURLConnection.HTTP_OK) {
                it.onError(RuntimeException(HTTP_FAIL))
            } else {
                it.onNext(HTTP_OK) // Передача результата работы
            }
        } finally {
            urlConnection.disconnect()
        }
    }
}
