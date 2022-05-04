package com.example.readingdiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.readingdiary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val mainViewModel : MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private val homeFragment by lazy { HomeFragment.newInstance() }
    private val booksFragment by lazy { BooksFragment.newInstance() }
    private val chartFragment by lazy { ChartFragment.newInstance() }
    private val settingFragment by lazy { SettingFragment.newInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        supportFragmentManager.beginTransaction().add(R.id.main_fragment_view, homeFragment)

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.menu_home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_fragment_view, homeFragment).commit()
                }
                R.id.menu_books -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_fragment_view, booksFragment).commit()
                }
                R.id.menu_chart -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_fragment_view, chartFragment).commit()
                }
                R.id.menu_setting -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_fragment_view, settingFragment).commit()
                }
            }
            true
        }

        setContentView(binding.root)
    }
}