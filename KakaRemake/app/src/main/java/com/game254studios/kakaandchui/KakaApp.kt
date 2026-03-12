package com.game254studios.kakaandchui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.game254studios.kakaandchui.ads.AdManager
import com.game254studios.kakaandchui.billing.BillingManager
import com.game254studios.kakaandchui.navigation.NavGraph

@Composable
fun KakaApp(
    billingManager: BillingManager,
    adManager: AdManager
) {
    val navController = rememberNavController()
    NavGraph(
        navController = navController,
        billingManager = billingManager,
        adManager = adManager
    )
}
