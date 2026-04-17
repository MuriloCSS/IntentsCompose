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
