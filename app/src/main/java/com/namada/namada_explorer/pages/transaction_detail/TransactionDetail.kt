@file:OptIn(ExperimentalMaterial3Api::class)

package com.namada.namada_explorer.pages.transaction_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.namada.namada_explorer.components.CardData
import com.namada.namada_explorer.ext.formatDate

@Composable
fun TransactionDetailPage(
    hash: String,
    navController: NavController,
    viewModel: TransactionDetailViewModel = hiltViewModel()
) {
    var isLoaded by rememberSaveable {
        mutableStateOf(false)
    }
    if (!isLoaded) {
        viewModel.loadUiData(hash = hash)
        isLoaded = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Transaction Detail",
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
            Column(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 24.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (viewModel.uiState.isLoading) {
                    CircularProgressIndicator()
                } else if (viewModel.uiState.errorMsg != null) {
                    Text(text = viewModel.uiState.errorMsg!!, color = Color.Red)
                } else {
                    val transaction = viewModel.uiState.transaction ?: return@Column
                    val block = viewModel.uiState.block ?: return@Column
                    CardData(
                        title = "Tx Hash",
                        value = transaction.hash,
                        modifier = Modifier.fillMaxWidth()
                    )
                    CardData(
                        title = "Time",
                        value = block.header.time.formatDate,
                        modifier = Modifier.fillMaxWidth()
                    )
                    CardData(
                        title = "Network",
                        value = block.header.chainID,
                        modifier = Modifier.fillMaxWidth()
                    )
                    CardData(
                        title = "Height",
                        value = block.header.height,
                        modifier = Modifier.fillMaxWidth()
                    )
                    CardData(
                        title = "Status",
                        value = if (transaction.txType == "Wrapper" || transaction.returnCode == 0L)
                            "Success"
                        else
                            "Failed",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}