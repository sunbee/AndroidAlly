package com.example.androidally.play.presentation.play_interactive_quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.androidally.authenticate.data.UserData

@Composable
fun PlayInteractiveQuizScreen(
    viewModel: PlayInteractiveQuizViewModel = hiltViewModel(),
    user: UserData,
    modifier: Modifier = Modifier
) {
    val state = viewModel.state.collectAsState()

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(6.dp)
    ) {
        state.value.bank.getOrNull(state.value.currentIndex)?.let { qna ->
            Text(
                text = qna.question,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (state.value.showQuestionOnly) {
                Button(
                    onClick = {
                        viewModel.onEvent(PlayInteractiveQuizEvent.OnClickedShowAnswer)
                    },
                    modifier = Modifier.padding(6.dp)) {
                    Text(text = "Show Answer")
                }
            } else {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = { viewModel.onEvent(PlayInteractiveQuizEvent.OnClickedCorrect) }) {
                        Text(
                            text = "Spot On",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp)
                    }
                    Button(onClick = { viewModel.onEvent((PlayInteractiveQuizEvent.OnClickedPartial)) }) {
                        Text(text = "Partial",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp)
                    }
                    Button(onClick = { viewModel.onEvent(PlayInteractiveQuizEvent.OnClickedIncorrect)}) {
                        Text(text = "Incorrect",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                qna.image?.let { imageURI ->
                    PlayInteractiveQuizImage(imageURI = imageURI)

                }
                Text(text = qna.answer,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                PlayInteractiveQuizYouTubeVideo(
                    videoId = qna.youTubeVideoId,
                    startTimeSeconds = qna.timestamp.toFloat(),
                    modifier = Modifier.fillMaxSize())
            }
        }
    }
}