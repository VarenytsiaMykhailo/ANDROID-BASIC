package com.github.varenytsiamykhailo.tamtech.week4.lab1

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
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
            createRequest("https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Flenta.ru%2Frss")
                .map { Gson().fromJson(it, Feed::class.java) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) // subscribeOn - поток, в котором будет выполнение, observeOn - поток, в который вернуть исполнение

        request = o.subscribe({
            // TODO получение результата из Observable
            showRecyclerView(it.items)
            for (item in it.items) {
                println("_____________a")
                Log.d("rec1_request", "title: ${item.title}")
            }
        }, {
            // TODO обработка ошибки
            println("_____________b $it")
            Log.e("rec1_request", "", it)
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

        return ElemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ElemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    class ElemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: FeedItem) {
            val vTitle = itemView.findViewById<TextView>(R.id.item_title)
            val vDesc= itemView.findViewById<TextView>(R.id.item_desc)
            val vThumb = itemView.findViewById<ImageView>(R.id.item_thumb)

            vTitle.text = item.title
            vDesc.text = item.description

            /*
            val thumbURI: String = item.thumbnail
            if (!thumbURI.isNotEmpty()) {
                Picasso.with(vThumb.context).load(thumbURI).into(vThumb)
            } else {
                val defaultThumbURI: String = "https://img3.akspic.ru/previews/8/7/7/6/6/166778/166778-spongebob-360x640.jpg"
                Picasso.with(vThumb.context).load(defaultThumbURI).into(vThumb)
            }
             */

            val thumbURI: String = item.enclosure.link
            try {
                Picasso.with(vThumb.context).load(thumbURI).into(vThumb)
            } catch (e: Exception) {
                Log.e("ImgLoadingError", e.message ?: "null")
                val defaultThumbURI: String = "https://img3.akspic.ru/previews/8/7/7/6/6/166778/166778-spongebob-360x640.jpg"
                Picasso.with(vThumb.context).load(defaultThumbURI).into(vThumb)
            }

            itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(item.link)
                itemView.context.startActivity(intent)
            }
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
    val description: String,
    val enclosure: Enclosure
) {

}

data class Enclosure(
    val link: String,
    val type: String,
    val length: Int
)