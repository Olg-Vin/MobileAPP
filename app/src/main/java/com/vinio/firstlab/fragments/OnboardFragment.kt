package com.vinio.firstlab.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vinio.firstlab.R
import com.vinio.firstlab.databinding.FragmentOnboardBinding

/**
 * Стартовый экран (фрагмент):
 * - краткое описание функционала приложения
 * - отображение изображения, заголовка и подзаголовка функционала
 * - кнопка перехода к следующему экрану
 */
class OnboardFragment : Fragment() {

    private var _binding: FragmentOnboardBinding? = null
    private val binding: FragmentOnboardBinding
        get() = (_binding
            ?: RuntimeException("FragmentOnboardBinding == null")) as FragmentOnboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener {
            Log.d("TAG", "Click on sign in button")
            findNavController().navigate(R.id.action_board_to_signIn)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("TAG", "Fragment onBoard onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TAG", "Fragment onBoard onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TAG", "Fragment onBoard onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("TAG", "Fragment onBoard onDestroyView")
    }
}
