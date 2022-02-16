package com.github.varenytsiamykhailo.tamtech.week4.lab1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.Disposable


class MainActivity : AppCompatActivity() {

    var request: Disposable? = null

    lateinit var vRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vRecyclerView = findViewById<RecyclerView>(R.id.act1_recyclerView)

        val dataSet: List<String> = List(200) { x -> "[item: {$x}]" }

        showRecyclerView(dataSet)
    }

    fun showRecyclerView(data: List<String>) {
        vRecyclerView.adapter = ListAdapter(data)
        vRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onDestroy() {
        request?.dispose() // Решаем проблему утечки памяти
        super.onDestroy()
    }
}

private class ListAdapter(val data: List<String>) :
    RecyclerView.Adapter<ListAdapter.ElemViewHolder>() {

    private var countItems: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item, parent, false)
        val elemViewHolder = ElemViewHolder(view)

        elemViewHolder.setData("ViewHolder index: $countItems")
        countItems++

        return elemViewHolder
    }

    override fun onBindViewHolder(holder: ElemViewHolder, position: Int) {
        holder.setData("item index changed: $position")
    }

    override fun getItemCount() = data.size

    class ElemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setData(data: String) {
            if (!data.startsWith("item")) {
                val vTitle = itemView.findViewById<TextView>(R.id.item_title)
                vTitle.text = data
            }
        }

    }

}
