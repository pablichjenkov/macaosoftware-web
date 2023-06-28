import androidx.compose.runtime.remember
import com.pablichj.incubator.uistate3.BrowserViewportWindow
import com.pablichj.templato.component.core.BrowserComponentRender
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    onWasmReady {
        BrowserViewportWindow("Macao Software") {
            val rootComponent = remember (Unit) { PanelBuilder.build() }
            BrowserComponentRender(
                rootComponent = rootComponent,
                onBackPressEvent = {
                    println("Back press dispatched in root component")
                }
            )
        }
    }
}
