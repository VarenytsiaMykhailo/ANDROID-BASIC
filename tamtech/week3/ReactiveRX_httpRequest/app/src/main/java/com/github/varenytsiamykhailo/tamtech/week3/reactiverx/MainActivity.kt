package com.github.varenytsiamykhailo.tamtech.week3.reactiverx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.zipWith
import io.reactivex.schedulers.Schedulers
import java.lang.RuntimeException
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    var request: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ==================================================================================
        val o2 = Observable.create<String> {
            // TODO обращение в сеть

            it.onNext("asd") // Передача результата работы
        }
            .flatMap { Observable.create<String> {} }
            .zipWith(Observable.create<String> {})
            .map { it.second + it.first }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()) // subscribeOn - поток, в котором будет выполнение, observeOn - поток, в который вернуть исполнение
        // flatMap, zipWith, map - операторы, их много см. документацию.

        request = o2.subscribe({
            // TODO получение результата из Observable
        }, {
            // TODO обработка ошибки
        }) // 1ая лямбда - получение результата из o. 2ая лямбда - вызовится, если возникнет ошибка
        // ==================================================================================


        // Осуществление http запроса в сеть
        val o =
            createRequest("https://api.rss2json.com/v1/api.json?rss_url=http%3A%2F%2Ffeeds.bbci.co.uk%2Fnews%2Frss.xml")
                .map { Gson().fromJson(it, Feed::class.java) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) // subscribeOn - поток, в котором будет выполнение, observeOn - поток, в который вернуть исполнение

        request = o.subscribe({
            // TODO получение результата из Observable
            for (item in it.items) {
                Log.w("test", "title: ${item.title}")
            }
        }, {
            // TODO обработка ошибки
            Log.e("test", "", it)
        }) // 1ая лямбда - получение результата из o. 2ая лямбда - вызовится, если возникнет ошибка
    }


    fun createRequest(url: String) = Observable.create<String> {
        // TODO обращение в сеть

        val urlConnection = URL(url).openConnection() as HttpURLConnection
        try {
            urlConnection.connect()

            if (urlConnection.responseCode != HttpURLConnection.HTTP_OK) {
                it.onError(RuntimeException(urlConnection.responseMessage))
            } else {
                val resultStr = urlConnection.inputStream.bufferedReader().readText()
                it.onNext(resultStr) // Передача результата работы
            }

        } finally {
            urlConnection.disconnect()
        }
    }

    override fun onDestroy() {

        request?.dispose() // Решаем проблему утечки памяти

        super.onDestroy()
    }

}

class Feed(
    val items: ArrayList<FeedItem>
) {

}

class FeedItem(
    val title: String,
    val link: String,
    val thumbnail: String,
    val description: String
) {

}