package demo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isCtrlPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.topbar.TopBar
import com.macaosoftware.component.topbar.TopBarState
import com.macaosoftware.component.topbar.TopBarStatePresenterDefault
import common.ClipBoardPasteButton
import domain.CallResult
import domain.GetCustomerProjectsUseCase
import kotlinx.browser.window
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DemoComponent(
    val screenName: String,
) : Component() {

    private val topBarStatePresenter = TopBarStatePresenterDefault()
    private val jsonMetadataState = JsonMetadataState()

    init {
        topBarStatePresenter.topBarState.value = TopBarState(
            title = "Contact US",
            onTitleClick = {},
            backNavigationIcon = Icons.Default.ArrowBack,
            onBackNavigationIconClick = {
                handleBackPressed()
            }
        )
    }

    override fun onStart() {
        println("CustomTopBarComponent::onStart()")
    }

    override fun onStop() {
        println("CustomTopBarComponent::onStop()")
    }

    @Composable
    override fun Content(modifier: Modifier) {
        println(
            """${instanceId()}.Composing()}
                |lifecycleState = ${lifecycleState}
            """
        )
        Scaffold(
            modifier = modifier,
            topBar = { TopBar(topBarStatePresenter) }
        ) { paddingValues ->
            JsonMetadataForm(
                Modifier.padding(paddingValues),
                jsonMetadataState
            )
        }
    }

}

@Composable
fun JsonMetadataForm(
    modifier: Modifier,
    jsonMetadataState: JsonMetadataState
) {
    val clipboardManager = window.navigator.clipboard
    var isCmdPress by remember { mutableStateOf(false) }
    var isPasteCmd by remember { mutableStateOf(false) }
    var jsonMetadataText by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Send us a brief explanation of what your App will do:",
            fontSize = TextUnit(20F, TextUnitType.Sp),
            fontWeight = FontWeight.SemiBold
        )
        Row {
            ClipBoardPasteButton {
                jsonMetadataText = it
            }
            Spacer(Modifier.width(16.dp).height(24.dp))
            SendButton {
                coroutineScope.launch {
                    val result = GetCustomerProjectsUseCase(
                        dispatcher = Dispatchers
                    ).doWork()

                    when (result) {
                        is CallResult.Error -> {
                            jsonMetadataText = result.error.toString()
                        }

                        is CallResult.Success -> {
                            val customerProjectsText =
                                result.responseBody.fold("Projects:") { acc, customerProject ->
                                    acc.plus("\n")
                                        .plus("ownerId = ${customerProject.ownerId}").plus("\n")
                                        .plus("jsonMetadata = ${customerProject.jsonMetadata}")
                                }
                            jsonMetadataText = customerProjectsText
                        }
                    }
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.0f)
                .onPreviewKeyEvent {
                    println("Pablo::onKeyEvent = $it")
                    when {
                        (it.key == Key.Unknown) || it.isCtrlPressed -> {
                            when (it.type) {
                                KeyEventType.KeyDown -> {
                                    isCmdPress = true
                                }

                                KeyEventType.KeyUp -> {
                                    isCmdPress = false
                                    if (isPasteCmd) {
                                        clipboardManager.readText().then {
                                            jsonMetadataText = it
                                        }
                                    }
                                    isPasteCmd = false
                                }
                            }

                            true
                        }

                        it.key == Key.V -> {
                            when (it.type) {
                                KeyEventType.KeyDown -> {
                                    isPasteCmd = isCmdPress
                                }

                                KeyEventType.KeyUp -> {
                                    isPasteCmd = isCmdPress
                                }
                            }
                            true
                        }

                        else -> false
                    }
                },
            value = jsonMetadataText,
            onValueChange = { jsonMetadataText = it },
            label = { Text("App Json") }
        )
    }
}

@Composable
private fun SendButton(
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.onSurface.copy(
                alpha = 0.66f
            )
        )
    ) {
        Text("Send")
    }
}
