package com.icyapps.admobcomposables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

@Composable
fun AdMobBanner(
    modifier: Modifier = Modifier,
    adSize: AdSize = AdSize.BANNER,
    adUnitId: String,
    backgroundColor: Color = Color.Transparent,
    padding: PaddingValues = PaddingValues(0.dp),
    loadingContent: @Composable () -> Unit = { CircularProgressIndicator() },
    errorContent: @Composable (modifier: Modifier) -> Unit = {
        Text(text = "Cannot load ad", color = Color.Red)
    }
) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }
    var adLoadError by remember { mutableStateOf<String?>(null) }

    val bannerHeight =
        adSize.height.dp + padding.calculateTopPadding() + padding.calculateBottomPadding()
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(bannerHeight)
            .background(backgroundColor)
            .padding(padding),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            loadingContent()
        }

        adLoadError?.let {
            errorContent(Modifier.fillMaxWidth().height(adSize.height.dp))
        } ?: AndroidView(
            modifier = Modifier
                .fillMaxSize(),
            factory = {
                AdView(context).apply {
                    setAdSize(adSize)
                    this.adUnitId = adUnitId
                    adListener = object : AdListener() {
                        override fun onAdLoaded() {
                            super.onAdLoaded()
                            isLoading = false
                            adLoadError = null
                        }

                        override fun onAdFailedToLoad(adError: LoadAdError) {
                            super.onAdFailedToLoad(adError)
                            isLoading = false
                            adLoadError = adError.message
                            Log.e("AdMobBanner", adError.message)
                        }
                    }
                    loadAd(AdRequest.Builder().build())
                }
            }
        )
    }
}