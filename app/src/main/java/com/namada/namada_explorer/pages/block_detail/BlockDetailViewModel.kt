package com.namada.namada_explorer.pages.block_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.namada.namada_explorer.Block
import com.namada.namada_explorer.service.RpcService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlockDetailViewModel @Inject constructor(
    private val rpcService: RpcService
) : ViewModel() {
    data class UiState(
        val isLoading: Boolean = false,
        val block: Block? = null,
        val errorMsg: String? = null,
    )

    var uiState by mutableStateOf(UiState())
        private set

    fun loadUiData(blockID: String) {
        uiState = UiState(isLoading = false)
        viewModelScope.launch {
            uiState = try {
                val res = rpcService.getBlock(id = blockID)
                UiState(
                    block = res
                )
            } catch (e: Exception) {
                UiState(
                    errorMsg = e.toString()
                )
            }
        }
    }
}