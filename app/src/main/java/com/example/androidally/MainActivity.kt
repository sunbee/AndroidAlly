package com.example.androidally

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.androidally.authenticate.data.FireAuthUIClient
import com.example.androidally.authenticate.presentation.sign_in.SignInScreen
import com.example.androidally.authenticate.presentation.sign_in.SignInViewModel
import com.example.androidally.play.presentation.access_knowledge_bank.AccessKnowledgeBankScreen
import com.example.androidally.play.presentation.access_knowledge_bank.AccessKnowledgeBankViewModel
import com.example.androidally.play.presentation.access_knowledge_detail.AccessKnowledgeDetailScreen
import com.example.androidally.play.presentation.access_knowledge_detail.AccessKnowledgeDetailViewModel
import com.example.androidally.play.presentation.landing.LandingScreen
import com.example.androidally.ui.theme.AndroidAllyTheme
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val fireClient by lazy {
            FireAuthUIClient(
                context = applicationContext,
                oneTapClient = Identity.getSignInClient(applicationContext)
            )
        }

        super.onCreate(savedInstanceState)
        setContent {
            AndroidAllyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "sign-in") {
                        composable("sign-in") {
                            val viewModel = viewModel<SignInViewModel>()
                            val state = viewModel.state.collectAsState()

                            LaunchedEffect(key1 = Unit) {
                                if (fireClient.getSignedInUser() != null) {
                                    navController.navigate("landing")
                                }
                            }

                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if (result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            var signInResult = fireClient.signInWithIntent(
                                                intent = result?.data ?: return@launch
                                            )
                                            viewModel.onSignInResult(signInResult)
                                        }  // end COROUTINE
                                    }  // end IF
                                }  // end ONRESULT
                            )  // end LAUNCHER
                            
                            LaunchedEffect(key1 = state.value.isSignInSuccessful) {
                                if (state.value.isSignInSuccessful) {
                                    Toast.makeText(
                                        applicationContext,
                                        "Signed in user!",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    navController.navigate("landing")
                                    viewModel.resetSignInState()
                                }  // end IF
                            }  // end LAUNCHED EFFECT

                            SignInScreen(
                                signInState = state.value,
                                onSignInClick = {
                                    lifecycleScope.launch {
                                        launcher.launch(
                                            IntentSenderRequest.Builder(
                                                fireClient.signIn() ?: return@launch
                                            ).build()
                                        )
                                    }
                                })
                        }
                        composable("landing") {
                            LandingScreen(
                                userData = fireClient.getSignedInUser(),
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
                            val state = viewModel.state.collectAsState()
                            val quiz = state.value.quiz

                            if (state.value.isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.fillMaxWidth(),
                                    color = Color.Green,
                                    strokeWidth = 36.dp
                                )
                            } else if (state.value.quiz != null) {
                                quiz?.let { quiz ->
                                    AccessKnowledgeDetailScreen(
                                        quiz = quiz,
                                        modifier = Modifier.fillMaxWidth())
                                }
                            } else {
                                Text(
                                    text = "NO-SHOW!",
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }  // end COMPOSABLE
                    }  // end NAVHOST
                }  // end SURFACE
            }  // end THEME
        }  // end SETCONTENT
    }  // end ONCREATE
}

