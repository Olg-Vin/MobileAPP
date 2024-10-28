package com.vinio.firstlab

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            addFragment(OnboardFragment())
        }
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container_view, fragment)
            .setReorderingAllowed(true)  // Важно для оптимизации порядка транзакций
            .addToBackStack(null)        // Добавляем в BackStack для обратной навигации
            .commit()
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, fragment)
            .setReorderingAllowed(true)
            .addToBackStack(null)
            .commit()
    }

    // Функция для удаления фрагмента
    private fun removeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .remove(fragment)
            .commit()
    }

    // Пример использования: переход на SignInFragment
    fun navigateToSignIn() {
        replaceFragment(SignInFragment())
    }

    // Пример использования: переход на SignUpFragment
    fun navigateToSignUp() {
        replaceFragment(SignUpFragment())
    }

    // Пример использования: переход на HomeFragment
    fun navigateToHome() {
        replaceFragment(HomeFragment())
    }
}