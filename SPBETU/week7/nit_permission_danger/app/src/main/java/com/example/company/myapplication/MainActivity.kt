package com.example.company.myapplication

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    val REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val status = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            if (status == PackageManager.PERMISSION_GRANTED) {
                val textView = findViewById<TextView>(R.id.textView)
                textView.text = "Granted"
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CODE)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val textView = findViewById<TextView>(R.id.textView)
                    textView.text = "Granted"
                } else {
                    val textView = findViewById<TextView>(R.id.textView)
                    textView.text = "Denied"
                }
            }
        }
    }
}
