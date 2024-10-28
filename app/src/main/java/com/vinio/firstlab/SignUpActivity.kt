package com.vinio.firstlab

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SignUpActivity : AppCompatActivity() {
    private lateinit var btn : Button
    private lateinit var entranceBtn : Button
    lateinit var userName : EditText
    lateinit var login : EditText
    lateinit var password : EditText
    lateinit var repeatPassword : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btn = findViewById(R.id.btn_register)
        entranceBtn = findViewById(R.id.btn_auth)

        userName = findViewById(R.id.et_userName)
        login = findViewById(R.id.et_login)
        password = findViewById(R.id.et_password)
        repeatPassword = findViewById(R.id.et_repeatPassword)

        btn.setOnClickListener {
            if (userName.text.isBlank()) {
                Toast.makeText(this,
                    "Имя пользователя не может быть пустым",
                    Toast.LENGTH_SHORT)
                    .show()
            } else if (login.text.isBlank()) {
                Toast.makeText(this,
                    "Почта не может быть пустым полем",
                    Toast.LENGTH_SHORT)
                    .show()
            } else if (password.text.isBlank()) {
                Toast.makeText(this,
                    "Вы не ввели пароль",
                    Toast.LENGTH_SHORT)
                    .show()
            } else if ((password.text.toString() != repeatPassword.text.toString())) {
                Toast.makeText(this,
                    "Пароли не совпадают",
                    Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this,
                    "Всё окей!",
                    Toast.LENGTH_SHORT)
                    .show()
//                withString()
                withClass()
            }
        }

        entranceBtn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }

    fun withClass(){
        val usernameEditText = findViewById<EditText>(R.id.et_userName)
        val emailEditText = findViewById<EditText>(R.id.et_login)
        val passwordEditText = findViewById<EditText>(R.id.et_password)
        val signUpButton = findViewById<Button>(R.id.btn_register)

        signUpButton.setOnClickListener {
            val user = User(
                usernameEditText.text.toString(),
                emailEditText.text.toString(),
                passwordEditText.text.toString()
            )
            val intent = Intent(this, SignInActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }
    }

    fun withString() {
        val intent = Intent(this, SignInActivity::class.java)
        intent.putExtra("username", userName.text.toString())
        intent.putExtra("login", login.text.toString())
        intent.putExtra("password", password.text.toString())
        startActivity(intent)
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