package com.icyapps.admobcomposables.interstitial

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

object AdMobInterstitialAdHelper {
    private const val TAG = "AdMobInterstitialAdHelper"
    private var interstitialAd: InterstitialAd? = null

    fun loadInterstitialAd(
        context: Context,
        adUnitId: String,
        onAdLoaded: () -> Unit,
        onAdFailedToLoad: (LoadAdError) -> Unit
    ) {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(context, adUnitId, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                interstitialAd = null
                Log.e(TAG, "Failed to load ad: ${adError.message}")
                onAdFailedToLoad(adError)
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                AdMobInterstitialAdHelper.interstitialAd = interstitialAd
                onAdLoaded()
                Log.d(TAG, "Ad loaded")
            }
        })
    }

    fun showInterstitialAd(context: Context) {
        interstitialAd?.show(context as Activity) ?: Log.e(
            TAG,
            "Attempted to show ad but it was not ready."
        )
    }
}