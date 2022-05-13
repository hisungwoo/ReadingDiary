package com.ilsamil.readingdiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.ilsamil.readingdiary.fragments.BooksFragment
import com.ilsamil.readingdiary.fragments.ChartFragment
import com.ilsamil.readingdiary.fragments.HomeFragment
import com.ilsamil.readingdiary.fragments.SettingFragment
import com.ilsamil.readingdiary.databinding.ActivityMainBinding
import com.ilsamil.readingdiary.model.SearchBookDto
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

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

        val retrofit = Retrofit.Builder()
                    .baseUrl("https://dapi.kakao.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

        val bookService = retrofit.create(BookService::class.java)


        bookService.getBookInfo("미움받을용기", "accuracy", 50, "title")
            .enqueue(object : Callback<SearchBookDto> {
                override fun onResponse(
                    call: Call<SearchBookDto>,
                    response: Response<SearchBookDto>
                ) {
                    Log.d("ttest", " 성공!! ")

                    if(response.isSuccessful.not()) {
                        return
                    }
                    response.body()?.let {
                        Log.d("ttest", "body 있음")

                        it.bookInfo.forEach { books ->
                            Log.d("ttest", books.toString())
                        }

                    }

                }

                override fun onFailure(call: Call<SearchBookDto>, t: Throwable) {
                    Log.d("ttest",t.toString())

                }


            })












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