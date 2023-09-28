package home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.deeplink.DeepLinkMsg
import com.macaosoftware.component.core.deeplink.DefaultDeepLinkManager
import com.macaosoftware.component.core.deeplink.LocalRootComponentProvider
import com.macaosoftware.component.topbar.TopBar
import com.macaosoftware.component.topbar.TopBarState
import com.macaosoftware.component.topbar.TopBarStatePresenterDefault

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
            val rootComponent = LocalRootComponentProvider.current
            HomeScreen(
                modifier = Modifier.padding(paddingValues),
                onContactUsClick = {
                    val deepLinkPath = listOf(
                        "navigation_panel_component",
                        "contact_us"
                    )
                    val deepLinkMsg = DeepLinkMsg(deepLinkPath) {
                        println("HomeComponent::deepLinkResult = $it")
                    }
                    DefaultDeepLinkManager().navigateToDeepLink(rootComponent, deepLinkMsg)
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
                color = MaterialTheme.colorScheme.primary,
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
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Macao is a digital agency located in Miami Florida. The company specializes in bringing digital products to life, such as mobile and web Applications.",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(32.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Some Work",
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(16.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.headlineSmall,
            text = "Amadeus Hotel App"
        )
        Spacer(Modifier.height(8.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyMedium,
            text = "A sample App to book hotels using the Amadeus self service API. Visit the link bellow for more information"
        )
        Spacer(Modifier.height(16.dp))
        val uriHandler = LocalUriHandler.current
        Text(
            modifier = Modifier.fillMaxWidth().clickable {
                uriHandler.openUri(amadeusHotelAppUrl)
            },
            style = MaterialTheme.typography.bodyMedium,
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
