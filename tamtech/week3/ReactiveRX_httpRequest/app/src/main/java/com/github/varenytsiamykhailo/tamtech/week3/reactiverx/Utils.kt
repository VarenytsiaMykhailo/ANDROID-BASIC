package com.github.varenytsiamykhailo.tamtech.week3.reactiverx

import io.reactivex.Observable
import java.lang.RuntimeException
import java.net.HttpURLConnection
import java.net.URL

fun createRequest(url: String) = Observable.create<String> {
    // TODO обращение в сеть

    val urlConnection = URL(url).openConnection() as HttpURLConnection
    try {
        urlConnection.connect()

        if (urlConnection.responseCode != HttpURLConnection.HTTP_OK) {
            it.onError(RuntimeException(urlConnection.responseMessage))
        } else {
            val resultStr = urlConnection.inputStream.bufferedReader().readText()
            it.onNext(resultStr)
        }

    } finally {
        urlConnection.disconnect()
    }
}