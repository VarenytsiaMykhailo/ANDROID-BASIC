package com.example.company.myapplication

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = MutableList(5) { v -> "${v + 1}" }
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, data)
        listView1.adapter = adapter

        listView1.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, ListItemActivity::class.java)
            intent.putExtra("str", (view as TextView).text)
            startActivity(intent)
        }

    }
}
