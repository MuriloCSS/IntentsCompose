package br.edu.ifsp.scl.sc3038467.intentscompose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class ParameterActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val receivedParameter = intent.getStringExtra("EXTRA_PARAMETER") ?: ""

        setContent {
            MaterialTheme {
                var textState by remember { mutableStateOf(receivedParameter) }

                ParameterScreen(
                    parameterText = textState,
                    onParameterChange = { newText -> textState = newText },
                    onSaveAndQuitClick = {
                        val returnIntent = Intent()
                        returnIntent.putExtra("EXTRA_PARAMETER", textState)
                        setResult(RESULT_OK, returnIntent)
                        finish()
                    },
                    title = getString(R.string.app_name),
                    subtitle = this@ParameterActivity.javaClass.simpleName
                )
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParameterScreen(
    parameterText: String,
    onParameterChange: (String) -> Unit,
    onSaveAndQuitClick: () -> Unit,
    title: String,
    subtitle: String
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(text = title)
                        Text(text = subtitle, style = MaterialTheme.typography.bodySmall)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            TextField(
                value = parameterText,
                onValueChange = onParameterChange,
                modifier = Modifier.fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(fontSize = 24.sp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onSaveAndQuitClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Save and quit")
            }
        }
    }
}