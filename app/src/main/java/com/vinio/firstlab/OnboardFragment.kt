package com.vinio.firstlab

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

/**
 * Стартовый экран (фрагмент):
 * - краткое описание функционала приложения
 * - отображение изображения, заголовка и подзаголовка функционала
 * - кнопка перехода к следующему экрану
 */
class OnboardFragment : Fragment() {

//    инициализируем сам фрагмент
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboard, container, false)
    }

//    проводим настройки элементов фрагмента
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
/*
        // Настраиваем edge-to-edge отображение
        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.onboard)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

        val btn: Button = view.findViewById(R.id.btn_next)
        btn.setOnClickListener {
            (activity as? MainActivity)?.navigateToSignIn()
        }
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
