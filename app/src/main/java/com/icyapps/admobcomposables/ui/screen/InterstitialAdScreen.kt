package com.icyapps.admobcomposables.ui.screen

import androidx.compose.runtime.Composable
import com.icyapps.admobcomposables.interstitial.AdMobInterstitial
import com.icyapps.admobcomposables.utils.Constants

@Composable
fun InterstitialAdScreen() {
    AdMobInterstitial(Constants.TEST_INTERSTITIAL_AD_UNIT_ID)
}