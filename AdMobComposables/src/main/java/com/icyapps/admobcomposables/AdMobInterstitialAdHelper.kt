package com.icyapps.admobcomposables

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

object AdMobInterstitialAdHelper {
    private const val TAG = "AdMobInterstitialAdHelper"
    private var interstitialAd: InterstitialAd? = null
    private const val MAX_RETRIES = 3
    private val RETRY_DELAY: Duration = 2.seconds

    fun loadInterstitialAd(
        context: Context,
        coroutineScope: CoroutineScope,
        adUnitId: String,
        onAdLoaded: () -> Unit,
        onAdFailedToLoad: (LoadAdError) -> Unit,
        attempt: Int = 1
    ) {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(context, adUnitId, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                interstitialAd = null
                Log.e(TAG, "Failed to load ad: ${adError.message}")

                if (attempt < MAX_RETRIES) {
                    retryLoadingAd(
                        context,
                        coroutineScope,
                        adUnitId,
                        onAdLoaded,
                        onAdFailedToLoad,
                        attempt + 1
                    )
                } else {
                    Log.d(TAG, "Failed to load ad after $MAX_RETRIES retries")
                    onAdFailedToLoad(adError)
                }
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                this@AdMobInterstitialAdHelper.interstitialAd = interstitialAd
                onAdLoaded()
                Log.d(TAG, "Ad loaded")
            }
        })
    }

    private fun retryLoadingAd(
        context: Context,
        coroutineScope: CoroutineScope,
        adUnitId: String,
        onAdLoaded: () -> Unit,
        onAdFailedToLoad: (LoadAdError) -> Unit,
        retryCount: Int
    ) {
        coroutineScope.launch {
            delay(RETRY_DELAY)
            loadInterstitialAd(
                context,
                coroutineScope,
                adUnitId,
                onAdLoaded,
                onAdFailedToLoad,
                retryCount
            )
        }
    }

    fun showInterstitialAd(context: Context) {
        interstitialAd?.show(context as Activity) ?: Log.e(
            TAG,
            "Attempted to show ad but it was not ready."
        )
    }
}