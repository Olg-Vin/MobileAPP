package com.vinio.firstlab

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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
//                    withString()
                    withClass()
                }
            }
        }

        // Переход к экрану входа
        entranceBtn.setOnClickListener {
            (activity as? MainActivity)?.navigateToSignIn()
        }
    }


    private fun withString() {
        (activity as? MainActivity)?.navigateToSignIn(
            userName.text.toString(),
            login.text.toString(),
            password.text.toString()
        )
    }
    private fun withClass() {
        val user = User(
            username = userName.text.toString(),
            email = login.text.toString(),
            password = password.text.toString()
        )
        (activity as? MainActivity)?.navigateToSignIn(user)
    }


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
