package common

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

class SimpleComponent(
    val text: String,
    val onMessage: (Msg) -> Unit
) : Component() {

    override fun onStart() {
        println("SimpleComponent::start()")
    }

    override fun onStop() {
        println("SimpleComponent::stop()")
    }

    sealed interface Msg {
        object Next : Msg
    }

    @Composable
    override fun Content(modifier: Modifier) {
        println("SimpleComponent::Composing()")
        BackPressHandler()
        Box(modifier = modifier.fillMaxSize()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                text = "$text ... coming soon",
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
