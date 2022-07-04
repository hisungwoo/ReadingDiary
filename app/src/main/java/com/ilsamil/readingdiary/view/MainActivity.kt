package com.ilsamil.readingdiary.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
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
    private var mInterstitialAd: InterstitialAd? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        MobileAds.initialize(this) {}

        var adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d("ttest", adError?.message)
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d("ttest", "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })


        binding.advTest.setOnClickListener {
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(this)
            } else {
                Log.d("ttest", "The interstitial ad wasn't ready yet.")
            }
        }

        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Log.d("ttest", "Ad was dismissed.")
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                Log.d("ttest", "Ad failed to show.")
            }

            override fun onAdShowedFullScreenContent() {
                Log.d("ttest", "Ad showed fullscreen content.")
                mInterstitialAd = null
            }
        }



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
                R.id.selBookFragment -> binding.bottomNav.visibility = View.GONE
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