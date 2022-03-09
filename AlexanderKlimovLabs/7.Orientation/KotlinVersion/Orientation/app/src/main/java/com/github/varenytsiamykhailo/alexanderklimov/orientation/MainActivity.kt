package com.github.varenytsiamykhailo.alexanderklimov.orientation

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Surface
import android.content.pm.ActivityInfo


// http://developer.alexanderklimov.ru/android/orientation.php
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE


        Log.d("qwerty.orientation", getScreenOrientation())
        Log.d("qwerty.turnedTo", getRotateOrientation())
    }

    private fun getScreenOrientation(): String {
        return when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> "Портретная ориентация"
            Configuration.ORIENTATION_LANDSCAPE -> "Альбомная ориентация"
            else -> "Другая"
        }
    }

    // !!!!!!!!!!!! На многих телефонах и эмуляторах код работает частично или не работает или работает неправильно.
    // Точно работает на планшете Google Nexus 7 2013. Не стоит полагаться на код в рабочем приложении.
    private fun getRotateOrientation(): String {
        return when (windowManager.defaultDisplay.rotation) {
            Surface.ROTATION_0 -> "Не поворачивали"
            Surface.ROTATION_90 -> "Повернули на 90 градусов по часовой стрелке"
            Surface.ROTATION_180 -> "Повернули на 180 градусов"
            Surface.ROTATION_270 -> "Повернули на 90 градусов против часовой стрелки"
            else -> "Не понятно"
        }
    }

}