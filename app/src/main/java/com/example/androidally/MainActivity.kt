package com.example.androidally

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.androidally.play.presentation.access_knowledge_bank.AccessKnowledgeBankScreen
import com.example.androidally.play.presentation.access_knowledge_bank.AccessKnowledgeBankViewModel
import com.example.androidally.play.presentation.access_knowledge_detail.AccessKnowledgeDetailScreen
import com.example.androidally.play.presentation.access_knowledge_detail.AccessKnowledgeDetailViewModel
import com.example.androidally.play.presentation.landing.LandingScreen
import com.example.androidally.ui.theme.AndroidAllyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidAllyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "landing") {
                        composable("landing") {
                            LandingScreen(
                                onItemClick = {
                                    val name = it.name
                                    navController.navigate("module?name=${name}") })
                        }
                        composable(
                            "module?name={name}",
                            arguments = listOf(
                                navArgument("name") {
                                    type = NavType.StringType
                                    defaultValue = "AndroidBasics"
                                }
                            )
                        ) {
                            val viewModel: AccessKnowledgeBankViewModel = hiltViewModel()
                            val state = viewModel.state.collectAsState()

                            val questions = state.value.questionBank
                            AccessKnowledgeBankScreen(
                                questions = questions,
                                onItemClick = {
                                    val moduleName = state.value.module ?: ""
                                    navController.navigate("details?name=${moduleName}&question=${it}")
                                })
                        }
                        composable(
                            "details?question={question}",
                            arguments = listOf(
                                navArgument("name") {
                                    type = NavType.StringType
                                    defaultValue = "AndroidBasics"
                                },
                                navArgument("question") {
                                    type = NavType.StringType
                                    defaultValue = "Why have you no question?"
                                }
                            )
                        ) {
                            val viewModel: AccessKnowledgeDetailViewModel = hiltViewModel()
                            val quiz = viewModel.state.collectAsState().value.quiz
                            AccessKnowledgeDetailScreen(
                                quiz = quiz,
                                modifier = Modifier.fillMaxWidth())
                        }
                    }  // end NAVHOST
                }  // end SURFACE
            }  // end THEME
        }  // end SETCONTENT
    }  // end ONCREATE
}

