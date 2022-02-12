package com.example.company.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.abs

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val cellInputList = mutableListOf("", "0", "X")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cellInputList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner11.adapter = adapter
        spinner11.onItemSelectedListener = this
        spinner12.adapter = adapter
        spinner12.onItemSelectedListener = this
        spinner13.adapter = adapter
        spinner13.onItemSelectedListener = this

        spinner21.adapter = adapter
        spinner21.onItemSelectedListener = this
        spinner22.adapter = adapter
        spinner22.onItemSelectedListener = this
        spinner23.adapter = adapter
        spinner23.onItemSelectedListener = this

        spinner31.adapter = adapter
        spinner31.onItemSelectedListener = this
        spinner32.adapter = adapter
        spinner32.onItemSelectedListener = this
        spinner33.adapter = adapter
        spinner33.onItemSelectedListener = this

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        fun checkInvalidCount(): Boolean {
            var count0 = 0
            var countX = 0

            when (spinner11.selectedItem.toString()) {
                cellInputList[1] -> count0++
                cellInputList[2] -> countX++
            }
            when (spinner12.selectedItem.toString()) {
                cellInputList[1] -> count0++
                cellInputList[2] -> countX++
            }
            when (spinner13.selectedItem.toString()) {
                cellInputList[1] -> count0++
                cellInputList[2] -> countX++
            }
            when (spinner21.selectedItem.toString()) {
                cellInputList[1] -> count0++
                cellInputList[2] -> countX++
            }
            when (spinner22.selectedItem.toString()) {
                cellInputList[1] -> count0++
                cellInputList[2] -> countX++
            }
            when (spinner23.selectedItem.toString()) {
                cellInputList[1] -> count0++
                cellInputList[2] -> countX++
            }
            when (spinner31.selectedItem.toString()) {
                cellInputList[1] -> count0++
                cellInputList[2] -> countX++
            }
            when (spinner32.selectedItem.toString()) {
                cellInputList[1] -> count0++
                cellInputList[2] -> countX++
            }
            when (spinner33.selectedItem.toString()) {
                cellInputList[1] -> count0++
                cellInputList[2] -> countX++
            }
            if (abs(count0 - countX) >= 2) {
                return true
            }

            return false
        }

        if (checkInvalidCount()) {
            status.text = "Invalid"
        } else if (
            (spinner11.selectedItem.toString() == cellInputList[1] && spinner12.selectedItem.toString() == cellInputList[1] && spinner13.selectedItem.toString() == cellInputList[1]) ||
            (spinner21.selectedItem.toString() == cellInputList[1] && spinner22.selectedItem.toString() == cellInputList[1] && spinner23.selectedItem.toString() == cellInputList[1]) ||
            (spinner31.selectedItem.toString() == cellInputList[1] && spinner32.selectedItem.toString() == cellInputList[1] && spinner33.selectedItem.toString() == cellInputList[1]) ||

            (spinner11.selectedItem.toString() == cellInputList[1] && spinner21.selectedItem.toString() == cellInputList[1] && spinner31.selectedItem.toString() == cellInputList[1]) ||
            (spinner12.selectedItem.toString() == cellInputList[1] && spinner22.selectedItem.toString() == cellInputList[1] && spinner32.selectedItem.toString() == cellInputList[1]) ||
            (spinner13.selectedItem.toString() == cellInputList[1] && spinner23.selectedItem.toString() == cellInputList[1] && spinner33.selectedItem.toString() == cellInputList[1]) ||

            (spinner11.selectedItem.toString() == cellInputList[1] && spinner22.selectedItem.toString() == cellInputList[1] && spinner33.selectedItem.toString() == cellInputList[1]) ||
            (spinner13.selectedItem.toString() == cellInputList[1] && spinner22.selectedItem.toString() == cellInputList[1] && spinner31.selectedItem.toString() == cellInputList[1])
        ) {
            status.text = "0 won"
        } else if (
            (spinner11.selectedItem.toString() == cellInputList[2] && spinner12.selectedItem.toString() == cellInputList[2] && spinner13.selectedItem.toString() == cellInputList[2]) ||
            (spinner21.selectedItem.toString() == cellInputList[2] && spinner22.selectedItem.toString() == cellInputList[2] && spinner23.selectedItem.toString() == cellInputList[2]) ||
            (spinner31.selectedItem.toString() == cellInputList[2] && spinner32.selectedItem.toString() == cellInputList[2] && spinner33.selectedItem.toString() == cellInputList[2]) ||

            (spinner11.selectedItem.toString() == cellInputList[2] && spinner21.selectedItem.toString() == cellInputList[2] && spinner31.selectedItem.toString() == cellInputList[2]) ||
            (spinner12.selectedItem.toString() == cellInputList[2] && spinner22.selectedItem.toString() == cellInputList[2] && spinner32.selectedItem.toString() == cellInputList[2]) ||
            (spinner13.selectedItem.toString() == cellInputList[2] && spinner23.selectedItem.toString() == cellInputList[2] && spinner33.selectedItem.toString() == cellInputList[2]) ||

            (spinner11.selectedItem.toString() == cellInputList[2] && spinner22.selectedItem.toString() == cellInputList[2] && spinner33.selectedItem.toString() == cellInputList[2]) ||
            (spinner13.selectedItem.toString() == cellInputList[2] && spinner22.selectedItem.toString() == cellInputList[2] && spinner31.selectedItem.toString() == cellInputList[2])
        ) {
            status.text = "X won"
        } else if (
            spinner11.selectedItem.toString() != cellInputList[0] && spinner12.selectedItem.toString() != cellInputList[0] && spinner13.selectedItem.toString() != cellInputList[0] &&
            spinner21.selectedItem.toString() != cellInputList[0] && spinner22.selectedItem.toString() != cellInputList[0] && spinner23.selectedItem.toString() != cellInputList[0] &&
            spinner31.selectedItem.toString() != cellInputList[0] && spinner32.selectedItem.toString() != cellInputList[0] && spinner33.selectedItem.toString() != cellInputList[0]
        ) {
            status.text = "Draw"
        } else {
            status.text = ""
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}
