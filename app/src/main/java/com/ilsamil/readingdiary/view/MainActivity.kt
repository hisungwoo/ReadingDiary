package com.ilsamil.readingdiary.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ilsamil.readingdiary.viewmodel.MainViewModel
import com.ilsamil.readingdiary.R
import com.ilsamil.readingdiary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object{
        private const val TAG = "MainActivity"
    }

    private val mainViewModel : MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        // bottom_nav 설정
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_host)
        if (navHostFragment != null) {
            navController = navHostFragment.findNavController()
        }
        binding.bottomNav.setupWithNavController(navController)


        // Navigation 탐색 리스너
        // 검색 Fragment 이동 시 Navigation 숨김 처리
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.searchFragment -> binding.bottomNav.visibility = View.GONE
                R.id.searchResultFragment -> binding.bottomNav.visibility = View.GONE
                else -> binding.bottomNav.visibility = View.VISIBLE
            }

//            if(destination.id == R.id.searchFragment) {
//                binding.bottomNav.visibility = View.GONE
//            } else {
//                binding.bottomNav.visibility = View.VISIBLE
//            }
        }


        setContentView(binding.root)
    }





}