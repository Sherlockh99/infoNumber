package com.sh.work.infonumber

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sh.work.infonumber.entity.HistoryEntity
import com.sh.work.infonumber.ui.details.DetailsScreen
import com.sh.work.infonumber.ui.main.MainScreen
import com.sh.work.infonumber.ui.main.MainViewModel
import com.sh.work.infonumber.ui.theme.InfoNumberTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            InfoNumberTheme {
                AppNavHost()
            }
        }
    }
}

@Composable
fun AppNavHost() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Main") {
        composable("Main") {
            val context = LocalContext.current
            val viewModel: MainViewModel = viewModel(
                factory = ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as Application)
            )
            MainScreen(viewModel = viewModel, navController = navController)
        }

        composable("details") {
            val navBackStackEntry = remember { navController.previousBackStackEntry }
            val history = navBackStackEntry?.savedStateHandle?.get<HistoryEntity>("selectedHistory")

            if (history != null) {
                DetailsScreen(history = history, navController = navController)
            }
        }
    }
}