package com.example.androidally.play.presentation.access_knowledge_bank

data class AccessKnowledgeBankState(
    val module: String? = null,
    val questionBank: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
