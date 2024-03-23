package com.namada.namada_explorer.model

data class Page<Data>(
    val data: List<Data>,
    val total: Int
)
