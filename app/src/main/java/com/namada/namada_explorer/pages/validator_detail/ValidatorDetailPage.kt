@file:OptIn(ExperimentalMaterial3Api::class)

package com.namada.namada_explorer.pages.validator_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.namada.namada_explorer.components.CardData
import com.namada.namada_explorer.ext.formatWithCommas
import com.namada.namada_explorer.model.Validators

@Composable
fun ValidatorDetailPage(
    navController: NavController,
    validator: Validators.Validator,
    viewModel: ValidatorDetailViewModel = hiltViewModel()
) {
//    var isLoaded by rememberSaveable {
//        mutableStateOf(false)
//    }
//    if (!isLoaded) {
//        viewModel.loadUiData(address = validator.operatorAddress)
//        isLoaded = true
//    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Validator Detail",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Back"
                        )
                    }
                },
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
                .padding(it)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val moniker = validator.moniker
                if(moniker != null) {
                    item {
                        CardData(
                            title = "Author",
                            value = moniker,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                val operatorAddress = validator.operatorAddress
                if(operatorAddress != null) {
                    item {
                        CardData(
                            title = "Address",
                            value = operatorAddress,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
//                item {
//                    CardData(
//                        title = "Public key",
//                        value = validator.pubKey.value.uppercase(),
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                }
//                item {
//                    CardData(
//                        title = "Voting power",
//                        value = validator.votingPower.toString(),
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                }
//                item {
//                    Text(
//                        text = "Blocks of validator",
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 18.sp,
//                        modifier = Modifier.fillMaxWidth(),
//                        textAlign = TextAlign.Left
//                    )
//                }
                if (viewModel.uiState.isLoading) {
                    item {
                        CircularProgressIndicator()
                    }
                } else if (viewModel.uiState.errorMsg != null) {
                    item {
                        Text(text = viewModel.uiState.errorMsg!!, color = Color.Red)
                    }
                } else {
//                    items(viewModel.uiState.blockSignature) {
//                        Card(
//                            modifier = Modifier,
//                            shape = RoundedCornerShape(8.dp),
//                            elevation = CardDefaults.cardElevation(
//                                defaultElevation = 8.dp
//                            ),
//                            colors = CardDefaults.cardColors(
//                                containerColor = Color.White
//                            )
//                        ) {
//                            Column(
//                                modifier = Modifier
//                                    .padding(horizontal = 12.dp, vertical = 8.dp)
//                                    .fillMaxSize(),
//                                verticalArrangement = Arrangement.spacedBy(4.dp),
//                                horizontalAlignment = Alignment.Start,
//                            ) {
//                                Text(
//                                    text = it.blockNumber.toString(),
//                                    fontWeight = FontWeight.Bold,
//                                    textAlign = TextAlign.Left,
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                )
//
//                                Text(
//                                    text = "Status: ${it.signStatus}",
//                                    fontSize = 13.sp,
//                                    textAlign = TextAlign.Right,
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                )
//                            }
//                        }
//                    }
                }
            }
        }
    }
}