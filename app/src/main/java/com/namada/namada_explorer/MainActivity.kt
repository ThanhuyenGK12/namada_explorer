package com.namada.namada_explorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.namada.namada_explorer.model.Validators
import com.namada.namada_explorer.pages.block_detail.BlockDetailPage
import com.namada.namada_explorer.pages.main.MainPage
import com.namada.namada_explorer.pages.transaction_detail.TransactionDetailPage
import com.namada.namada_explorer.pages.validator_detail.ValidatorDetailPage
import com.namada.namada_explorer.ui.theme.Namada_explorerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Namada_explorerTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "Main"
                ) {
                    composable(route = "Main") {
                        MainPage(navController = navController)
                    }
                    composable(route = "BlockDetail") {
                        val blockID =
                            navController.previousBackStackEntry?.savedStateHandle?.get<String>("blockID")
                                ?: return@composable
                        BlockDetailPage(navController = navController, blockID = blockID)
                    }
                    composable(route = "TransactionDetail") {
                        val transactionHash =
                            navController.previousBackStackEntry?.savedStateHandle?.get<String>("transaction_hash")
                                ?: return@composable
                        TransactionDetailPage(navController = navController, hash = transactionHash)
                    }
                    composable(route = "ValidatorDetail") {
                        val validator =
                            navController.previousBackStackEntry?.savedStateHandle?.get<Validators.Validator>(
                                "validator"
                            )
                                ?: return@composable
                        ValidatorDetailPage(navController = navController, validator = validator)
                    }
                }
            }
        }
    }
}