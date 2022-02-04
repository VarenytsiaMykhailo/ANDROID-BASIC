package com.example.company.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var storage: String? = null

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val wordCount =
                    s.toString().split("[^A-Za-zА-Яа-я0-9_]".toRegex()).filter { it != "" }.size
                stats_view.text = wordCount.toString()
                if (storage == s.toString()) {
                    unsaved_changes_view.text = "All changes saved"
                } else {
                    unsaved_changes_view.text = "Unsaved changes"
                }
            }
        })

        save_button.setOnClickListener {
            val text = editText.text.toString()
            storage = text
            unsaved_changes_view.text = "All changes saved"
        }

        load_button.setOnClickListener {
            editText.setText(storage)
        }

        clear_button.setOnClickListener {
            editText.setText("")
        }
    }
}
