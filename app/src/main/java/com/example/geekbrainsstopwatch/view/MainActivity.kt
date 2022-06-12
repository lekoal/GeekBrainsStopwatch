package com.example.geekbrainsstopwatch.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.geekbrainsstopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.activityMainContainer.id, MainScreenFragment.newInstance())
                .commit()
        }
    }
}