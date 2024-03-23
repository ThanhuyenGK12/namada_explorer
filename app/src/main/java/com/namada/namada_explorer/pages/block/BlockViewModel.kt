package com.namada.namada_explorer.pages.block

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.namada.namada_explorer.Block
import com.namada.namada_explorer.service.RpcService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BlockViewModel @Inject constructor(
    private val rpcService: RpcService
) : ViewModel() {
    data class UiState(
        val isLoading: Boolean = false,
        val errorMsg: String? = null,
    )

    var uiState by mutableStateOf(UiState())
        private set

    val blocks = Pager(
        initialKey = 1,
        config = PagingConfig(pageSize = 15),
        pagingSourceFactory = {
            object : PagingSource<Int, Block>() {
                override fun getRefreshKey(state: PagingState<Int, Block>): Int? {
                    return state.anchorPosition?.let { anchorPosition ->
                        state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                            ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
                    }
                }

                override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Block> {
                    return withContext(Dispatchers.IO) {
                        try {
                            val page = params.key ?: 1
                            uiState = UiState(isLoading = true)
                            val responses = rpcService.getBlock(page = page, pageSize = 15)
                            LoadResult.Page(
                                data = responses.data,
                                prevKey = if (page == 1) null else page - 1,
                                nextKey = if (responses.total <= responses.data.size) null else page + 1
                            )
                        } catch (e: Exception) {
                            uiState = UiState(errorMsg = e.toString())
                            LoadResult.Error(e)
                        }
                    }
                }
            }
        }
    ).flow
}