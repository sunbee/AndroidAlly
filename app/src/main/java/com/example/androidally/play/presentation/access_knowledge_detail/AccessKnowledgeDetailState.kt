package com.example.androidally.play.presentation.access_knowledge_detail

import com.example.androidally.play.domain.model.Quiz

data class AccessKnowledgeDetailState(
    val quiz: Quiz? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)