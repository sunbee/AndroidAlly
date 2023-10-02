package com.example.androidally.play.presentation.access_knowledge_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.androidally.play.domain.model.Quiz
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun AccessKnowledgeDetailScreen(
    quiz: Quiz?,
    modifier: Modifier
) {
    Column (
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)) {
    }
    if (quiz == null) {
        Text(
            text = "FOUND NO QUIZ!",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Italic,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    } else {
        Text(
            text = quiz.question,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        KnowledgeBaseVideo(
            videoId = quiz.youTubeVideoId,
            startTimeSeconds = quiz.timestamp.toFloat(),
            modifier = Modifier.fillMaxWidth())

    }
}

@Composable
fun KnowledgeBaseVideo(
    videoId: String,
    startTimeSeconds: Float = 0f,
    modifier: Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    AndroidView(
        factory = { context ->
            var view = YouTubePlayerView(context)
            lifecycleOwner.lifecycle.addObserver(view)
            var fragment = view.addYouTubePlayerListener(
                object: AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        super.onReady(youTubePlayer)
                        youTubePlayer.loadVideo(videoId, startTimeSeconds)
                    }  // end ONREADY
                }  // end OBJECT
            )  // end FRAGMENT
            view
        })
}