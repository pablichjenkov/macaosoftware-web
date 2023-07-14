import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
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
import com.pablichj.templato.component.core.consumeBackPressEvent
import com.pablichj.templato.component.core.getRouter
import com.pablichj.templato.component.core.router.DeepLinkMatchData
import com.pablichj.templato.component.core.router.DeepLinkMatchType
import com.pablichj.templato.component.core.router.DeepLinkMsg

class HomeComponent : Component() {

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
        println("HomeComponent::Composing()")
        consumeBackPressEvent()
        // HomeScreen()
        HomeScreen1 {
            val deepLinkPath = listOf("Contact Us")
            val deepLinkMsg = DeepLinkMsg(
                deepLinkPath
            ) {
                println("MainWindowNode::deepLinkResult = $it")
            }
            getRouter()?.handleDeepLink(deepLinkMsg)
        }
    }

}

@Composable
fun HomeScreen1(
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
        modifier = Modifier.fillMaxSize().padding(16.dp),
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

@Composable
fun HomeScreen() {
    @Composable
    fun TermsAndConditions() {
        val fullText = "By clicking Accept, you agree to our Privacy Policy and Terms of Service."
        val tags = listOf(
            Triple("Privacy Policy", "privacy", "https://composables.com/privacy"),
            Triple("Terms of Service", "terms", "https://composables.com/terms"),
        )

        val annotatedString = buildAnnotatedString {
            append(fullText)
            tags.forEach { (text, tag, url) ->
                val start = fullText.indexOf(text)
                val end = start + text.length

                addStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.SemiBold
                    ),
                    start = start,
                    end = end
                )

                addStringAnnotation(
                    tag = tag,
                    annotation = url,
                    start = start,
                    end = end
                )
            }
        }
        val uriHandler = LocalUriHandler.current
        ClickableText(
            style = MaterialTheme.typography.body1,
            text = annotatedString,
            onClick = { offset ->
                tags.firstNotNullOfOrNull { (_, tag) ->
                    annotatedString.getStringAnnotations(tag, offset, offset).firstOrNull()
                }?.let { string ->
                    uriHandler.openUri(string.item)
                }
            }
        )
    }

    Scaffold(
        contentColor = MaterialTheme.colors.onSurface,
        backgroundColor = MaterialTheme.colors.surface
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(top = 100.dp)
                .padding(horizontal = 24.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colors.primary,
                modifier = Modifier.size(80.dp)
            ) {
                Icon(
                    Icons.Rounded.Lock,
                    contentDescription = "Icon",
                    modifier = Modifier.padding(12.dp)
                )
            }

            Spacer(Modifier.height(36.dp))
            Text(
                text = "We respect your privacy",
                style = MaterialTheme.typography.h4
            )
            Spacer(Modifier.height(36.dp))
            Text(
                text = "We take your personal information seriously and are committed to keeping it secure. We only collect the necessary data to provide you with the best possible service and never share or sell your information to third parties without your explicit consent.",
            )
            Spacer(Modifier.height(12.dp))
            TermsAndConditions()

            Spacer(Modifier.weight(1f))
            Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
                Text("Accept")
            }
            TextButton(
                onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.onSurface)
            ) {
                Text("Reject")
            }
            Spacer(Modifier.padding(12.dp))
        }
    }
}
