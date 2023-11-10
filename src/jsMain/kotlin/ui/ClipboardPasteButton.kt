package ui

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import kotlinx.browser.window

@Composable
fun ClipBoardPasteButton(
    onPaste: (String) -> Unit
) {
    // val clipboardManager = LocalClipboardManager.current
    val clipboardManager = window.navigator.clipboard

    Button(onClick = {
        clipboardManager.readText().then {
            onPaste(it)
        }
    }) {
        Text("Paste from Clipboard")
    }
}
