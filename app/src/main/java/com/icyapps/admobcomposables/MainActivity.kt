package com.icyapps.admobcomposables

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.icyapps.admobcomposables.ui.theme.AdMobComposablesTheme

class MainActivity : androidx.activity.ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AdMobComposablesTheme {
                AdMobBanner()
            }
        }
    }
}