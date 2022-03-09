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
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmList
import io.realm.RealmObject
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    var request: Disposable? = null

    lateinit var vRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Realm.init(this)
        Realm.setDefaultConfiguration( RealmConfiguration.Builder().allowWritesOnUiThread(true).allowQueriesOnUiThread(true).build())

        vRecyclerView = findViewById<RecyclerView>(R.id.act1_recyclerView)

        // createRequest("https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Flenta.ru%2Frss")
        // Осуществление http запроса в сеть
        val o =
            createRequest("https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Ftass.ru%2Fen%2Frss")

                .map { Gson().fromJson(it, FeedAPI::class.java) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) // subscribeOn - поток, в котором будет выполнение, observeOn - поток, в который вернуть исполнение

        request = o.subscribe({
            // TODO получение результата из Observable

            val feed = Feed(
                it.items.mapTo(
                    RealmList<FeedItem>(),
                    { feed ->
                        FeedItem(
                            feed.title,
                            feed.link,
                            feed.description,
                            Enclosure(
                                feed.enclosure.link,
                                feed.enclosure.type,
                                feed.enclosure.length
                            )
                        )
                    })
            )

            Realm.getDefaultInstance().executeTransaction { realm ->
                // Прежде чем записать данные в бд, нужно удалить те данные, которые там уже записаны
                val oldList = realm.where(Feed::class.java).findAll()
                if (oldList.size > 0) {
                    for (item in oldList) {
                        item.deleteFromRealm()
                    }
                }

                // Запись в БД
                realm.copyToRealm(feed)

            }

            showRecyclerView()

        }, {
            // TODO обработка ошибки
            println("_____________b $it")
            Log.e("rec1_request", "", it)
            showRecyclerView()
        }) // 1ая лямбда - получение результата из o. 2ая лямбда - вызовится, если возникнет ошибка

    }

    fun showRecyclerView() {

        Realm.getDefaultInstance().executeTransaction { realm ->
            val feed = realm.where(Feed::class.java).findAll()
            if (feed.size > 0) {
                vRecyclerView.adapter = ListAdapter(feed[0]!!.items)
                vRecyclerView.layoutManager = LinearLayoutManager(this)
            }

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

private class ListAdapter(val items: RealmList<FeedItem>) :
    RecyclerView.Adapter<ListAdapter.ElemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElemViewHolder {
        val inflater = LayoutInflater.from(parent!!.context)
        val view = inflater.inflate(R.layout.list_item, parent, false)

        return ElemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ElemViewHolder, position: Int) {
        items[position]?.let { holder.bind(it) }
    }

    override fun getItemCount() = items.size

    class ElemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: FeedItem) {
            val vTitle = itemView.findViewById<TextView>(R.id.item_title)
            val vDesc = itemView.findViewById<TextView>(R.id.item_desc)
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

            val defaultThumbURI: String =
                "https://img3.akspic.ru/previews/8/7/7/6/6/166778/166778-spongebob-360x640.jpg"
            val thumbURI: String = item.enclosure?.link ?: defaultThumbURI
            try {
                Picasso.with(vThumb.context).load(thumbURI).into(vThumb)
            } catch (e: Exception) {
                Log.e("ImgLoadingError", e.message ?: "null")

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

class FeedAPI(
    val items: ArrayList<FeedItemAPI>
) {

}

class FeedItemAPI(
    val title: String,
    val link: String,
    val description: String,
    val enclosure: EnclosureAPI
) {

}

data class EnclosureAPI(
    val link: String,
    val type: String,
    val length: Int
)

open class Feed(
    var items: RealmList<FeedItem> = RealmList<FeedItem>()
) : RealmObject() {

}

open class FeedItem(
    var title: String = "",
    var link: String = "",
    var description: String = "",
    var enclosure: Enclosure? = Enclosure()
) : RealmObject() {

}

open class Enclosure(
    var link: String,
    var type: String,
    var length: Int
) : RealmObject() {

    constructor() : this("", "", 0) {

    }

}