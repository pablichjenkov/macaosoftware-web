package texteditor

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.BasicRichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.OutlinedRichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichText
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RichEditorContent() {

    val basicRichTextState = rememberRichTextState()
    val richTextState = rememberRichTextState()
    val outlinedRichTextState = rememberRichTextState()

    LaunchedEffect(Unit) {
        richTextState.setHtml(
            """
            <p><b>RichTextEditor</b> is a <i>composable</i> that allows you to edit <u>rich text</u> content.</p>
            """.trimIndent()
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // BasicRichTextEditor
        Text(
            text = "BasicRichTextEditor:",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(8.dp))

        RichTextStyleRow(
            modifier = Modifier.fillMaxWidth(),
            state = basicRichTextState,
        )

        BasicRichTextEditor(
            modifier = Modifier.fillMaxWidth(),
            state = basicRichTextState,
        )

        Divider(modifier = Modifier.padding(vertical = 20.dp))

        // RichTextEditor
        Text(
            text = "RichTextEditor:",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(8.dp))

        RichTextStyleRow(
            modifier = Modifier.fillMaxWidth(),
            state = richTextState,
        )

        RichTextEditor(
            modifier = Modifier.fillMaxWidth(),
            state = richTextState,
        )

        Divider(modifier = Modifier.padding(vertical = 20.dp))

        // OutlinedRichTextEditor
        Text(
            text = "OutlinedRichTextEditor:",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(8.dp))

        RichTextStyleRow(
            modifier = Modifier.fillMaxWidth(),
            state = outlinedRichTextState,
        )

        OutlinedRichTextEditor(
            modifier = Modifier.fillMaxWidth(),
            state = outlinedRichTextState,
        )

        Divider(modifier = Modifier.padding(vertical = 20.dp))

        // RichText
        Text(
            text = "RichText:",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(8.dp))

        RichText(
            modifier = Modifier.fillMaxWidth(),
            state = richTextState,
        )
    }

}