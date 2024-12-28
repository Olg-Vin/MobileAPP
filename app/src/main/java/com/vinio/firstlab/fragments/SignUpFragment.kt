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
import com.vinio.firstlab.entity.User
import com.vinio.firstlab.databinding.FragmentSignupBinding

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding: FragmentSignupBinding
        get() = (_binding
            ?: RuntimeException("FragmentSignupBinding == null")) as FragmentSignupBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnRegister.setOnClickListener {
            when {
                binding.etUserName.text.isBlank() -> {
                    Toast.makeText(
                        requireContext(),
                        "Имя пользователя не может быть пустым",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                binding.etLogin.text.isBlank() -> {
                    Toast.makeText(
                        requireContext(),
                        "Почта не может быть пустым полем",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                binding.etPassword.text.isBlank() -> {
                    Toast.makeText(requireContext(), "Вы не ввели пароль", Toast.LENGTH_SHORT)
                        .show()
                }

                binding.etPassword.text.toString() != binding.etRepeatPassword.text.toString() -> {
                    Toast.makeText(requireContext(), "Пароли не совпадают", Toast.LENGTH_SHORT)
                        .show()
                }

                else -> {
                    Toast.makeText(requireContext(), "Всё окей!", Toast.LENGTH_SHORT).show()
//                    withString()
                    withClass()
                }
            }
        }

        // Переход к экрану входа
        binding.btnAuth.setOnClickListener {
            findNavController().navigate(R.id.action_signUp_to_singIn)
        }
    }


    //    private fun withString() {
//        val action = SignUpFragmentDirections
//            .actionSignUpToSingIn(binding.etUserName.text.toString(), binding.etPassword.text.toString())
//        findNavController().navigate(action)
//    }
//
    private fun withClass() {
        val user = User(
            binding.etUserName.text.toString(),
            binding.etLogin.text.toString(),
            binding.etPassword.text.toString()
        )
        val action = SignUpFragmentDirections.actionSignUpToSingIn(user)
        findNavController().navigate(action)
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
