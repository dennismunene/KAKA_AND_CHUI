package com.game254studios.kakaandchui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.game254studios.kakaandchui.ads.AdManager
import com.game254studios.kakaandchui.analytics.AnalyticsManager
import com.game254studios.kakaandchui.billing.BillingManager
import com.game254studios.kakaandchui.config.RemoteConfigManager
import com.game254studios.kakaandchui.ui.theme.KakaTheme
import com.google.firebase.crashlytics.FirebaseCrashlytics

class MainActivity : ComponentActivity() {

    private lateinit var billingManager: BillingManager
    private lateinit var adManager: AdManager
    private lateinit var analyticsManager: AnalyticsManager
    private lateinit var remoteConfigManager: RemoteConfigManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Firebase Crashlytics — disable until user consent is obtained
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)

        analyticsManager = AnalyticsManager.getInstance(this)
        remoteConfigManager = RemoteConfigManager.getInstance()
        remoteConfigManager.fetchAndActivate()

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
