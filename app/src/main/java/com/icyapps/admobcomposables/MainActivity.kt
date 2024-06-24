package com.icyapps.admobcomposables

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.icyapps.admobcomposables.AdMobInitializer.initializeAdMob
import com.icyapps.admobcomposables.ui.navigation.AppNavigation

class MainActivity : androidx.activity.ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initializeAdMob()

        setContent {
            SampleApp()
        }
    }
}

@Composable
fun SampleApp() {
    Scaffold(modifier = Modifier.fillMaxSize()) {
        AppNavigation(modifier = Modifier.padding(it))
    }
}