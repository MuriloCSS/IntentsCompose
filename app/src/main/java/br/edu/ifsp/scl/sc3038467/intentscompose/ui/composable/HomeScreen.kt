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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(modifier: Modifier = Modifier,
               palavraDevolvida: String = "",
               onAdicionarClick: (String) -> Unit,) {

    var textoAtual by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(palavraDevolvida) {
        if (palavraDevolvida.isNotBlank()) {
            if (textoAtual.isEmpty()) {
                textoAtual = palavraDevolvida
            } else {
                textoAtual = "$textoAtual $palavraDevolvida"
            }
        }
    }


    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
    ) {
        OutlinedTextField(
            value = textoAtual,
            onValueChange = { },
            readOnly = true,
            label = { Text("Texto atual") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {onAdicionarClick(textoAtual)},
            modifier = Modifier.fillMaxWidth().padding(top = 32.dp)
        ) {
            Text("Adicionar palavra")
        }

        Button(
            onClick = { textoAtual = "" },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text("Reiniciar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Surface {
        HomeScreen(modifier = Modifier,
            palavraDevolvida = "",
            onAdicionarClick = {})
    }
}