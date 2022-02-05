package com.github.varenytsiamykhailo.alexanderklimov.spinnertest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener  {

    private val toChooseList: List<String> = listOf("Breakfast", "branch", "lunch", "dinner")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner: Spinner = findViewById<Spinner>(R.id.spinnerExample)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, toChooseList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        Log.d("TAG", "ABRACODABRA")
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {
    }
}