package com.namada.namada_explorer.pages.validator_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.namada.namada_explorer.model.BlockSignature
import com.namada.namada_explorer.service.StakePoolService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ValidatorDetailViewModel @Inject constructor(
    private val stakePoolService: StakePoolService
) : ViewModel() {
    data class UiState(
        val isLoading: Boolean = false,
        val errorMsg: String? = null,
        val blockSignature: List<BlockSignature> = listOf(),
    )

    var uiState by mutableStateOf(UiState())
        private set

    fun loadUiData(address: String) {
        uiState = UiState(isLoading = true)
        viewModelScope.launch {
            uiState = try {
                val res = stakePoolService.getBlockSignature(address = address)
                UiState(
                    blockSignature = res
                )
            } catch (e: Exception) {
                UiState(
                    errorMsg = e.toString()
                )
            }
        }
    }
}