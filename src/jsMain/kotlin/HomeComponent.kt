import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.consumeBackPressEvent

class HomeComponent : Component() {

    override fun start() {
        super.start()
        println("HomeComponent::start()")
    }

    override fun stop() {
        super.stop()
        println("HomeComponent::stop()")
    }

    @Composable
    override fun Content(modifier: Modifier) {
        println("HomeComponent::Composing()")
        consumeBackPressEvent()
        AcceptPrivacyScreen()
    }

}

@Composable
fun AcceptPrivacyScreen() {
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
        /*val uriHandler = LocalUriHandler.current
        ClickableText(
            style = MaterialTheme.typography.bodyLarge,
            text = annotatedString,
            onClick = { offset ->
                tags.firstNotNullOfOrNull { (_, tag) ->
                    annotatedString.getStringAnnotations(tag, offset, offset).firstOrNull()
                }?.let { string ->
                    uriHandler.openUri(string.item)
                }
            }
        )*/
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
