@file:OptIn(ExperimentalMaterial3Api::class)

package com.namada.namada_explorer.pages.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.namada.namada_explorer.R
import com.namada.namada_explorer.pages.block.BlockPage
import com.namada.namada_explorer.pages.home.HomePage
import com.namada.namada_explorer.pages.transaction.TransactionPage
import com.namada.namada_explorer.pages.validator.ValidatorPage
import kotlinx.coroutines.launch

enum class MainPageState(
    val icon: Int
) {
    Home(R.drawable.home),
    Validator(R.drawable.account),
    Block(R.drawable.box),
    Transaction(R.drawable.transaction)
}

@Composable
fun MainPage(navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var currentState by rememberSaveable {
        mutableStateOf(MainPageState.Home)
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                Surface(
                    modifier = Modifier
                        .requiredWidth(320.dp)
                        .fillMaxHeight(),
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = "Namada Explorer",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                }
                            )
                        }
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(it)
                                .padding(horizontal = 16.dp, vertical = 24.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            for (state in MainPageState.entries) {
                                Card(
                                    modifier = Modifier,
                                    shape = RoundedCornerShape(8.dp),
                                    elevation = CardDefaults.cardElevation(
                                        defaultElevation = 8.dp
                                    ),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (state == currentState) Color.LightGray else Color.White
                                    ),
                                    onClick = {
                                        currentState = state
                                        if (drawerState.isOpen) {
                                            scope.launch {
                                                drawerState.close()
                                            }
                                        }
                                    }
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp, vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Image(
                                            painter = painterResource(id = state.icon),
                                            contentDescription = ""
                                        )

                                        Text(
                                            text = state.name,
                                            fontWeight = if (state == currentState) FontWeight.Bold else null
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = currentState.name,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    if (drawerState.isClosed) {
                                        scope.launch {
                                            drawerState.open()
                                        }
                                    }
                                }
                            ) {
                                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                            }
                        }
                    )
                }
            ) {
                Box(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                ) {
                    when (currentState) {
                        MainPageState.Home -> HomePage()
                        MainPageState.Validator -> ValidatorPage(navController = navController)
                        MainPageState.Block -> BlockPage(navController = navController)
                        MainPageState.Transaction -> TransactionPage(navController = navController)
                    }
                }
            }
        }
    }
}