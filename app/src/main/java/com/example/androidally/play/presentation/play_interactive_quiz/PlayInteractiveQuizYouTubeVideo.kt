package com.example.androidally.play.presentation.play_interactive_quiz

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun PlayInteractiveQuizYouTubeVideo(
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
                        youTubePlayer.cueVideo(videoId, startTimeSeconds)
                    }  // end ONREADY
                }  // end OBJECT
            )  // end FRAGMENT
            view
        })
}