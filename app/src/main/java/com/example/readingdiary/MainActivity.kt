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
        setContentView(R.layout.activity_main)

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.i1 -> {
                }
                R.id.i2 -> {
                }
                R.id.i3 -> {
                }
                R.id.i4 -> {
                }
            }
            true
        }

    }
}