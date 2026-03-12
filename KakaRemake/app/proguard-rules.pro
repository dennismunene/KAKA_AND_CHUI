# ProGuard / R8 rules for Kaka and Chui

# ── Global optimizations ──
-allowaccessmodification
-repackageclasses

# Keep application classes
-keep class com.game254studios.** { *; }

# ── AndroidX ──
-keep class androidx.** { *; }
-dontwarn androidx.**

# ── Jetpack Compose ──
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**
# Allow R8 to optimize Compose runtime internals
-dontwarn androidx.compose.runtime.**

# ── Kotlin ──
-keep class kotlin.** { *; }
-dontwarn kotlin.**
-keepclassmembers class **$WhenMappings {
    <fields>;
}

# ── Room (entities, DAOs, generated code) ──
-keep class * extends androidx.room.RoomDatabase { *; }
-keep @androidx.room.Entity class * { *; }
-keep @androidx.room.Dao class * { *; }
-keepclassmembers class * {
    @androidx.room.* <methods>;
}
-dontwarn androidx.room.**

# ── Firebase ──
-keep class com.google.firebase.** { *; }
-dontwarn com.google.firebase.**
-keep class com.google.android.gms.internal.firebase-perf.** { *; }
-dontwarn com.google.android.gms.internal.**

# ── AdMob (Google Mobile Ads) ──
-keep class com.google.android.gms.ads.** { *; }
-dontwarn com.google.android.gms.ads.**

# ── Google Play Billing ──
-keep class com.android.vending.billing.** { *; }
-keep class com.android.billingclient.** { *; }
-dontwarn com.android.billingclient.**

# ── Lottie ──
-dontwarn com.airbnb.lottie.**
-keep class com.airbnb.lottie.** { *; }

# Keep custom font references
-keep class * extends android.graphics.Typeface

# Remove logging in release builds
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int d(...);
    public static int i(...);
}
