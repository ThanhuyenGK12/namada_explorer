@file:OptIn(ExperimentalMaterial3Api::class)

package com.namada.namada_explorer.pages.block

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.paging.compose.collectAsLazyPagingItems
import com.namada.namada_explorer.ext.center3Dot
import com.namada.namada_explorer.ext.formatDate

@Composable
fun BlockPage(
    navController: NavController,
    viewModel: BlockViewModel = hiltViewModel()
) {
    val pagingItems = viewModel.blocks.collectAsLazyPagingItems()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (pagingItems.itemCount == 0) {
                if (viewModel.uiState.isLoading) {
                    item {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }
                } else if (viewModel.uiState.errorMsg != null) {
                    item {
                        Text(text = viewModel.uiState.errorMsg!!, color = Color.Red)
                    }
                }
            } else {
                items(pagingItems.itemCount) { index ->
                    val block = pagingItems[index] ?: return@items

                    Box(modifier = Modifier.padding(horizontal = 12.dp)) {
                        Card(
                            modifier = Modifier,
                            shape = RoundedCornerShape(8.dp),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 8.dp
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            onClick = {
                                navBackStackEntry?.savedStateHandle?.set(
                                    "blockID",
                                    block.blockID
                                )
                                navController.navigate("BlockDetail")
                            }
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                                horizontalAlignment = Alignment.Start,
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        text = block.blockID.uppercase().center3Dot,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Left,
                                        modifier = Modifier
                                    )

                                    Text(text = " - ")

                                    Text(
                                        text = block.header.time.formatDate,
                                        textAlign = TextAlign.Left,
                                        modifier = Modifier
                                    )
                                }

                                Text(
                                    text = block.header.height,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }

                if (viewModel.uiState.isLoading) {
                    item {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }
                } else if (viewModel.uiState.errorMsg != null) {
                    item {
                        Text(text = viewModel.uiState.errorMsg!!, color = Color.Red)
                    }
                }
            }
        }
    }
}