package com.vinio.firstlab.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vinio.firstlab.R
import com.vinio.firstlab.databinding.FragmentSigninBinding

/**
 * Экран входа (фрагмент):
 * - ввод эл.почты и пароля пользователя с проверкой на корректность данных
 * - использование фиктивных данных для аутентификации
 */
class SignInFragment : Fragment() {

    private var _binding: FragmentSigninBinding? = null
    private val binding: FragmentSigninBinding
        get() = (_binding
            ?: RuntimeException("FragmentSigninBinding == null")) as FragmentSigninBinding

    private val origin_login = "login"
    private val origin_password = "password"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSigninBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth()

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_signIn_to_signUp)
        }

//        register()
        classRegister()
    }

    private fun auth() {
        binding.btnAuth.setOnClickListener {
            when {
                binding.etLogin.text.isBlank() -> {
                    Toast.makeText(
                        requireContext(),
                        "Логин не может быть пустым",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                binding.etPassword.text.isBlank() -> {
                    Toast.makeText(requireContext(), "Вы не ввели пароль", Toast.LENGTH_SHORT)
                        .show()
                }

                binding.etLogin.text.toString() == origin_login && binding.etPassword.text.toString() == origin_password -> {
                    findNavController().navigate(R.id.action_signIn_to_home)
                }

                else -> {
                    Toast.makeText(
                        requireContext(),
                        "Логин или пароль не верны",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

//    private fun register() {
//        val args = SignInFragmentArgs.fromBundle(requireArguments())
//        if (args.name != "-" && args.password != "-") {
//            binding.etLogin.setText(args.name)
//            binding.etPassword.setText(args.password)
//        }
//    }


    private fun classRegister() {
        val args = SignInFragmentArgs.fromBundle(requireArguments())
        val user = args.user
        if (user != null) {
            binding.etLogin.setText(user.email)
            binding.etPassword.setText(user.password)
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
