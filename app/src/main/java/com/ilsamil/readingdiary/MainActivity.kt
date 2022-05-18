package com.ilsamil.readingdiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ilsamil.readingdiary.databinding.ActivityMainBinding
import com.ilsamil.readingdiary.fragments.BooksFragment
import com.ilsamil.readingdiary.fragments.ChartFragment
import com.ilsamil.readingdiary.fragments.HomeFragment
import com.ilsamil.readingdiary.fragments.SettingFragment
import com.ilsamil.readingdiary.model.SearchBookDto
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    companion object{
        private const val TAG = "MainActivity"
    }

    val mainViewModel : MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private val homeFragment by lazy { HomeFragment.newInstance() }
    private val booksFragment by lazy { BooksFragment.newInstance() }
    private val chartFragment by lazy { ChartFragment.newInstance() }
    private val settingFragment by lazy { SettingFragment.newInstance() }
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

//        val retrofit = Retrofit.Builder()
//                    .baseUrl("https://dapi.kakao.com")
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()
//
//        val bookService = retrofit.create(BookService::class.java)


//        bookService.getBookInfo("미움받을용기", "accuracy", 50, "title")
//            .enqueue(object : Callback<SearchBookDto> {
//                override fun onResponse(
//                    call: Call<SearchBookDto>,
//                    response: Response<SearchBookDto>
//                ) {
//                    Log.d(TAG, " 성공!! ")
//
//                    if(response.isSuccessful.not()) {
//                        return
//                    }
//                    response.body()?.let {
//                        Log.d(TAG, "body 있음")
//
//                        it.bookInfo.forEach { books ->
//                            Log.d(TAG, books.toString())
//                        }
//
//                    }
//
//                }
//                override fun onFailure(call: Call<SearchBookDto>, t: Throwable) {
//                    Log.d("ttest",t.toString())
//
//                }
//            })

        setContentView(binding.root)
    }



}