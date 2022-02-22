package com.github.varenytsiamykhailo.tamtech.week5.myapplication.onsaveinstancestate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    lateinit var a: String

    lateinit var b: String

    lateinit var c: String

    lateinit var aPersist: String

    lateinit var bPersist: String

    lateinit var cPersist: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        a = savedInstanceState?.getString("a", "a_default") ?: "a_new"
        b = savedInstanceState?.getString("b", "b_default") ?: "b_new"
        c = savedInstanceState?.getString("c", "c_default") ?: "c_new"

        Log.d("qwertyA", a)
        Log.d("qwertyB", b)
        Log.d("qwertyC", c)

        // ------------------

        aPersist = savedInstanceState?.getString("aPersist", "aPersist_default") ?: "aPersist_new"
        bPersist = savedInstanceState?.getString("bPersist", "bPersist_default") ?: "bPersist_new"
        cPersist = savedInstanceState?.getString("cPersist", "cPersist_default") ?: "cPersist_new"

        Log.d("qwertyAPersist", aPersist)
        Log.d("qwertyBPersist", bPersist)
        Log.d("qwertyCPersist", cPersist)

    }

    // Этот метод можно использовать для сохранения данных при поворотах экрана, когда активити пересоздается
    // Или можно в манифесте запретить пересоздавать активити при повротах экрана
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        Log.d("qwertyOSIS", "onSaveInstanceState")

        outState.putString("a", "a_value")
        outState.putString("b", "b_value")

        outPersistentState.putString("aPersist", "aPersist_value")
        outPersistentState.putString("bPersist", "bPersist_value")

        super.onSaveInstanceState(outState, outPersistentState)
    }

}