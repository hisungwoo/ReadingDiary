package com.ilsamil.readingdiary.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.ilsamil.readingdiary.BuildConfig
import com.ilsamil.readingdiary.R
import com.ilsamil.readingdiary.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object{
        private const val TAG = "MainActivity_1sam1"
        var adsCnt = 0
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var mInterstitialAd: InterstitialAd? = null
    private var adRequest : AdRequest? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        MobileAds.initialize(this) {}
        adRequest = AdRequest.Builder().build()
        setAd()


        // bottom_nav 설정
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_host)
        if (navHostFragment != null) navController = navHostFragment.findNavController()
        binding.bottomNav.setupWithNavController(navController)


        // 검색 Fragment 이동 시 Navigation 숨김 처리
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.searchFragment -> {
                    binding.bottomNav.visibility = View.GONE
                    adsEvent()
                }
                R.id.searchResultFragment -> {
                    binding.bottomNav.visibility = View.GONE
                    adsEvent()
                }
                R.id.selBookFragment -> {
                    binding.bottomNav.visibility = View.GONE
                    adsEvent()
                }
                R.id.writeReadingFragment -> {
                    binding.bottomNav.visibility = View.GONE
                    adsEvent()
                }
                else -> {
                    binding.bottomNav.visibility = View.VISIBLE
                    adsEvent()
                }
            }
        }
        setContentView(binding.root)
    }

    private fun adsEvent() {
        adsCnt++
        if (adsCnt == 10) {
            adsCnt = 0
            if (mInterstitialAd != null) {
                mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
                    override fun onAdClicked() {
                        // 광고에 대한 클릭이 기록될 때 호출
                        Log.d(TAG, "Ad was clicked.")
                    }

                    override fun onAdDismissedFullScreenContent() {
                        // 광고가 닫힐 때 호출
                        Log.d(TAG, "Ad dismissed fullscreen content.")
                        mInterstitialAd = null
                        setAd()
                    }

                    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                        // 광고가 표시되지 않을 때 호출
                        Log.e(TAG, "Ad failed to show fullscreen content.")
                        mInterstitialAd = null
                    }

                    override fun onAdImpression() {
                        // 광고에 대한 노출이 기록될 때 호출
                        Log.d(TAG, "Ad recorded an impression.")
                    }

                    override fun onAdShowedFullScreenContent() {
                        // 광고가 표시될 때 호출
                        Log.d(TAG, "Ad showed fullscreen content.")
                    }
                }
                mInterstitialAd?.show(this)
            } else {
                Log.d(TAG, "The interstitial ad wasn't ready yet.")
            }
        }
    }

    fun setAd() {
        InterstitialAd.load(this,"${BuildConfig.adsId}", adRequest!!, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                // code 3 : 광고 요청 구현에 문제는 없고 성공했지만, 인벤토리에 광고가 없음
                Log.d(TAG, "code : ${adError?.code.toString()}, message : ${adError?.message}")
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })
    }

}