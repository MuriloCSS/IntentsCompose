package br.edu.ifsp.scl.sc3038467.intentscompose.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.edu.ifsp.scl.sc3038467.intentscompose.ui.composable.AddWordScreen
import br.edu.ifsp.scl.sc3038467.intentscompose.ui.composable.HomeScreen

private const val NOVA_PALAVRA = "nova_palavra"

@Composable
fun MainNavHost(navHostController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.HomeScreen.route
    ) {

        composable(route = Screen.HomeScreen.route) { backStackEntry ->
            val palavraDevolvida = backStackEntry.savedStateHandle.get<String>(NOVA_PALAVRA) ?: ""
            if (palavraDevolvida.isNotEmpty()) {
                backStackEntry.savedStateHandle.remove<String>(NOVA_PALAVRA)
            }

            HomeScreen(
                palavraDevolvida = palavraDevolvida,
                modifier = modifier,
                onAdicionarClick = { textoAtual ->

                    val textoParaEnviar = if (textoAtual.isEmpty()) " " else textoAtual


                    navHostController.navigate("${Screen.AddWordScreen.route}/${Uri.encode(textoParaEnviar)}")
                }
            )
        }

        composable(
            route = "${Screen.AddWordScreen.route}/{stringAtual}",
            arguments = listOf(
                navArgument("stringAtual") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            var stringAtual = backStackEntry.arguments?.getString("stringAtual") ?: ""

            if (stringAtual == " ") stringAtual = ""

            AddWordScreen(
                textoRecebido = stringAtual,
                modifier = modifier,
                onConcatenarClick = { palavraDigitada ->
                    navHostController.previousBackStackEntry?.savedStateHandle?.set(NOVA_PALAVRA, palavraDigitada)

                    navHostController.popBackStack()
                }
            )
        }
    }
}