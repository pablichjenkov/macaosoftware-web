package demo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
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
import androidx.compose.ui.Alignment
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
import common.CallResult
import demo.domain.model.CustomerProjectUpdateRequest
import demo.domain.GetCustomerProjectByOwnerIdUseCase
import demo.domain.GetCustomerProjectListUseCase
import demo.domain.UpdateCustomerProjectByOwnerIdUseCase
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

    override fun onAttach() {
        println("CustomTopBarComponent::onAttach()")
    }

    override fun onStart() {
        println("CustomTopBarComponent::onStart()")
    }

    override fun onStop() {
        println("CustomTopBarComponent::onStop()")
    }

    override fun onDetach() {
        println("CustomTopBarComponent::onDetach()")
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
    var ownerId by remember { mutableStateOf("") }
    var jsonMetadataText by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Use your ownerId value to retrieve your App metadata and modify it.",
            fontSize = TextUnit(20F, TextUnitType.Sp),
            fontWeight = FontWeight.SemiBold
        )
        Row(
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
        ) {
            Column(
                modifier = Modifier.weight(1.0F).padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = ownerId,
                    onValueChange = {
                        ownerId = it
                    },
                    label = { Text("ownerId") }
                )
                Spacer(Modifier.width(16.dp).height(16.dp))
                ClipBoardPasteButton {
                    jsonMetadataText = it
                }
            }
            Column(
                modifier = Modifier.weight(1.0F).padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ButtonGetMetadata(ownerId) {
                    jsonMetadataText = it
                }
                Spacer(Modifier.width(16.dp).height(16.dp))
                ButtonUpdateMetadata(
                    CustomerProjectUpdateRequest(ownerId, jsonMetadataText)
                ) {
                    jsonMetadataText = it
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
private fun ButtonGetMetadata(
    ownerId: String,
    onResult: (String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    Button(onClick = {
        if (ownerId.isNullOrEmpty()) {
            onResult("ownerId cannot be empty. Apply for one in the Account session")
            return@Button
        }
        coroutineScope.launch {
            val result = GetCustomerProjectByOwnerIdUseCase(
                dispatcher = Dispatchers
            ).doWork(ownerId)

            when (result) {
                is CallResult.Error -> {
                    onResult(result.error.toString())
                }

                is CallResult.Success -> {
                    onResult(result.responseBody.jsonMetadata)
                }
            }
        }
    }) {
        Text("Get Metadata")
    }
}

@Composable
private fun ButtonUpdateMetadata(
    updateRequest: CustomerProjectUpdateRequest,
    onResult: (String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    Button(onClick = {
        if (updateRequest.ownerId.isNullOrEmpty()) {
            onResult("ownerId cannot be empty. Apply for one in the Account session")
            return@Button
        }
        coroutineScope.launch {
            val result = UpdateCustomerProjectByOwnerIdUseCase(
                dispatcher = Dispatchers
            ).doWork(updateRequest)

            when (result) {
                is CallResult.Error -> {
                    onResult(result.error.toString())
                }

                is CallResult.Success -> {
                    onResult(result.responseBody.jsonMetadata)
                }
            }
        }
    }) {
        Text("Update Metadata")
    }
}

@Composable
private fun ButtonListAllCustomers(
    onResult: (jsonMetadataText: String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    SendButton {
        coroutineScope.launch {
            val result = GetCustomerProjectListUseCase(
                dispatcher = Dispatchers
            ).doWork()

            when (result) {
                is CallResult.Error -> {
                    onResult(result.error.toString())
                }

                is CallResult.Success -> {
                    val customerProjectsText =
                        result.responseBody.fold("Projects:") { acc, customerProject ->
                            acc.plus("\n")
                                .plus("ownerId = ${customerProject.ownerId}").plus("\n")
                                .plus("jsonMetadata = ${customerProject.jsonMetadata}")
                        }
                    onResult(customerProjectsText)
                }
            }
        }
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
