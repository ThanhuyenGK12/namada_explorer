package com.namada.namada_explorer.pages.validator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.namada.namada_explorer.model.Validators
import com.namada.namada_explorer.service.StakePoolService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ValidatorViewModel @Inject constructor(
    private val stakePoolService: StakePoolService
) : ViewModel() {
    data class UiState(
        val isLoading: Boolean = false,
        val validators: List<Validators.Validator> = listOf(),
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
                val res = stakePoolService.getValidator()
                UiState(
                    validators = res.validators
                )
            } catch (e: Exception) {
                UiState(
                    errorMsg = e.toString()
                )
            }
        }
    }
}