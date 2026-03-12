package com.game254studios.kakaandchui.analytics

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

/**
 * COPPA-compliant analytics wrapper.
 * No PII or user-ID tracking — only gameplay events with anonymous parameters.
 */
class AnalyticsManager(context: Context) {

    private val analytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(context).apply {
        // Disable user-ID and personalized ads for COPPA compliance
        setAnalyticsCollectionEnabled(true)
    }

    fun logModuleStarted(moduleName: String) {
        analytics.logEvent("module_started", Bundle().apply {
            putString("module_name", moduleName)
        })
    }

    fun logQuizCompleted(moduleName: String, score: Int, total: Int, stars: Int) {
        analytics.logEvent("quiz_completed", Bundle().apply {
            putString("module_name", moduleName)
            putInt("score", score)
            putInt("total_questions", total)
            putInt("stars", stars)
        })
    }

    fun logAchievementEarned(achievementId: String, achievementName: String) {
        analytics.logEvent("achievement_earned", Bundle().apply {
            putString("achievement_id", achievementId)
            putString("achievement_name", achievementName)
        })
    }

    fun logLearnSession(moduleName: String, itemsViewed: Int) {
        analytics.logEvent("learn_session", Bundle().apply {
            putString("module_name", moduleName)
            putInt("items_viewed", itemsViewed)
        })
    }

    fun logAdWatched(adType: String) {
        analytics.logEvent("ad_watched", Bundle().apply {
            putString("ad_type", adType)
        })
    }

    fun logSubscriptionStarted(productId: String) {
        analytics.logEvent("subscription_started", Bundle().apply {
            putString("product_id", productId)
        })
    }

    companion object {
        @Volatile
        private var instance: AnalyticsManager? = null

        fun getInstance(context: Context): AnalyticsManager {
            return instance ?: synchronized(this) {
                instance ?: AnalyticsManager(context.applicationContext).also { instance = it }
            }
        }
    }
}
