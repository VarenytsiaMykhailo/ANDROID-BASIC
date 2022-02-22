package com.github.varenytsiamykhailo.tamtech.week5.myapplication.globalvariableswithgradle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("qwerty", BuildConfig.SERVER_URL)

        /*
        Нужно перекомпилировать проект, после добавления констант в грейдл.
        если настройки - это константы, типа дебаг-сервер, релиз-сервер,
        то тут есть способ лучше - в гредле можно создать виды сборок и для каждой кастомизировать константные переменные. н
        апример так:

        android {
            defaultConfig {
                buildConfigField "String", "SERVER_URL", "\"https://www.googleapis.com\""


        это будет дефолтное значение константы.
        и дальше  создаете сколько угодно вариантов сборок:

        flavorDimensions "main"
        productFlavors {
            forHome {
                buildConfigField "String", "SERVER_URL", "\"https://www.home.com\""
            }

            superDebug {
                buildConfigField "String", "SERVER_URL", "\"https://www.yandex.ru\""
            }

        }


        в результате после перекомпиляции проекта у вас появится автосгенеренный файл BuildConfig,
        а потом в коде используете это как BuildConfig.SERVER_URL
        - ну и в зависимости от того какую сборку собираем такая константа и будет

        */
    }
}