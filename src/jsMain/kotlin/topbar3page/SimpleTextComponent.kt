package topbar3page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.macaosoftware.component.core.BackPressHandler
import com.macaosoftware.component.core.Component

class SimpleTextComponent(
    val text: String,
    val onMessage: (Msg) -> Unit
) : Component() {

    override fun onStart() {
        println("SimpleTextComponent::start()")
    }

    override fun onStop() {
        println("SimpleTextComponent::stop()")
    }

    sealed interface Msg {
        object Next : Msg
    }

    @Composable
    override fun Content(modifier: Modifier) {
        println("SimpleTextComponent::Composing()")
        BackPressHandler()
        Box(modifier = modifier.fillMaxSize()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                text = "Sometimes your IDE stops working due to corruption of files in the file disk." +
                        "This is something that happens once in a while but we all go through it." +
                        "All of the sudden you notice the project doesn't compile ...",
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
            Button(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(vertical = 40.dp),
                onClick = { onMessage(Msg.Next) }
            ) {
                Text(text = "Next")
            }
        }
    }

}
