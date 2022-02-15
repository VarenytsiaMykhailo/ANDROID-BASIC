package com.github.varenytsiamykhailo.tamtech.week4.lab1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    var request: Disposable? = null

    lateinit var vList: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Осуществление http запроса в сеть
        val o =
            createRequest("https://api.rss2json.com/v1/api.json?rss_url=http%3A%2F%2Ffeeds.bbci.co.uk%2Fnews%2Frss.xml")
                .map { Gson().fromJson(it, Feed::class.java) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) // subscribeOn - поток, в котором будет выполнение, observeOn - поток, в который вернуть исполнение

        request = o.subscribe({
            // TODO получение результата из Observable
            showLinearLayout(it.items)
            for (item in it.items) {
                Log.w("test", "title: ${item.title}")
            }
        }, {
            // TODO обработка ошибки
            Log.e("test", "", it)
        }) // 1ая лямбда - получение результата из o. 2ая лямбда - вызовится, если возникнет ошибка

        vList = findViewById(R.id.act1_list)
    }

    fun showLinearLayout(feedList: ArrayList<FeedItem>) {
        val inflater = layoutInflater
        for (f in feedList) {
            val view = inflater.inflate(R.layout.list_item, vList, false)
            val vTitle = view.findViewById<TextView>(R.id.item_title)
            vTitle.text = f.title
            vList.addView(view)
        }
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