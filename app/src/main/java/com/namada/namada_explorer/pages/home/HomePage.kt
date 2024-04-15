package com.namada.namada_explorer.pages.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.namada.namada_explorer.components.CardData
import com.namada.namada_explorer.ext.formatNumber

@Composable
fun HomePage(viewModel: HomeViewModel = hiltViewModel()) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
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
                CardData(
                    title = "Time",
                    value = viewModel.uiState.latestBlockTime,
                    modifier = Modifier.fillMaxWidth()
                )
                CardData(
                    title = "Blocks",
                    value = viewModel.uiState.blockSize.toString(),
                    modifier = Modifier.fillMaxWidth()
                )
                CardData(
                    title = "Network",
                    value = viewModel.uiState.network,
                    modifier = Modifier.fillMaxWidth()
                )
                CardData(
                    title = "Validator",
                    value = viewModel.uiState.validatorCount.toString(),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}