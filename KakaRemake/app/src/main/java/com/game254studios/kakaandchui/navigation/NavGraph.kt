package com.game254studios.kakaandchui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.game254studios.kakaandchui.data.model.Module
import com.game254studios.kakaandchui.ui.screens.HomeScreen
import com.game254studios.kakaandchui.ui.screens.LearnScreen
import com.game254studios.kakaandchui.ui.screens.QuizResultScreen
import com.game254studios.kakaandchui.ui.screens.QuizScreen
import com.game254studios.kakaandchui.ui.screens.SplashScreen

object Routes {
    const val SPLASH = "splash"
    const val HOME = "home"
    const val LEARN = "learn/{moduleId}"
    const val QUIZ = "quiz/{moduleId}"
    const val QUIZ_RESULT = "quiz_result/{moduleId}/{score}/{total}"

    fun learn(module: Module) = "learn/${module.name}"
    fun quiz(module: Module) = "quiz/${module.name}"
    fun quizResult(module: Module, score: Int, total: Int) =
        "quiz_result/${module.name}/$score/$total"
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.SPLASH) {

        composable(Routes.SPLASH) {
            SplashScreen(onSplashFinished = {
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.SPLASH) { inclusive = true }
                }
            })
        }

        composable(Routes.HOME) {
            HomeScreen(onModuleClick = { module ->
                navController.navigate(Routes.learn(module))
            })
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
            QuizScreen(
                module = module,
                onBack = { navController.popBackStack() },
                onQuizFinished = { score, total ->
                    navController.navigate(Routes.quizResult(module, score, total)) {
                        popUpTo(Routes.HOME)
                    }
                }
            )
        }

        composable(
            route = Routes.QUIZ_RESULT,
            arguments = listOf(
                navArgument("moduleId") { type = NavType.StringType },
                navArgument("score") { type = NavType.IntType },
                navArgument("total") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val moduleId = backStackEntry.arguments?.getString("moduleId") ?: return@composable
            val module = Module.valueOf(moduleId)
            val score = backStackEntry.arguments?.getInt("score") ?: 0
            val total = backStackEntry.arguments?.getInt("total") ?: 1
            QuizResultScreen(
                module = module,
                score = score,
                total = total,
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
