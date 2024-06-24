package com.icyapps.admobcomposables.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.gms.ads.AdSize
import com.icyapps.admobcomposables.AdMobBanner
import com.icyapps.admobcomposables.utils.Constants

@Composable
fun BannerAdsScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AdMobBanner(
            adUnitId = Constants.TEST_BANNER_AD_UNIT_ID,
            backgroundColor = Color.LightGray,
            padding = PaddingValues(16.dp),
            modifier = Modifier.padding(8.dp)
        )
        AdMobBanner(
            adSize = AdSize.MEDIUM_RECTANGLE,
            adUnitId = Constants.TEST_BANNER_AD_UNIT_ID,
            backgroundColor = Color.LightGray,
            padding = PaddingValues(16.dp),
            modifier = Modifier.padding(8.dp)
        )
        AdMobBanner(
            adSize = AdSize.FULL_BANNER,
            adUnitId = Constants.TEST_BANNER_AD_UNIT_ID,
            backgroundColor = Color.LightGray,
            padding = PaddingValues(16.dp),
            modifier = Modifier.padding(8.dp)
        )
    }
}