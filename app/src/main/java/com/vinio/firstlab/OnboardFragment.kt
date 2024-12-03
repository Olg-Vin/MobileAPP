package com.vinio.firstlab

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

/**
 * Стартовый экран (фрагмент):
 * - краткое описание функционала приложения
 * - отображение изображения, заголовка и подзаголовка функционала
 * - кнопка перехода к следующему экрану
 */
class OnboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn: Button = view.findViewById(R.id.btn_next)
        btn.setOnClickListener {
            Log.d("TAG", "Click on sing in button")
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
