package com.game254studios.kakaandchui.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.game254studios.kakaandchui.data.model.Module
import com.game254studios.kakaandchui.ui.screens.HomeScreen
import com.game254studios.kakaandchui.ui.screens.LearnScreen
import com.game254studios.kakaandchui.ui.screens.MemoryMatchScreen
import com.game254studios.kakaandchui.ui.screens.ParentZoneScreen
import com.game254studios.kakaandchui.ui.screens.QuizResultScreen
import com.game254studios.kakaandchui.ui.screens.QuizScreen
import com.game254studios.kakaandchui.ui.screens.PrivacyPolicyScreen
import com.game254studios.kakaandchui.ui.screens.SoundMatchScreen
import com.game254studios.kakaandchui.ui.screens.SubscriptionScreen
import com.game254studios.kakaandchui.ui.screens.SplashScreen
import com.game254studios.kakaandchui.ads.AdManager
import com.game254studios.kakaandchui.billing.BillingManager
import com.game254studios.kakaandchui.viewmodel.HomeViewModel
import com.game254studios.kakaandchui.viewmodel.MemoryMatchViewModel
import com.game254studios.kakaandchui.viewmodel.QuizViewModel
import com.game254studios.kakaandchui.viewmodel.SoundMatchViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

object Routes {
    const val SPLASH = "splash"
    const val HOME = "home"
    const val LEARN = "learn/{moduleId}"
    const val QUIZ = "quiz/{moduleId}"
    const val QUIZ_RESULT = "quiz_result/{moduleId}/{score}/{total}/{xpEarned}/{coinsEarned}"

    const val MEMORY_MATCH = "memory_match/{moduleId}"
    const val SOUND_MATCH = "sound_match/{moduleId}"

    fun learn(module: Module) = "learn/${module.name}"
    fun quiz(module: Module) = "quiz/${module.name}"
    fun memoryMatch(module: Module) = "memory_match/${module.name}"
    fun soundMatch(module: Module) = "sound_match/${module.name}"
    fun quizResult(module: Module, score: Int, total: Int, xpEarned: Int, coinsEarned: Int) =
        "quiz_result/${module.name}/$score/$total/$xpEarned/$coinsEarned"

    const val PARENT_ZONE = "parent_zone"
    const val PRIVACY_POLICY = "privacy_policy"
    const val SUBSCRIPTION = "subscription"
}

@Composable
fun NavGraph(
    navController: NavHostController,
    billingManager: BillingManager,
    adManager: AdManager
) {
    val homeViewModel: HomeViewModel = viewModel()
    val isPremium by billingManager.isPremium.collectAsState()
    val context = LocalContext.current
    val activity = context as? Activity

    // Show an interstitial if the user is on the free tier and cooldown has elapsed
    fun tryShowInterstitial() {
        if (!isPremium && activity != null) {
            adManager.showInterstitialIfReady(activity)
        }
    }

    NavHost(navController = navController, startDestination = Routes.SPLASH) {

        composable(Routes.SPLASH) {
            SplashScreen(onSplashFinished = {
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.SPLASH) { inclusive = true }
                }
            })
        }

        composable(Routes.HOME) {
            homeViewModel.loadState()
            HomeScreen(
                onModuleClick = { module ->
                    navController.navigate(Routes.learn(module))
                },
                homeViewModel = homeViewModel,
                onNavigateToParentZone = {
                    navController.navigate(Routes.PARENT_ZONE)
                },
                isPremium = isPremium
            )
        }

        composable(
            route = Routes.LEARN,
            arguments = listOf(navArgument("moduleId") { type = NavType.StringType })
        ) { backStackEntry ->
            val moduleId = backStackEntry.arguments?.getString("moduleId") ?: return@composable
            val module = Module.valueOf(moduleId)
            LearnScreen(
                module = module,
                onBack = {
                    // Interstitial on leaving a learning session
                    tryShowInterstitial()
                    navController.popBackStack()
                },
                onStartQuiz = {
                    navController.navigate(Routes.quiz(module)) {
                        popUpTo(Routes.learn(module)) { inclusive = true }
                    }
                },
                onStartMemoryMatch = {
                    navController.navigate(Routes.memoryMatch(module))
                },
                onStartSoundMatch = {
                    navController.navigate(Routes.soundMatch(module))
                }
            )
        }

        composable(
            route = Routes.QUIZ,
            arguments = listOf(navArgument("moduleId") { type = NavType.StringType })
        ) { backStackEntry ->
            val moduleId = backStackEntry.arguments?.getString("moduleId") ?: return@composable
            val module = Module.valueOf(moduleId)
            val quizViewModel: QuizViewModel = viewModel()

            QuizScreen(
                module = module,
                onBack = { navController.popBackStack() },
                onQuizFinished = { score, total, xpEarned, coinsEarned ->
                    // Interstitial between quiz and results screen
                    tryShowInterstitial()
                    navController.navigate(
                        Routes.quizResult(module, score, total, xpEarned, coinsEarned)
                    ) {
                        popUpTo(Routes.HOME)
                    }
                },
                quizViewModel = quizViewModel
            )
        }

        composable(
            route = Routes.MEMORY_MATCH,
            arguments = listOf(navArgument("moduleId") { type = NavType.StringType })
        ) { backStackEntry ->
            val moduleId = backStackEntry.arguments?.getString("moduleId") ?: return@composable
            val module = Module.valueOf(moduleId)
            MemoryMatchScreen(
                module = module,
                onBack = {
                    // Interstitial on leaving memory match
                    tryShowInterstitial()
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Routes.SOUND_MATCH,
            arguments = listOf(navArgument("moduleId") { type = NavType.StringType })
        ) { backStackEntry ->
            val moduleId = backStackEntry.arguments?.getString("moduleId") ?: return@composable
            val module = Module.valueOf(moduleId)
            SoundMatchScreen(
                module = module,
                onBack = {
                    // Interstitial on leaving sound match
                    tryShowInterstitial()
                    navController.popBackStack()
                },
                onGameFinished = { score, total, xpEarned, coinsEarned ->
                    // Interstitial between game and results screen
                    tryShowInterstitial()
                    navController.navigate(
                        Routes.quizResult(module, score, total, xpEarned, coinsEarned)
                    ) {
                        popUpTo(Routes.HOME)
                    }
                }
            )
        }

        composable(Routes.PARENT_ZONE) {
            ParentZoneScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToPrivacyPolicy = {
                    navController.navigate(Routes.PRIVACY_POLICY)
                },
                onNavigateToSubscription = {
                    navController.navigate(Routes.SUBSCRIPTION)
                },
                adManager = adManager,
                isPremium = isPremium
            )
        }

        composable(Routes.PRIVACY_POLICY) {
            PrivacyPolicyScreen(onNavigateBack = { navController.popBackStack() })
        }

        composable(Routes.SUBSCRIPTION) {
            SubscriptionScreen(
                billingManager = billingManager,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Routes.QUIZ_RESULT,
            arguments = listOf(
                navArgument("moduleId") { type = NavType.StringType },
                navArgument("score") { type = NavType.IntType },
                navArgument("total") { type = NavType.IntType },
                navArgument("xpEarned") { type = NavType.IntType },
                navArgument("coinsEarned") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val moduleId = backStackEntry.arguments?.getString("moduleId") ?: return@composable
            val module = Module.valueOf(moduleId)
            val score = backStackEntry.arguments?.getInt("score") ?: 0
            val total = backStackEntry.arguments?.getInt("total") ?: 1
            val xpEarned = backStackEntry.arguments?.getInt("xpEarned") ?: 0
            val coinsEarned = backStackEntry.arguments?.getInt("coinsEarned") ?: 0
            QuizResultScreen(
                module = module,
                score = score,
                total = total,
                xpEarned = xpEarned,
                coinsEarned = coinsEarned,
                onPlayAgain = {
                    navController.navigate(Routes.quiz(module)) {
                        popUpTo(Routes.HOME)
                    }
                },
                onBackToHome = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                },
                adManager = adManager,
                isPremium = isPremium
            )
        }
    }
}
