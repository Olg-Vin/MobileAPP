package com.vinio.firstlab

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * Экран входа:
 * - ввод эл.почты и пароля пользователя с проверкой на корректность данных
 * - использовать фиктивные данные
 * */
class SignInActivity : AppCompatActivity() {
    private lateinit var btn : Button
    private lateinit var regBtn : Button
    lateinit var login : EditText
    lateinit var password : EditText

    val origin_login = "login@login"
    val origin_password = "password"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signin)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth()
//        register()
        classRegister()

        regBtn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
// startActivityForResult
    fun auth() {
        btn = findViewById(R.id.btn_auth)
        regBtn = findViewById(R.id.btn_register)
        login = findViewById(R.id.et_login)
        password = findViewById(R.id.et_password)

        btn.setOnClickListener {
            if (login.text.isBlank()) {
                Toast.makeText(this,
                    "Логин не может быть пустым",
                    Toast.LENGTH_SHORT)
                    .show()
            } else if (password.text.isBlank()) {
                Toast.makeText(this,
                    "Вы не ввели пароль",
                    Toast.LENGTH_SHORT)
                    .show()
            } else if ((login.text.toString() == origin_login && password.text.toString() == origin_password)) {
                val intent: Intent = Intent(this@SignInActivity, HomeActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this,
                    "Логин или пароль не верны",
                    Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun register () {
        val login = intent.getStringExtra("login")
        val password = intent.getStringExtra("password")

        val loginTextView = findViewById<TextView>(R.id.et_login)
        val passwordTextView = findViewById<TextView>(R.id.et_password)

        loginTextView.text = login
        passwordTextView.text = password
    }

    fun classRegister () {
        val user = intent.getSerializableExtra("user") as? User

        val loginTextView = findViewById<TextView>(R.id.et_login)
        val passwordTextView = findViewById<TextView>(R.id.et_password)

        user?.let {
            loginTextView.text = user.email
            passwordTextView.text = user.password
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