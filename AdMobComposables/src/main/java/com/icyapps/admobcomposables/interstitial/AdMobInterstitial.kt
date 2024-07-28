package com.icyapps.admobcomposables.interstitial

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun AdMobInterstitial(
    adUnitId: String,
    loadingContent: @Composable () -> Unit = { CircularProgressIndicator() },
    errorContent: @Composable (modifier: Modifier) -> Unit = {
        Text(text = "Cannot load ad", color = Color.Red)
    }
) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }
    var adLoadError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        AdMobInterstitialAdHelper.loadInterstitialAd(
            context = context,
            adUnitId = adUnitId,
            onAdLoaded = {
                isLoading = false
                adLoadError = null
                if (context is Activity) {
                    AdMobInterstitialAdHelper.showInterstitialAd(context)
                }
            },
            onAdFailedToLoad = { loadAdError ->
                isLoading = false
                adLoadError = loadAdError.message
            },
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            loadingContent()
        }

        adLoadError?.let {
            errorContent(Modifier.fillMaxWidth())
        }
    }
}