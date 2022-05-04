package com.example.readingdiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.readingdiary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val mainViewModel : MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)


        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.menu_home -> {
                }
                R.id.menu_books -> {
                }
                R.id.menu_chart -> {
                }
                R.id.menu_setting -> {
                }
            }
            true
        }

        setContentView(binding.root)
    }
}