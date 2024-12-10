package com.vinio.firstlab.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vinio.firstlab.R
import com.vinio.firstlab.databinding.FragmentHomeBinding

/**
 * Главный экран (фрагмент):
 * - отображение списка чатов с именем отправителя,
 * его профилем, последним сообщением и временем отправки сообщения
 * - использовать фиктивные данные для чатов
 * */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = (_binding ?: RuntimeException("FragmentHomeBinding == null")) as FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toRecycle.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_recycle)
        }
        Log.d("TAG", "Fragment onViewCreated")
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
