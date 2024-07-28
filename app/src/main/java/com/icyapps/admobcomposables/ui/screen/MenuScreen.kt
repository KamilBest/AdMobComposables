package com.icyapps.admobcomposables.ui.screen

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.ump.FormError
import com.icyapps.admobcomposables.ump.GoogleMobileAdsConsentManager

@Composable
fun MenuScreen(onBannerAdsClick: () -> Unit, onInterstitialAdScreenClick: () -> Unit) {
    val context = LocalContext.current as Activity
    var canRequestAds by remember { mutableStateOf(false) }
    var consentError by remember { mutableStateOf<FormError?>(null) }
    var isCheckingConsent by remember { mutableStateOf(true) }
    var isGatheringConsent by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        GoogleMobileAdsConsentManager.getInstance(context)
            .checkConsent(context) { canRequestAdsResult, error ->
                canRequestAds = canRequestAdsResult
                consentError = error
                isCheckingConsent = false
            }
    }

    if (isCheckingConsent || isGatheringConsent) {
        // Show a loading indicator while checking or gathering consent
        CircularProgressIndicator()
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!canRequestAds) {
                Text(text = "YOU CAN'T!", style = MaterialTheme.typography.titleLarge)
            }

            Text(text = "Samples", style = MaterialTheme.typography.titleLarge)
            Button(onClick = onBannerAdsClick, enabled = canRequestAds) {
                Text(text = "Banner ads")
            }
            Button(onClick = onInterstitialAdScreenClick, enabled = canRequestAds) {
                Text(text = "Interstitial ad")
            }
            Button(onClick = {
                isGatheringConsent = true
                GoogleMobileAdsConsentManager.getInstance(context)
                    .gatherConsent(context) { canRequestAdsResult, error ->
                        consentError = error
                        isGatheringConsent = false
                        if (error == null) {
                            canRequestAds = canRequestAdsResult
                        }
                    }
            }) {
                Text(text = "REQUEST CONSENT")
            }
        }
    }
}