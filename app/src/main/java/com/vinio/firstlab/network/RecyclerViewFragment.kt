package com.vinio.firstlab.network

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.vinio.firstlab.AppDatabase
import com.vinio.firstlab.databinding.FragmentRecyclerViewBinding
import com.vinio.firstlab.databinding.RecyclerItemBinding
import com.vinio.firstlab.entity.Character
import com.vinio.firstlab.entity.CharacterEntity
import com.vinio.firstlab.entity.CharacterRepository
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
    lateinit var sharedPreferences: SharedPreferences

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

        val daoRep = CharacterRepository(
            AppDatabase.DatabaseProvider.getDatabase(requireContext()).characterDao())

        dataSource = CharacterDataSource(requireContext())

        binding.refreshButton.setOnClickListener {
            var pageNumber = binding.pageNumberEditText.text.toString()
            if (pageNumber.isEmpty()) {
                pageNumber = getSavedPage()
            }
            CoroutineScope(Dispatchers.Main).launch {
                refreshCharacterList(pageNumber.toInt(), daoRep)
            }
        }

        binding.loadPageButton.setOnClickListener {
            val pageNumber = binding.pageNumberEditText.text.toString()
            CoroutineScope(Dispatchers.Main).launch {
                loadCharactersFromPage(pageNumber.toInt(), daoRep)
            }
        }



        CoroutineScope(Dispatchers.Main).launch {
            if (daoRep.getAllCharacters().isNotEmpty()) {
                Toast.makeText(requireContext(), "Данные есть в бд", Toast.LENGTH_SHORT).show()
                val characters = daoRep.getAllCharacters()
                setupCharacterAdapter(characters.toSimple(), daoRep)
            } else {
                Toast.makeText(requireContext(), "Данных нет, делаем запрос", Toast.LENGTH_SHORT).show()
                loadCharactersFromPage(2, daoRep)
            }
        }
        CoroutineScope(Dispatchers.Main).launch {
            daoRep.getAllCharactersFlow().collect { characters ->
                setupCharacterAdapter(characters.toSimple(), daoRep)
            }
        }
    }

    private fun setupCharacterAdapter(characters: List<Character>, daoRep: CharacterRepository) {
        adapter = CharacterAdapter(
            characterList = characters,
            onUpdateClick = { character ->
                handleUpdateCharacter(character, daoRep)
            },
            onDeleteClick = { character ->
                handleDeleteCharacter(character, daoRep)
            }
        )
        binding.recycle.adapter = adapter
    }

    private fun handleUpdateCharacter(character: Character, daoRep: CharacterRepository) {
        Toast.makeText(requireContext(), "Обновление: ${character.name}", Toast.LENGTH_SHORT).show()
        CoroutineScope(Dispatchers.Main).launch {
            if (character.name?.isEmpty() == true){
                Toast.makeText(requireContext(), "Имя отсутствует: ${character.name}", Toast.LENGTH_SHORT).show()
            } else {
                val characterEntity = character.name?.let { daoRep.getCharacterByName(it) }
                if (characterEntity != null) {
                    Log.d("[UPDATE]", characterEntity.toString())
                    characterEntity.name = characterEntity.name?.uppercase()
                    daoRep.updateCharacter(characterEntity)
                }
            }
        }
    }

    private fun handleDeleteCharacter(character: Character, daoRep: CharacterRepository) {
        Toast.makeText(requireContext(), "Удаление: ${character.name}", Toast.LENGTH_SHORT).show()
        CoroutineScope(Dispatchers.Main).launch {
            if (character.name?.isEmpty() == true){
                Toast.makeText(requireContext(), "Имя отсутствует: ${character.name}", Toast.LENGTH_SHORT).show()
            } else {
                val characterEntity = character.name?.let { daoRep.getCharacterByName(it) }
                if (characterEntity != null) {
                    Log.d("[DELETE]", characterEntity.toString())
                    daoRep.deleteCharacter(characterEntity)
                }
            }
        }
    }


    fun savePage(page: Int) {
        sharedPreferences = requireContext().getSharedPreferences("myShared", Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .putString("page", page.toString())
            .apply()
    }
    fun getSavedPage(): String {
        sharedPreferences = requireContext().getSharedPreferences("myShared", Context.MODE_PRIVATE)
        return sharedPreferences.getString("page", "")!!
    }

    private suspend fun loadCharactersFromPage(page: Int, daoRep: CharacterRepository) {
        try {
            savePage(page)
            val characters = dataSource.getSomeCharacters(page)
            daoRep.deleteAllCharacters()
            daoRep.insertCharacters(characters.getOrThrow().toEntityList())

            saveFileToDownloads(characters)
            setupCharacterAdapter(characters.getOrThrow(), daoRep)
            Toast.makeText(requireContext(), "Данные успешно загружены", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private suspend fun refreshCharacterList(page: Int, daoRep: CharacterRepository) {
        try {
            val characters = dataSource.getSomeCharacters(page)
            daoRep.deleteAllCharacters()
            daoRep.insertCharacters(characters.getOrThrow().toEntityList())

            setupCharacterAdapter(characters.getOrThrow(), daoRep)
            binding.recycle.adapter = adapter

            Toast.makeText(requireContext(), "Список обновлен", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Ошибка обновления списка: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }


    private fun Character.toEntity(): CharacterEntity {
        return CharacterEntity(
            name = this.name,
            culture = this.culture,
            born = this.born,
            titles = this.titles,
            aliases = this.aliases,
            playedBy = this.playedBy
        )
    }
    private fun CharacterEntity.toSimple(): Character {
        return Character(
            name = this.name,
            culture = this.culture,
            born = this.born,
            titles = this.titles,
            aliases = this.aliases,
            playedBy = this.playedBy
        )
    }

    private fun List<Character>.toEntityList(): List<CharacterEntity> {
        return this.map { it.toEntity() }
    }
    private fun List<CharacterEntity>.toSimple(): List<Character> {
        return this.map { it.toSimple() }
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