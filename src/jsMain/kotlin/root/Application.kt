package root

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.macaosoftware.component.BrowserComponentRender
import com.macaosoftware.component.panel.PanelComponent
import com.macaosoftware.component.panel.PanelComponentDefaults
import com.macaosoftware.platform.JsBridge

@Composable
internal fun Application() {

    val jsBridge = remember { JsBridge() }

    val panelComponent = remember(Unit) {
        PanelComponent<AppLeftPanelViewModel>(
            viewModelFactory = AppLeftPanelViewModelFactory(
                panelStatePresenter = PanelComponentDefaults.createPanelStatePresenter(),
            ),
            content = PanelComponentDefaults.PanelComponentView
        )
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
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
            }

        }
    ) { paddingValues ->
        Box(Modifier.padding(paddingValues)) {
            BrowserComponentRender(
                rootComponent = panelComponent,
                jsBridge = jsBridge,
                onBackPress = {
                    println("Back press dispatched in root component")
                }
            )
        }
    }
}
