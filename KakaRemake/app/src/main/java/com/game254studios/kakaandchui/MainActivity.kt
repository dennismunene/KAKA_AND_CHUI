package com.game254studios.kakaandchui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.game254studios.kakaandchui.ads.AdManager
import com.game254studios.kakaandchui.analytics.AnalyticsManager
import com.game254studios.kakaandchui.audio.BackgroundMusicPlayer
import com.game254studios.kakaandchui.billing.BillingManager
import com.game254studios.kakaandchui.config.RemoteConfigManager
import com.game254studios.kakaandchui.ui.theme.KakaTheme
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var billingManager: BillingManager
    private lateinit var adManager: AdManager
    private lateinit var analyticsManager: AnalyticsManager
    private lateinit var remoteConfigManager: RemoteConfigManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Lightweight — just sets a flag, safe on main thread
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)

        // Create manager instances (constructors are lightweight)
        analyticsManager = AnalyticsManager.getInstance(this)
        remoteConfigManager = RemoteConfigManager.getInstance()
        billingManager = BillingManager(this)
        adManager = AdManager(this)

        // Render UI immediately — don't block on init
        enableEdgeToEdge()
        setContent {
            KakaTheme {
                KakaApp(
                    billingManager = billingManager,
                    adManager = adManager
                )
            }
        }

        // Defer all heavy initialization to after first frame
        lifecycleScope.launch(Dispatchers.Main) {
            // These all use callbacks internally so they're fine on Main
            // but we post them after setContent so the first frame renders fast
            remoteConfigManager.fetchAndActivate()
            billingManager.startConnection()
            adManager.initialize()
            adManager.loadInterstitial()
            adManager.loadRewarded()
        }

        // Music init does I/O — run off main thread entirely
        lifecycleScope.launch(Dispatchers.IO) {
            BackgroundMusicPlayer.start(this@MainActivity)
        }
    }

    override fun onPause() {
        super.onPause()
        BackgroundMusicPlayer.pause()
    }

    override fun onResume() {
        super.onResume()
        BackgroundMusicPlayer.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        BackgroundMusicPlayer.stop()
        billingManager.endConnection()
    }
}
