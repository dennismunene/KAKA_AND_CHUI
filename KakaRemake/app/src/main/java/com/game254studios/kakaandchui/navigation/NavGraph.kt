package com.game254studios.kakaandchui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.game254studios.kakaandchui.data.model.Module
import com.game254studios.kakaandchui.ui.screens.HomeScreen
import com.game254studios.kakaandchui.ui.screens.LearnScreen
import com.game254studios.kakaandchui.ui.screens.ParentZoneScreen
import com.game254studios.kakaandchui.ui.screens.QuizResultScreen
import com.game254studios.kakaandchui.ui.screens.QuizScreen
import com.game254studios.kakaandchui.ui.screens.PrivacyPolicyScreen
import com.game254studios.kakaandchui.ui.screens.SplashScreen
import com.game254studios.kakaandchui.viewmodel.HomeViewModel
import com.game254studios.kakaandchui.viewmodel.QuizViewModel

object Routes {
    const val SPLASH = "splash"
    const val HOME = "home"
    const val LEARN = "learn/{moduleId}"
    const val QUIZ = "quiz/{moduleId}"
    const val QUIZ_RESULT = "quiz_result/{moduleId}/{score}/{total}/{xpEarned}/{coinsEarned}"

    fun learn(module: Module) = "learn/${module.name}"
    fun quiz(module: Module) = "quiz/${module.name}"
    fun quizResult(module: Module, score: Int, total: Int, xpEarned: Int, coinsEarned: Int) =
        "quiz_result/${module.name}/$score/$total/$xpEarned/$coinsEarned"

    const val PARENT_ZONE = "parent_zone"
    const val PRIVACY_POLICY = "privacy_policy"
}

@Composable
fun NavGraph(navController: NavHostController) {
    val homeViewModel: HomeViewModel = viewModel()

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
                }
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
                onBack = { navController.popBackStack() },
                onStartQuiz = {
                    navController.navigate(Routes.quiz(module)) {
                        popUpTo(Routes.learn(module)) { inclusive = true }
                    }
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
                    navController.navigate(
                        Routes.quizResult(module, score, total, xpEarned, coinsEarned)
                    ) {
                        popUpTo(Routes.HOME)
                    }
                },
                quizViewModel = quizViewModel
            )
        }

        composable(Routes.PARENT_ZONE) {
            ParentZoneScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToPrivacyPolicy = {
                    navController.navigate(Routes.PRIVACY_POLICY)
                }
            )
        }

        composable(Routes.PRIVACY_POLICY) {
            PrivacyPolicyScreen(onNavigateBack = { navController.popBackStack() })
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
                }
            )
        }
    }
}
