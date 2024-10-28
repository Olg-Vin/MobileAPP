package com.vinio.firstlab

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

class SignUpFragment : Fragment() {

    private lateinit var btn: Button
    private lateinit var entranceBtn: Button
    private lateinit var userName: EditText
    private lateinit var login: EditText
    private lateinit var password: EditText
    private lateinit var repeatPassword: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Подключаем макет фрагмента
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
/*
        // Настраиваем edge-to-edge отображение
        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

        // Инициализация UI элементов
        btn = view.findViewById(R.id.btn_register)
        entranceBtn = view.findViewById(R.id.btn_auth)
        userName = view.findViewById(R.id.et_userName)
        login = view.findViewById(R.id.et_login)
        password = view.findViewById(R.id.et_password)
        repeatPassword = view.findViewById(R.id.et_repeatPassword)

        // Настройка кнопки регистрации
        btn.setOnClickListener {
            when {
                userName.text.isBlank() -> {
                    Toast.makeText(requireContext(), "Имя пользователя не может быть пустым", Toast.LENGTH_SHORT).show()
                }
                login.text.isBlank() -> {
                    Toast.makeText(requireContext(), "Почта не может быть пустым полем", Toast.LENGTH_SHORT).show()
                }
                password.text.isBlank() -> {
                    Toast.makeText(requireContext(), "Вы не ввели пароль", Toast.LENGTH_SHORT).show()
                }
                password.text.toString() != repeatPassword.text.toString() -> {
                    Toast.makeText(requireContext(), "Пароли не совпадают", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireContext(), "Всё окей!", Toast.LENGTH_SHORT).show()
//                    withClass()
                }
            }
        }

        // Переход к экрану входа
        entranceBtn.setOnClickListener {
            (activity as? MainActivity)?.navigateToSignIn()
        }
    }

  /*  // Метод для передачи данных через объект User
    private fun withClass() {
        val user = User(
            userName.text.toString(),
            login.text.toString(),
            password.text.toString()
        )
        val intent = Intent(requireContext(), SignInActivity::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
    }

    // Метод для передачи данных через строки
    private fun withString() {
        val intent = Intent(requireContext(), SignInActivity::class.java)
        intent.putExtra("username", userName.text.toString())
        intent.putExtra("login", login.text.toString())
        intent.putExtra("password", password.text.toString())
        startActivity(intent)
    }*/

    override fun onStart() {
        super.onStart()
        Log.d("TAG", "Fragment onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TAG", "Fragment onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TAG", "Fragment onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("TAG", "Fragment onDestroyView")
    }
}
