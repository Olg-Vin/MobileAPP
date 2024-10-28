package com.vinio.firstlab

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


/**
 * Стартовый экран:
 * - краткое описание функционала приложения
 * - отобразить изображение, заголовка и подзаголовка функционала
 * - кнопка перехода к следующим/предыдущим функциям
 * */
class OnboardActivity : AppCompatActivity() {
    private lateinit var btn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.onboard)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btn = findViewById(R.id.btn_next)
        btn.setOnClickListener {
            val intent: Intent = Intent(this@OnboardActivity, SignInActivity::class.java)
            startActivity(intent)
        }
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