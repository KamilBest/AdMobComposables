package com.icyapps.admobcomposables

import android.content.Context
import com.google.android.gms.ads.MobileAds

object AdMobInitializer {
    fun Context.initializeAdMob() {
        MobileAds.initialize(this)
    }
}