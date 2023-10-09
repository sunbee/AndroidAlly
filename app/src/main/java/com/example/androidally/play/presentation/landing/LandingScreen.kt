package com.example.androidally.play.presentation.landing

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.androidally.authenticate.data.UserData
import com.example.androidally.play.domain.model.ChapterInfo

@Composable
fun LandingScreen(
    userData: UserData?,
    viewModel: LandingViewModel = hiltViewModel(),
    onItemClick: (ChapterInfo) -> Unit
) {
    val state = viewModel.state.collectAsState()

    when {
        state.value.isLoading -> {
            // Show a loading indicator while data is being fetched.
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        !state.value.error.isNullOrBlank() -> {
            // Show an error message if data fetching failed.
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = state.value.error ?: "Err",
                    color = Color.Red,
                    textAlign = TextAlign.Center)
            }
        }

        else -> {
            // Display the list of modules when data is available.
            Box(
                modifier = Modifier.fillMaxWidth()) {
                userData?.let { 
                    Text(text = it.userId)
                }
                
            }
            ModuleList(
                modules = state.value.chapters,
                onItemClick = onItemClick)
        }
    }
}

@Composable
fun ModuleList(
    modules: List<ChapterInfo>,
    onItemClick: (ChapterInfo) -> Unit
) {
    LazyColumn {
        items(modules) {
            ModuleListItem(
                module = it,
                onItemClick = onItemClick)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModuleListItem(
    module: ChapterInfo,
    onItemClick: (ChapterInfo) -> Unit
) {
    ListItem(
        modifier = Modifier.clickable { onItemClick(module) },
        headlineText = { Text(text = module.title) },
        supportingText = { Text(text = module.description) }
    )
}


