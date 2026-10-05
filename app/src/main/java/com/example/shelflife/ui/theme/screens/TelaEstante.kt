package com.example.shelflife.ui.theme.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shelflife.data.LivrosIniciais
import com.example.shelflife.model.Livro
import com.example.shelflife.ui.theme.CorCartao
import com.example.shelflife.ui.theme.CorFundo
import com.example.shelflife.ui.theme.CorTextoSecundario
import com.example.shelflife.ui.theme.components.BarraProgresso
import com.example.shelflife.ui.theme.components.CampoTexto
import com.example.shelflife.ui.theme.components.CapaLivro

@Composable
fun TelaEstante(
    livros: List<Livro>,
    aoAdicionar: (titulo: String, autor: String, paginas: Int) -> Unit,
    aoRemover: (Livro) -> Unit,
    aoClicarLivro: (Livro) -> Unit,
    modifier: Modifier = Modifier
) {
    var livroParaRemover by remember { mutableStateOf<Livro?>(null) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(CorFundo)
            .imePadding(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Column {
                Text(
                    text = "Estante",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Toque num livro para ver os detalhes",
                    color = CorTextoSecundario,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                FormularioNovoLivro(aoAdicionar = aoAdicionar)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Seus livros (${livros.size})",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (livros.isEmpty()) {
            item {
                Text(
                    text = "Sua estante está vazia. Adicione um livro acima.",
                    color = CorTextoSecundario,
                    fontSize = 14.sp
                )
            }
        } else {
            items(livros, key = { it.id }) { livro ->
                ItemLivroEstante(
                    livro = livro,
                    aoClicar = { aoClicarLivro(livro) },
                    aoRemover = { livroParaRemover = livro }
                )
            }
        }
    }

    livroParaRemover?.let { livro ->
        AlertDialog(
            onDismissRequest = { livroParaRemover = null },
            title = { Text("Remover livro?") },
            text = { Text("\"${livro.titulo}\" sairá da sua estante.") },
            confirmButton = {
                TextButton(onClick = {
                    aoRemover(livro)
                    livroParaRemover = null
                }) { Text("Remover") }
            },
            dismissButton = {
                TextButton(onClick = { livroParaRemover = null }) { Text("Cancelar") }
            }
        )
    }
}

@Composable
private fun FormularioNovoLivro(aoAdicionar: (String, String, Int) -> Unit) {
    val context = LocalContext.current
    var titulo by remember { mutableStateOf("") }
    var autor by remember { mutableStateOf("") }
    var paginas by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CorCartao)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Adicionar livro",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            CampoTexto(valor = titulo, aoMudar = { titulo = it }, rotulo = "Título")
            CampoTexto(valor = autor, aoMudar = { autor = it }, rotulo = "Autor (opcional)")
            CampoTexto(
                valor = paginas,
                aoMudar = { paginas = it.filter(Char::isDigit).take(5) },
                rotulo = "Total de páginas (opcional)",
                numerico = true
            )
            Button(
                onClick = {
                    if (titulo.isBlank()) {
                        Toast.makeText(context, "Digite o título do livro.", Toast.LENGTH_SHORT).show()
                    } else {
                        aoAdicionar(titulo.trim(), autor.trim(), paginas.toIntOrNull() ?: 0)
                        Toast.makeText(
                            context,
                            "Livro \"${titulo.trim()}\" adicionado!",
                            Toast.LENGTH_SHORT
                        ).show()
                        titulo = ""
                        autor = ""
                        paginas = ""
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = com.example.shelflife.ui.theme.CorDestaque,
                    contentColor = Color.White
                )
            ) {
                Text("Adicionar livro")
            }
        }
    }
}

@Composable
private fun ItemLivroEstante(
    livro: Livro,
    aoClicar: () -> Unit,
    aoRemover: () -> Unit
) {
    val progresso = if (livro.paginasTotal > 0) {
        livro.paginasLidas.toFloat() / livro.paginasTotal
    } else {
        0f
    }

    Card(
        onClick = aoClicar,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CorCartao)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CapaLivro(
                livro = livro,
                modifier = Modifier
                    .width(56.dp)
                    .aspectRatio(0.67f)
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = livro.titulo,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(text = livro.autor, color = CorTextoSecundario, fontSize = 13.sp)
                if (livro.paginasTotal > 0) {
                    BarraProgresso(progresso = progresso, altura = 6.dp)
                    Text(
                        text = "${(progresso * 100).toInt()}% • ${livro.paginasLidas}/${livro.paginasTotal} páginas",
                        color = CorTextoSecundario,
                        fontSize = 12.sp
                    )
                } else {
                    Text(
                        text = "Páginas não informadas",
                        color = CorTextoSecundario,
                        fontSize = 12.sp
                    )
                }
            }

            IconButton(onClick = aoRemover) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remover ${livro.titulo}",
                    tint = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviaTelaEstante() {
    TelaEstante(
        livros = LivrosIniciais,
        aoAdicionar = { _, _, _ -> },
        aoRemover = {},
        aoClicarLivro = {}
    )
}