package com.icyapps.admobcomposables.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.icyapps.admobcomposables.ui.navigation.destination.BannerAdsDestination
import com.icyapps.admobcomposables.ui.navigation.destination.InterstitialAdDestination
import com.icyapps.admobcomposables.ui.navigation.destination.MenuDestination
import com.icyapps.admobcomposables.ui.screen.BannerAdsScreen
import com.icyapps.admobcomposables.ui.screen.InterstitialAdScreen
import com.icyapps.admobcomposables.ui.screen.MenuScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = MenuDestination
    ) {
        composable<MenuDestination> {
            MenuScreen(onBannerAdsClick = {
                navController.navigate(BannerAdsDestination)
            }, onInterstitialAdScreenClick = {
                navController.navigate(InterstitialAdDestination)
            })
        }
        composable<BannerAdsDestination> {
            BannerAdsScreen()
        }

        composable<InterstitialAdDestination> {
            InterstitialAdScreen()
        }
    }
}