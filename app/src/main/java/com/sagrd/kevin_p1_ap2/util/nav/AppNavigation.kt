package com.sagrd.kevin_p1_ap2.util.nav

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sagrd.kevin_p1_ap2.ui.Division.ConsultDivisionScreen
import com.sagrd.kevin_p1_ap2.ui.Division.DivisionScreen

@Composable
fun AppNavigation(context: Context,
)
{
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.FormScreen.route
    ) {
        //Home Screen
        composable(AppScreens.ConsultScreen.route) {
            ConsultDivisionScreen(navController = navController)
        }
        composable(AppScreens.FormScreen.route) {
            DivisionScreen(context=context,navController = navController)
        }
    }
}
