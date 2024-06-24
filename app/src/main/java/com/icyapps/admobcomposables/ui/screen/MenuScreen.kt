package com.icyapps.admobcomposables.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MenuScreen(onBannerAdsClick: () -> Unit, onInterstitialAdScreenClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Samples", style = MaterialTheme.typography.titleLarge)
        Button(onClick = onBannerAdsClick) {
            Text(text = "Banner ads")
        }
        Button(onClick = onInterstitialAdScreenClick) {
            Text(text = "Interstitial ad")
        }
    }
}