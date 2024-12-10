package com.vinio.firstlab.saves

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.vinio.firstlab.databinding.FragmentSettingsBinding
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.lang.Float

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding
        get() = (_binding
            ?: RuntimeException("FragmentSettingsBinding == null")) as FragmentSettingsBinding

    lateinit var sharedPreferences: SharedPreferences
    val Context.dataStore by preferencesDataStore(name = "settings")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedFun()
        storeFun()
    }

    private fun sharedFun() {
        sharedPreferences = requireContext().getSharedPreferences("myShared", Context.MODE_PRIVATE)

        val savedText = sharedPreferences.getString("user_email", "")
        binding.changeEmail.setText(savedText)
        binding.saveEmailButton.setOnClickListener {
            val textToSave = binding.changeEmail.text.toString()

            if (textToSave.isNotBlank()) {
                sharedPreferences.edit()
                    .putString("user_email", textToSave)
                    .apply()

                Toast.makeText(requireContext(), "Данные сохранены", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Поле не должно быть пустым", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        val isNotificationsEnabled = sharedPreferences.getBoolean("notifications_enabled", true)
        binding.themeSwitch.isChecked = isNotificationsEnabled
        binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit()
                .putBoolean("notifications_enabled", isChecked)
                .apply()
        }


        val radioGroup = binding.languageGroup
        val savedLanguageId =
            sharedPreferences.getInt("selected_language", radioGroup.checkedRadioButtonId)
        radioGroup.check(savedLanguageId)
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            sharedPreferences.edit().putInt("selected_language", checkedId).apply()
        }

    }

    private fun storeFun() {
        lifecycleScope.launch {
            requireContext().dataStore.data
                .map { preferences ->
                    preferences[stringPreferencesKey("font_size")] ?: "16"
                }.collect { e ->
                    run {
                        binding.changeFontSize.setText(e)
                        binding.fontSizePreview.textSize = e.toFloat()
                    }
                }
        }
        binding.saveSizeButton.setOnClickListener {
            binding.fontSizePreview.textSize = binding.changeFontSize.text.toString().toFloat()
            lifecycleScope.launch {
                requireContext().dataStore.edit { preferences ->
                    preferences[stringPreferencesKey("font_size")] =
                        binding.changeFontSize.text.toString()
                }
            }
        }

        lifecycleScope.launch {
            requireContext().dataStore.data
                .map { preferences ->
                    preferences[stringPreferencesKey("notifier")] ?: ""
                }.collect { e -> binding.checkNotifier.isChecked = e.toBoolean() }
        }
        binding.checkNotifier.setOnCheckedChangeListener { _, isChecked ->
            lifecycleScope.launch {
                requireContext().dataStore.edit { preferences ->
                    preferences[stringPreferencesKey("notifier")] = isChecked.toString()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("TAG", "Fragment settings onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TAG", "Fragment settings onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TAG", "Fragment settings onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("TAG", "Fragment settings onDestroyView")
    }
}