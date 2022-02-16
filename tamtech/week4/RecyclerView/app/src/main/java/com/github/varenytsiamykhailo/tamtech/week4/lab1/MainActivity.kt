package com.github.varenytsiamykhailo.tamtech.week4.lab1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    var request: Disposable? = null

    lateinit var vRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vRecyclerView = findViewById<RecyclerView>(R.id.act1_recyclerView)

        // Осуществление http запроса в сеть
        val o =
            createRequest("https://api.rss2json.com/v1/api.json?rss_url=http%3A%2F%2Ffeeds.bbci.co.uk%2Fnews%2Frss.xml")
                .map { Gson().fromJson(it, Feed::class.java) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) // subscribeOn - поток, в котором будет выполнение, observeOn - поток, в который вернуть исполнение

        request = o.subscribe({
            // TODO получение результата из Observable
            showRecyclerView(it.items)
            for (item in it.items) {
                Log.w("test", "title: ${item.title}")
            }
        }, {
            // TODO обработка ошибки
            Log.e("test", "", it)
        }) // 1ая лямбда - получение результата из o. 2ая лямбда - вызовится, если возникнет ошибка

    }

    fun showRecyclerView(feedList: ArrayList<FeedItem>) {
        vRecyclerView.adapter = ListAdapter(feedList)
        vRecyclerView.layoutManager = LinearLayoutManager(this)
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

private class ListAdapter(val items: ArrayList<FeedItem>) :
    RecyclerView.Adapter<ListAdapter.ElemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElemViewHolder {
        val inflater = LayoutInflater.from(parent!!.context)
        val view = inflater.inflate(R.layout.list_item, parent, false)
        val elemViewHolder = ElemViewHolder(view)

        return ElemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ElemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    class ElemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: FeedItem) {
            val vTitle = itemView.findViewById<TextView>(R.id.item_title)
            vTitle.text = item.title
        }
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