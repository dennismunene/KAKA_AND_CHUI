package com.game254studios.kakaandchui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.game254studios.kakaandchui.ads.AdManager
import com.game254studios.kakaandchui.billing.BillingManager
import com.game254studios.kakaandchui.ui.theme.KakaTheme

class MainActivity : ComponentActivity() {

    private lateinit var billingManager: BillingManager
    private lateinit var adManager: AdManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        billingManager = BillingManager(this)
        billingManager.startConnection()

        adManager = AdManager(this)
        adManager.initialize()
        adManager.loadInterstitial()
        adManager.loadRewarded()

        enableEdgeToEdge()
        setContent {
            KakaTheme {
                KakaApp(
                    billingManager = billingManager,
                    adManager = adManager
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        billingManager.endConnection()
    }
}
