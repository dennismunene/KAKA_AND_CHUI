package com.game254studios.kakaandchui.data.model

data class AchievementDef(
    val id: String,
    val name: String,
    val description: String,
    val icon: String
)

val ALL_ACHIEVEMENTS = listOf(
    AchievementDef("first_steps", "First Steps", "Complete your first lesson", "🐣"),
    AchievementDef("vowel_master", "Vowel Master", "3 stars on Vokali quiz", "🅰️"),
    AchievementDef("word_wizard", "Word Wizard", "3 stars on Maneno quiz", "📝"),
    AchievementDef("number_ninja", "Number Ninja", "3 stars on Tarakimu quiz", "🔢"),
    AchievementDef("counting_champion", "Counting Champion", "3 stars on Tarakimu 11-20 quiz", "🔟"),
    AchievementDef("shape_shifter", "Shape Shifter", "3 stars on Maumbo quiz", "🔷"),
    AchievementDef("rainbow_warrior", "Rainbow Warrior", "3 stars on Rangi quiz", "🌈"),
    AchievementDef("week_warrior", "Week Warrior", "7-day streak", "🔥"),
    AchievementDef("consistent_learner", "Consistent Learner", "30-day streak", "⭐"),
    AchievementDef("kaka_best_friend", "Kaka's Best Friend", "Complete all modules", "🏆")
)
