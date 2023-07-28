import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.OutlinedRichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichText
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendMessageForm(
    modifier: Modifier,
    sendMessageState: SendMessageState
) {
    val outlinedRichTextState = rememberRichTextState()
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Send us a brief explanation of what your App will do:",
            fontSize = TextUnit(20F, TextUnitType.Sp),
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(24.dp))
        OutlinedRichTextEditor(
            modifier = Modifier.fillMaxWidth(),
            state = outlinedRichTextState,
        )
        RichTextStyleRow(
            modifier = Modifier.fillMaxWidth(),
            state = outlinedRichTextState,
        )
        Spacer(Modifier.height(48.dp))
        RichText(
            modifier = Modifier.fillMaxWidth()
                .heightIn(min = 200.dp)
                .border(width = 1.dp, color = Color.Black),
            state = outlinedRichTextState,
        )
        Spacer(Modifier.height(16.dp))
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            TextButton(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colors.onSurface.copy(
                        alpha = 0.66f
                    )
                )
            ) {
                Button(
                    enabled = true,
                    onClick = {},
                    modifier = Modifier.wrapContentSize()
                ) {
                    Text("Send")
                }
            }
        }
    }
}
