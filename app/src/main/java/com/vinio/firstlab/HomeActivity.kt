package com.vinio.firstlab

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * Главный экран:
 * - отображение списка чатов с именем отправителя,
 * его профилем, последним сообщением и временем отправки сообщения
 * - использовать фиктивные данные для чатов
 * */
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Log.d("TAG", "OnCreate");
    }


    override fun onStart() {
        super.onStart()
        Log.d("TAG", "OnStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TAG", "OnResume")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TAG", "OnStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG", "onDestroy")
    }
}