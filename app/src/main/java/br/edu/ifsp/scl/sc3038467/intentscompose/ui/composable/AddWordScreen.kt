package br.edu.ifsp.scl.sc3038467.intentscompose.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AddWordScreen(modifier: Modifier = Modifier,
                  textoRecebido: String,
                  onConcatenarClick: (String) -> Unit) {

    var novaPalavra by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
    ) {
        OutlinedTextField(
            value = textoRecebido,
            onValueChange = { },
            readOnly = true,
            label = { Text("Texto atual") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = novaPalavra,
            onValueChange = { novaPalavra = it},
            label = { Text("Digite a nova palavra") },
            modifier = Modifier.fillMaxWidth()
        )

        Button (
            onClick = { onConcatenarClick(novaPalavra) },
            modifier = Modifier.fillMaxWidth().padding(top = 32.dp)
        ) {
            Text("Concatenar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddWordScreenPreview() {
    Surface {
        AddWordScreen(modifier = Modifier,
            textoRecebido = "Olá mundo",
            onConcatenarClick = {})
    }
}