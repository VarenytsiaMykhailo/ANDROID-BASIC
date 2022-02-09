package com.example.company.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = MutableList(31) { x -> "$x" }
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data)
        gridView.adapter = adapter
        gridView.numColumns = 4

        gridView.setOnItemClickListener { parent, view, position, id ->
            var row = (1 + position / gridView.numColumns)
            var col = (1 + position % gridView.numColumns)
            data[position] = "${row},${col}"
            adapter.notifyDataSetChanged()
        }

    }
}
