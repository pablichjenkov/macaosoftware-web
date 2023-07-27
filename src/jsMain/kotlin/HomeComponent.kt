import androidx.compose.foundation.clickable
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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.getRouter
import com.pablichj.templato.component.core.router.DeepLinkMatchData
import com.pablichj.templato.component.core.router.DeepLinkMatchType
import com.pablichj.templato.component.core.router.DeepLinkMsg
import com.pablichj.templato.component.core.topbar.TopBar
import com.pablichj.templato.component.core.topbar.TopBarState
import com.pablichj.templato.component.core.topbar.TopBarStatePresenterDefault

class HomeComponent : Component() {

    private val topBarStatePresenter = TopBarStatePresenterDefault()

    init {
        topBarStatePresenter.topBarState.value = TopBarState(
            title = "Home",
            onTitleClick = {}
        )
    }

    override fun onStart() {
        println("HomeComponent::onStart()")
    }

    override fun onStop() {
        println("HomeComponent::onStop()")
    }

    // region: DeepLink

    override fun getDeepLinkHandler(): DeepLinkMatchData {
        return DeepLinkMatchData(
            "Home",
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
            HomeScreen(
                modifier = Modifier.padding(paddingValues),
                onContactUsClick = {
                    val deepLinkPath = listOf("Contact Us")
                    val deepLinkMsg = DeepLinkMsg(
                        deepLinkPath
                    ) {
                        println("MainWindowNode::deepLinkResult = $it")
                    }
                    getRouter()?.handleDeepLink(deepLinkMsg)
                }
            )
        }
    }

}

@Composable
fun HomeScreen(
    modifier: Modifier,
    onContactUsClick: () -> Unit
) {
    val amadeusHotelAppUrl = remember { "https://github.com/pablichjenkov/amadeus-hotel-app" }
    val annotatedString = buildAnnotatedString {
        append(amadeusHotelAppUrl)
        addStyle(
            style = SpanStyle(
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.SemiBold
            ),
            start = 0,
            end = amadeusHotelAppUrl.length
        )
    }

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "The Company",
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Macao is a digital agency located in Miami Florida. The company specializes in bringing digital products to life, such as mobile and web Applications.",
            style = MaterialTheme.typography.body1
        )
        Spacer(Modifier.height(32.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Some Work",
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(16.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.h6,
            text = "Amadeus Hotel App"
        )
        Spacer(Modifier.height(8.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.body1,
            text = "A sample App to book hotels using the Amadeus self service API. Visit the link bellow for more information"
        )
        Spacer(Modifier.height(16.dp))
        val uriHandler = LocalUriHandler.current
        Text(
            modifier = Modifier.fillMaxWidth().clickable {
                uriHandler.openUri(amadeusHotelAppUrl)
            },
            style = MaterialTheme.typography.body1,
            text = annotatedString
        )
        Spacer(Modifier.height(56.dp))
        Button(
            modifier = Modifier.wrapContentSize(),
            onClick = {
                onContactUsClick()
            }
        ) {
            Text("Contact Us")
        }
    }
}
