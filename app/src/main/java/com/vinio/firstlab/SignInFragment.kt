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
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp

/**
 * Экран входа (фрагмент):
 * - ввод эл.почты и пароля пользователя с проверкой на корректность данных
 * - использование фиктивных данных для аутентификации
 */
class SignInFragment : Fragment() {

    private lateinit var btn: Button
    private lateinit var regBtn: Button
    private lateinit var login: EditText
    private lateinit var password: EditText

    private val origin_login = "login@login"
    private val origin_password = "password"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn = view.findViewById(R.id.btn_auth)
        regBtn = view.findViewById(R.id.btn_register)
        login = view.findViewById(R.id.et_login)
        password = view.findViewById(R.id.et_password)

        auth()

        regBtn.setOnClickListener {
            findNavController().navigate(R.id.action_signIn_to_signUp)
        }

        // Заполняем поля логина и пароля при регистрации через intent
//        register()
        classRegister()
    }

    // Логика аутентификации
    private fun auth() {
        btn.setOnClickListener {
            when {
                login.text.isBlank() -> {
                    Toast.makeText(requireContext(), "Логин не может быть пустым", Toast.LENGTH_SHORT).show()
                }
                password.text.isBlank() -> {
                    Toast.makeText(requireContext(), "Вы не ввели пароль", Toast.LENGTH_SHORT).show()
                }
                login.text.toString() == origin_login && password.text.toString() == origin_password -> {
                    findNavController().navigate(R.id.action_signIn_to_home)
                }
                else -> {
                    Toast.makeText(requireContext(), "Логин или пароль не верны", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

  // Заполняем поля логина и пароля, переданные через Intent
//    private fun register() {
//        val args = SignInFragmentArgs.fromBundle(requireArguments())
//        if (args.name != "-" && args.password != "-") {
//          login.setText(args.name)
//          password.setText(args.password)
//        }
//    }


    private fun classRegister() {
        val args = SignInFragmentArgs.fromBundle(requireArguments())
        val user = args.user

        // Если объект `user` передан, заполняем поля
        if (user != null) {
            login.setText(user.email)
            password.setText(user.password)
        }

        regBtn.setOnClickListener {
            findNavController().navigate(R.id.action_signIn_to_signUp)
        }
    }


    override fun onStart() {
        super.onStart()
        Log.d("TAG", "Fragment singIn onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TAG", "Fragment singIn onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TAG", "Fragment singIn onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("TAG", "Fragment singIn onDestroyView")
    }
}
