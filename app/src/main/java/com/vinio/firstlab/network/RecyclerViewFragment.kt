package com.vinio.firstlab.network

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.vinio.firstlab.databinding.FragmentRecyclerViewBinding
import com.vinio.firstlab.entity.Character
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class RecyclerViewFragment : Fragment() {
    private var _binding: FragmentRecyclerViewBinding? = null
    private val binding: FragmentRecyclerViewBinding
        get() = (_binding
            ?: RuntimeException("FragmentRecyclerViewBinding == null")) as FragmentRecyclerViewBinding

    private lateinit var dataSource: CharacterDataSource
    private lateinit var adapter: CharacterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecyclerViewBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycle.layoutManager = LinearLayoutManager(requireContext())

        dataSource = CharacterDataSource(requireContext())

        CoroutineScope(Dispatchers.Main).launch {
            val characters = dataSource.getSomeCharacters()
            saveFileToDownloads(characters)
            Log.d("[REQUEST_KTOR]", characters.toString())
            adapter = CharacterAdapter(characters.getOrNull()!!)
            binding.recycle.adapter = adapter
        }
    }
    private val fileName = "2_character_list.txt"
    private fun saveFileToDownloads(characters: Result<List<Character>>) {
        val downloadDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

        val file = File(downloadDir, fileName)

        if (file.exists()) {
            val deleted = file.delete()
            if (deleted) {
                Toast.makeText(requireContext(), "Файл удален", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Не удалось удалить файл", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        try {
            val outputStream = FileOutputStream(file)
            val content = characters.getOrNull().toString()
            println(characters.getOrNull().toString())
            outputStream.write(content.toByteArray())
            outputStream.close()
            Toast.makeText(
                requireContext(),
                "Файл успешно сохранен в Downloads",
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Ошибка при сохранении файла", Toast.LENGTH_SHORT)
                .show()
        }
    }


    override fun onStart() {
        super.onStart()
        Log.d("TAG", "Fragment recycle onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TAG", "Fragment recycle onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TAG", "Fragment recycle onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d("TAG", "Fragment recycle onDestroyView")
    }
}