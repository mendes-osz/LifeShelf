package com.example.shelflife.navigation

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.shelflife.model.Amigo
import com.example.shelflife.ui.theme.CorCartao
import com.example.shelflife.ui.theme.CorDestaque
import com.example.shelflife.ui.theme.CorFundo
import com.example.shelflife.ui.theme.CorTextoSecundario
import com.example.shelflife.ui.theme.screens.AmigosStorage
import com.example.shelflife.ui.theme.screens.TelaAmigos
import com.example.shelflife.ui.theme.screens.TelaDetalheAmigo
import com.example.shelflife.ui.theme.screens.TelaDetalhes
import com.example.shelflife.ui.theme.screens.TelaEstante
import com.example.shelflife.ui.theme.screens.TelaInicio
import com.example.shelflife.ui.theme.screens.TelaPerfil
import com.example.shelflife.ui.theme.screens.TelaRanking

private data class ItemBarra(val rota: String, val emoji: String, val rotulo: String)

private val itensBarra = listOf(
    ItemBarra(Rotas.HOME, "🏠", "Home"),
    ItemBarra(Rotas.RANKING, "👑", "Ranking"),
    ItemBarra(Rotas.ESTANTE, "📚", "Estante"),
    ItemBarra(Rotas.AMIGOS, "👥", "Amigos"),
    ItemBarra(Rotas.PERFIL, "🙂", "Perfil")
)

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    val amigos = remember {
        mutableStateListOf<Amigo>().apply { addAll(AmigosStorage.carregar(context)) }
    }

    fun adicionarAmigo(amigo: Amigo) {
        if (amigos.none { it.id == amigo.id }) {
            amigos.add(0, amigo)
            AmigosStorage.salvar(context, amigos)
        }
    }

    fun removerAmigo(amigo: Amigo) {
        amigos.removeAll { it.id == amigo.id }
        AmigosStorage.salvar(context, amigos)
    }

    val entradaAtual by navController.currentBackStackEntryAsState()
    val rotaAtual = entradaAtual?.destination?.route

    Scaffold(
        containerColor = CorFundo,
        bottomBar = {
            NavigationBar(containerColor = CorCartao) {
                itensBarra.forEach { item ->
                    NavigationBarItem(
                        selected = rotaAtual == item.rota,
                        onClick = {
                            navController.navigate(item.rota) {
                                popUpTo(Rotas.HOME)
                                launchSingleTop = true
                            }
                        },
                        icon = { Text(text = item.emoji, fontSize = 20.sp) },
                        label = { Text(text = item.rotulo, fontSize = 11.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedTextColor = Color.White,
                            unselectedTextColor = CorTextoSecundario,
                            indicatorColor = CorDestaque
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Rotas.HOME,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        ) {
            composable(Rotas.HOME) { TelaInicio() }

            composable(Rotas.RANKING) {
                TelaRanking(
                    amigos = amigos,
                    aoClicarAmigo = { amigo -> navController.navigate(Rotas.detalheAmigo(amigo.id)) },
                    aoRemoverAmigo = { amigo -> removerAmigo(amigo) }
                )
            }

            composable(Rotas.AMIGOS) {
                TelaAmigos(
                    amigos = amigos,
                    aoAdicionar = { amigo -> adicionarAmigo(amigo) },
                    aoRemover = { amigo -> removerAmigo(amigo) },
                    aoAbrirDetalhe = { id -> navController.navigate(Rotas.detalheAmigo(id)) }
                )
            }

            composable(
                route = Rotas.DETALHE_AMIGO,
                arguments = listOf(navArgument("amigoId") { type = NavType.IntType })
            ) { entrada ->
                val id = entrada.arguments?.getInt("amigoId")
                TelaDetalheAmigo(
                    amigo = amigos.firstOrNull { it.id == id },
                    amigos = amigos,
                    aoVoltar = { navController.popBackStack() },
                    aoRemover = { amigo -> removerAmigo(amigo) }
                )
            }

            composable(Rotas.ESTANTE) { TelaEstante() }

            composable(Rotas.PERFIL) {
                TelaPerfil(
                    amigos = amigos,
                    aoVerEstante = { navController.navigate(Rotas.ESTANTE) },
                    aoVerAmigos = { navController.navigate(Rotas.AMIGOS) },
                    aoAbrirAmigo = { id -> navController.navigate(Rotas.detalheAmigo(id)) }
                )
            }

            composable(
                route = Rotas.DETALHE_LIVRO,
                arguments = listOf(navArgument("livroId") { type = NavType.IntType })
            ) { TelaDetalhes() }
        }
    }
}