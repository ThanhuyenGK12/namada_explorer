package com.namada.namada_explorer.pages.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.namada.namada_explorer.ext.formatDate
import com.namada.namada_explorer.service.RpcService
import com.namada.namada_explorer.service.StakePoolService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val rpcService: RpcService,
    private val stakePoolService: StakePoolService,
) : ViewModel() {
    data class UiState(
        val isLoading: Boolean = false,
        val network: String = "",
        val blockSize: Int = 0,
        val latestBlockTime: String = "",
        val validatorCount: Int = 0,
        val votingPower: Long = 0L,
        val errorMsg: String? = null,
    )

    var uiState by mutableStateOf(UiState())
        private set

    init {
        loadUiData()
    }

    private fun loadUiData() {
        uiState = UiState(isLoading = true)
        viewModelScope.launch {
            uiState = try {
                val res = rpcService.getBlock(
                    page = 1,
                    pageSize = 1
                )
                val validators = stakePoolService.getValidator()
                val latestBlock = res.data.first()
                val blockSize = res.total

                UiState(
                    network = latestBlock.header.chainID,
                    blockSize = blockSize,
                    latestBlockTime = latestBlock.header.time.formatDate,
                    validatorCount = validators.currentValidatorsList.size,
                    votingPower = validators.currentValidatorsList.sumOf {
                        it.votingPower
                    }
                )
            } catch (e: Exception) {
                UiState(
                    errorMsg = e.toString()
                )
            }
        }
    }
}