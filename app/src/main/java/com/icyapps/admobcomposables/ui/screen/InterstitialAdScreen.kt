package com.icyapps.admobcomposables.ui.screen

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.icyapps.admobcomposables.AdMobInterstitialAdHelper
import com.icyapps.admobcomposables.utils.Constants

@Composable
fun InterstitialAdScreen() {
    val context = LocalContext.current

    var adLoadError by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        AdMobInterstitialAdHelper.loadInterstitialAd(
            context,
            coroutineScope,
            onAdLoaded = {
                if (context is Activity) {
                    AdMobInterstitialAdHelper.showInterstitialAd(context)
                }
            },
            onAdFailedToLoad = { loadAdError ->
                adLoadError = "Failed to load ad: ${loadAdError.message}"
            },
            adUnitId = Constants.TEST_INTERSTITIAL_AD_UNIT_ID
        )
    }

    adLoadError?.let {
        InterstitialAdLoadError(errorMessage = it)
    }
}

@Composable
fun InterstitialAdLoadError(errorMessage: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = errorMessage,
            color = Color.Red,
            modifier = Modifier.padding(16.dp)
        )
    }
}