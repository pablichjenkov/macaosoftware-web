import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.pablichj.templato.component.core.BrowserComponentRender
import com.pablichj.templato.component.core.BrowserViewportWindow
import com.pablichj.templato.component.core.topbar.TopBarState
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    onWasmReady {
        BrowserViewportWindow("Macao Software") {
            RootView()
        }
    }
}

@Composable
internal fun RootView() {
    val rootComponent = remember(Unit) { PanelBuilder.build() }
    val topBarState = remember {
        TopBarState {
            //handleBackPressed()
        }
    }
    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeContent),
        topBar = {
            Box(modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
                .background(Color.LightGray)
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(start = 16.dp),
                    text = "Macao Software",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = TextUnit(26F, TextUnitType.Sp),
                        fontWeight = FontWeight.Bold
                    )
                )
                //TopBar(topBarState)
            }

        }
    ) { paddingValues ->
        BrowserComponentRender(
            rootComponent = rootComponent,
            onBackPressEvent = {
                println("Back press dispatched in root component")
            }
        )
    }
}
