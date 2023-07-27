import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.router.DeepLinkMatchData
import com.pablichj.templato.component.core.router.DeepLinkMatchType
import com.pablichj.templato.component.core.topbar.TopBar
import com.pablichj.templato.component.core.topbar.TopBarState
import com.pablichj.templato.component.core.topbar.TopBarStatePresenterDefault

class AboutUsTopBarComponent(
    val screenName: String,
) : Component() {

    private val topBarStatePresenter = TopBarStatePresenterDefault()
    private val sendMessageState = SendMessageState()

    init {
        topBarStatePresenter.topBarState.value = TopBarState(
            title = "Contact US",
            onTitleClick = {},
            icon1 = Icons.Default.ArrowBack,
            onIcon1Click = {
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

    // region: DeepLink

    override fun getDeepLinkHandler(): DeepLinkMatchData {
        return DeepLinkMatchData(
            screenName,
            DeepLinkMatchType.MatchOne
        )
    }

    // endregion

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
            SendMessageForm(Modifier.padding(paddingValues), sendMessageState)
        }
    }

}

@Composable
fun SendMessageForm(
    modifier: Modifier,
    sendMessageState: SendMessageState
) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Send us a brief explanation of what you App will do:",
            fontSize = TextUnit(20F, TextUnitType.Sp),
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(24.dp))
        TextField(
            modifier = Modifier.fillMaxWidth().height(200.dp),
            value = sendMessageState.topLeftText.value,
            onValueChange = {
                sendMessageState.topLeftText.value = it
            }
        )
        Button(
            enabled = true,
            onClick = {},
            modifier = Modifier.wrapContentSize()
        ) {
            Text("Send")
        }
        Spacer(Modifier.height(8.dp))
        TextButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.textButtonColors(
                contentColor = MaterialTheme.colors.onSurface.copy(
                    alpha = 0.66f
                )
            )
        ) {
            Text("Already have an account? Sign in")
        }
    }
}
