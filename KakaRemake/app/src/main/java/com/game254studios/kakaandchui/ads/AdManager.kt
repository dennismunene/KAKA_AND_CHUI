package com.game254studios.kakaandchui.ads

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

class AdManager(private val context: Context) {

    // Test ad unit IDs — replace with real ones before production
    companion object {
        const val BANNER_ID = "ca-app-pub-3940256099942544/6300978111"
        const val INTERSTITIAL_ID = "ca-app-pub-3940256099942544/1033173712"
        const val REWARDED_ID = "ca-app-pub-3940256099942544/5224354917"
        const val INTERSTITIAL_COOLDOWN_MS = 5 * 60 * 1000L // 5 minutes

        /** Builds an AdRequest inheriting COPPA child-directed global config */
        fun buildChildDirectedAdRequest(): AdRequest = AdRequest.Builder().build()
    }

    private var interstitialAd: InterstitialAd? = null
    private var rewardedAd: RewardedAd? = null
    private var lastInterstitialTime = 0L

    fun initialize() {
        // COPPA compliance: set child-directed treatment BEFORE initialization
        val requestConfig = RequestConfiguration.Builder()
            .setTagForChildDirectedTreatment(RequestConfiguration.TAG_FOR_CHILD_DIRECTED_TREATMENT_TRUE)
            .setMaxAdContentRating(RequestConfiguration.MAX_AD_CONTENT_RATING_G)
            .setTagForUnderAgeOfConsent(RequestConfiguration.TAG_FOR_UNDER_AGE_OF_CONSENT_TRUE)
            .build()
        MobileAds.setRequestConfiguration(requestConfig)
        MobileAds.initialize(context) {}
    }

    fun getAdRequest(): AdRequest {
        return AdRequest.Builder().build()
    }

    fun loadInterstitial() {
        InterstitialAd.load(context, INTERSTITIAL_ID, getAdRequest(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) { interstitialAd = ad }
                override fun onAdFailedToLoad(error: LoadAdError) { interstitialAd = null }
            })
    }

    fun showInterstitialIfReady(activity: Activity): Boolean {
        val now = System.currentTimeMillis()
        if (now - lastInterstitialTime < INTERSTITIAL_COOLDOWN_MS) return false
        interstitialAd?.let { ad ->
            ad.show(activity)
            lastInterstitialTime = now
            interstitialAd = null
            loadInterstitial()
            return true
        }
        return false
    }

    fun loadRewarded() {
        RewardedAd.load(context, REWARDED_ID, getAdRequest(),
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedAd) { rewardedAd = ad }
                override fun onAdFailedToLoad(error: LoadAdError) { rewardedAd = null }
            })
    }

    fun showRewarded(activity: Activity, onReward: (Int) -> Unit) {
        rewardedAd?.let { ad ->
            ad.show(activity) { reward ->
                onReward(reward.amount)
            }
            rewardedAd = null
            loadRewarded()
        }
    }

    val isRewardedReady: Boolean get() = rewardedAd != null
}
