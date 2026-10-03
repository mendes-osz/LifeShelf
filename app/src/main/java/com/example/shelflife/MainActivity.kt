package com.example.shelflife

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items as itemsGrade
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shelflife.ui.theme.*
import com.example.shelflife.model.Amigo
import com.example.shelflife.ui.theme.screens.TelaRanking

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShelflifeTheme {
                val amigos = remember {
                    mutableStateListOf(
                        Amigo(0, "Ovin", 5400, imagemRes = R.drawable.amigo_0, livrosLidos = 12, sequenciaDias = 21),
                        Amigo(1, "Cogumelito", 8550, imagemRes = R.drawable.amigo_1, livrosLidos = 7, sequenciaDias = 4),
                        Amigo(2, "Sapinho", 3000, imagemRes = R.drawable.amigo_2, livrosLidos = 15, sequenciaDias = 1),
                        Amigo(3, "Nome", 1200, imagemRes = R.drawable.amigo_3, livrosLidos = 3, sequenciaDias = 9)
                    )
                }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BarraNavegacao() }
                ) { innerPadding ->
                    TelaRanking(amigos = amigos, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

data class Livro(val id: Int, val titulo: String)
data class LivroHome(val id: Int, val titulo: String, @DrawableRes val imagemRes: Int? = null)
data class AmigoHome(val id: Int, val nome: String, @DrawableRes val imagemRes: Int? = null, val emoji: String = "🙂")

@Preview(showBackground = true)
@Composable
fun TelaPerfil(modifier: Modifier = Modifier) {
    val livros = remember {
        listOf(
            Livro(0, "1984"),
            Livro(1, "O Código Da Vinci"),
            Livro(2, "Labirinto do Fauno"),
            Livro(3, "Um Conto para o Ser Tempo"),
            Livro(4, "O Sol e a Estrela")
        )
    }

    val amigos = remember {
        listOf(
            Amigo(0, "Ovin", 5400, "🐣"),
            Amigo(1, "Cogumelito", 8550, "🍄"),
            Amigo(2, "Sapinho", 3000, "🐸")
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFF4E3B31))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.DarkGray),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "🙂", fontSize = 48.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Nome do Usuário",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Tempo total lendo livros",
            color = Color(0xFFB0A79E),
            fontSize = 13.sp
        )
        Text(
            text = "142h 30min",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        CabecalhoSecao(titulo = "Livros mais lidos")
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow {
            items(livros, key = { it.id }) { livro ->
                ItemLivro(livro = livro)
                Spacer(modifier = Modifier.width(12.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Nível: 3", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF3A2C24))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFC08552))
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        CabecalhoSecao(titulo = "Amigos")
        Text(
            text = "Você leu mais que 2 dos seus 3 amigos",
            color = Color(0xFFB0A79E),
            fontSize = 13.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow {
            items(amigos, key = { it.id }) { amigo ->
                ItemAmigo(amigo = amigo)
                Spacer(modifier = Modifier.width(12.dp))
            }
        }
    }
}

@Composable
fun CabecalhoSecao(titulo: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = titulo, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        IconButton(onClick = { }) {
            Text(text = "→", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun BarraNavegacao() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF3A2C24))
            .padding(vertical = 12.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ItemNavegacao(emoji = "👑", texto = "Ranking")
        ItemNavegacao(emoji = "🖊️", texto = "Anotar")
        ItemNavegacao(emoji = "📚", texto = "Estante")
        ItemNavegacao(emoji = "⋯", texto = "Mais")
    }
}

@Composable
fun ItemNavegacao(emoji: String, texto: String) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF4E3B31))
            .clickable { }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = emoji, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = texto, color = Color.White, fontSize = 12.sp)
    }
}

@Composable
fun ItemLivro(livro: Livro) {
    Card(
        modifier = Modifier.width(90.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF3A2C24))
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(width = 74.dp, height = 100.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFF6B4F3F)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "📖", fontSize = 28.sp)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = livro.titulo,
                color = Color.White,
                fontSize = 11.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ItemAmigo(amigo: Amigo) {
    Card(
        modifier = Modifier.width(80.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF3A2C24))
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                Text(text = amigo.emoji, fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = amigo.nome, color = Color.White, fontSize = 12.sp)
        }
    }
}

// ── TELA INICIAL ──────────────────────────────────────────────────────────────

@Preview(showBackground = true)
@Composable
fun TelaInicio(modifier: Modifier = Modifier) {

    val livros = remember {
        mutableStateListOf(
            LivroHome(0, "1984", imagemRes = R.drawable.livro_1984),
            LivroHome(1, "O Código Da Vinci", imagemRes = R.drawable.o_codigo_da_vinci),
            LivroHome(2, "Labirinto do Fauno", imagemRes = R.drawable.labirinto_do_fauno),
            LivroHome(3, "Um Conto para Ser Tempo", imagemRes = R.drawable.um_conto_para_ser_tempo),
            LivroHome(4, "O Sol e a Estrela", imagemRes = R.drawable.o_sol_e_a_estrela),
            LivroHome(5, "Enterrem Nossos Ossos à Meia-Noite", imagemRes = R.drawable.enterrem_nossos_ossos)
        )
    }

    val ranking = remember {
        mutableStateListOf(
            AmigoHome(0, "Nome", R.drawable.amigo_0),
            AmigoHome(1, "Nome", R.drawable.amigo_1),
            AmigoHome(2, "Nome", R.drawable.amigo_2),
            AmigoHome(3, "Nome", R.drawable.amigo_3)
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(CorFundo)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        CartaoResumoUsuario(nome = "Nome", minutosHoje = 25, imagemRes = R.drawable.usuario)

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Column(modifier = Modifier.weight(1.4f)) {
                Text(
                    text = "Livros Principais",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                GradeLivrosPrincipais(livros = livros)

                Spacer(modifier = Modifier.height(12.dp))

                BotaoAdicionarLivro(
                    aoAdicionar = { nomeLivro ->
                        livros.add(LivroHome(id = livros.size, titulo = nomeLivro))
                    }
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Ranking",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                PreviaRanking(ranking = ranking)
            }
        }
    }
}

@Composable
private fun CartaoResumoUsuario(
    nome: String,
    minutosHoje: Int,
    @DrawableRes imagemRes: Int? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CorCartao)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.DarkGray),
            contentAlignment = Alignment.Center
        ) {
            if (imagemRes != null) {
                Image(
                    painter = painterResource(id = imagemRes),
                    contentDescription = "Foto de perfil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Text(text = "🙂", fontSize = 26.sp)
            }
        }
        Column {
            Text(text = nome, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Hoje: $minutosHoje min lidos",
                color = CorTextoSecundario,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
private fun GradeLivrosPrincipais(livros: List<LivroHome>) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(CorCartao)
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        itemsGrade(livros, key = { it.id }) { livro ->
            CapaLivro(livro = livro, modifier = Modifier.width(80.dp))
        }
    }
}

@Composable
private fun CapaLivro(livro: LivroHome, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .aspectRatio(0.72f)
            .clip(RoundedCornerShape(6.dp))
            .background(Color(0xFF6B4F3F)),
        contentAlignment = Alignment.Center
    ) {
        if (livro.imagemRes != null) {
            Image(
                painter = painterResource(id = livro.imagemRes),
                contentDescription = "Capa de ${livro.titulo}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
        } else {
            Text(text = "📖", fontSize = 22.sp)
        }
    }
}

@Composable
private fun BotaoAdicionarLivro(aoAdicionar: (String) -> Unit) {
    val context = LocalContext.current

    var novoLivro by remember { mutableStateOf("") }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(6.dp))
            .background(CorCartao)
            .padding(16.dp),
    ) {

        OutlinedTextField(
            value = novoLivro,
            onValueChange = { novoLivro = it },
            label = { Text(text = "Nome do livro", color = Color.White )},
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = CorDestaque,
                unfocusedBorderColor = CorTextoSecundario,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (novoLivro.isNotBlank()) {
                    aoAdicionar(novoLivro)

                    Toast.makeText(
                        context,
                        "Livro \"$novoLivro\" adicionado!",
                        Toast.LENGTH_SHORT
                    ).show()

                    novoLivro = ""
                } else {
                    Toast.makeText(
                        context,
                        "Digite o nome de um livro.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = CorDestaque,
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Adicionar livro")
        }
    }
}

@Composable
private fun PreviaRanking(ranking: List<AmigoHome>) {
    val coresPosicao = listOf(
        Color(0xFF2E2E2E),
        Color(0xFFC49A93),
        Color(0xFF8B3A2B),
        Color(0xFF7A5C55)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(CorCartao)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ranking.forEachIndexed { indice, amigo ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(coresPosicao.getOrElse(indice) { CorCartao }),
                contentAlignment = Alignment.Center
            ) {
                if (amigo.imagemRes != null) {
                    Image(
                        painter = painterResource(id = amigo.imagemRes),
                        contentDescription = "Foto de ${amigo.nome}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(10.dp))
                    )
                } else {
                    Text(text = amigo.emoji, fontSize = 26.sp)
                }
            }
        }
    }
}

// ── TELA ESTANTE ──────────────────────────────────────────────────────────────

@Preview(showBackground = true)
@Composable
fun TelaEstante(modifier: Modifier = Modifier) {
    val livros = remember {
        listOf(
            LivroHome(0,  "Enterrem Nossos Ossos à Meia-Noite", R.drawable.enterrem_nossos_ossos),
            LivroHome(1,  "Um Conto Para Ser Tempo",                          R.drawable.um_conto_para_ser_tempo),
            LivroHome(2,  "1984",                               R.drawable.livro_1984),
            LivroHome(3,  "O Sol e a Estrela",                  R.drawable.o_sol_e_a_estrela),
            LivroHome(4,  "Labirinto do Fauno",                 R.drawable.labirinto_do_fauno),
            LivroHome(5,  "O Código Da Vinci",                  R.drawable.o_codigo_da_vinci),
            LivroHome(6,  "1793",                               null),
            LivroHome(7,  "O Ponto de Vista do Leitor Onisciente",                 null),
            LivroHome(8,  "A Vida Invisível de Addie LaRue",                      null),
            LivroHome(9,  "Battle Royale",                      null),
            LivroHome(10, "Noite na Taverna",                            null),
            LivroHome(11, "Assassinato Express do Oriente",     null),
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CorFundo)
    ) {
        Text(
            text = "Shelf",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsGrade(livros, key = { it.id }) { livro ->
                ItemLivroEstante(livro = livro)
            }
        }
    }
}

@Composable
private fun ItemLivroEstante(livro: LivroHome) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.67f)
            .clip(RoundedCornerShape(8.dp))
            .background(CorPlaceholder)
            .clickable{ },
        contentAlignment = Alignment.Center
    ) {
        if (livro.imagemRes != null) {
            Image(
                painter = painterResource(id = livro.imagemRes),
                contentDescription = livro.titulo,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "📖", fontSize = 26.sp)
                Text(
                    text = livro.titulo,
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 9.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }
        }
    }
}

// ── TELA DETALHES ─────────────────────────────────────────────────────────────

@Preview(showBackground = true)
@Composable
fun TelaDetalhes(modifier: Modifier = Modifier) {
    val livro = LivroHome(0, "Enterrem Nossos Ossos à Meia-Noite", R.drawable.enterrem_nossos_ossos)
    val autor = "V.E. Schwab"
    val paginasLidas = 328
    val paginasTotal = 664
    val tempoLido = "7h19min"
    val sinopse = "Esta é uma história sobre sede. 1532. Santo Domingo de la Calzada. Maria sempre foi uma menina de personalidade forte. Cresceu cheia de sonhos e com uma beleza que chamava a atenção. Mas ela sabe que no jogo ditado pelos homens, só lhe será permitido um papel: troféu ou peão. Quando uma misteriosa viajante aparece por Maria uma fuga da vida que a garota tanto despreza, ela faz uma escolha desesperada. E jura nunca se arrepender de nada. Esta é uma história sobre amor. 1837. Londres. A jovem Calla é solidária na propriedade da família no campo, até que a sua vida desmorona ao ser flagrada em um momento íntimo proibido e, como consequência, é enviada a Londres. Lá, seus corações solitários e seus desejos mais audaciosos ficam extremamente quando uma bela viúva lhe faz uma proposta. Ela só não imaginava que o preço da liberdade seria tão alto."

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CorFundo)
            .verticalScroll(rememberScrollState())
    ) {
        // ── Cabeçalho: capa + título/autor ──
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(CorCartao)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(90.dp)
                    .aspectRatio(0.67f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(CorPlaceholder)
            ) {
                if (livro.imagemRes != null) {
                    Image(
                        painter = painterResource(id = livro.imagemRes),
                        contentDescription = livro.titulo,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = livro.titulo,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp
                )
                Text(
                    text = autor,
                    color = CorTextoSecundario,
                    fontSize = 13.sp
                )
            }
        }

        // ── Progresso ──
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            val progresso = paginasLidas.toFloat() / paginasTotal.toFloat()
            Text(
                text = "Páginas: $paginasLidas / $paginasTotal (${(progresso * 100).toInt()}%)",
                color = Color.White,
                fontSize = 14.sp
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(CorCartao)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progresso)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(5.dp))
                        .background(CorDestaque)
                )
            }
            Text(
                text = "Tempo: $tempoLido",
                color = CorTextoSecundario,
                fontSize = 13.sp
            )
        }

        // ── Sinopse ──
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(CorCartao)
                .padding(14.dp)
        ) {
            Text(
                text = sinopse,
                color = CorTextoSecundario,
                fontSize = 12.sp,
                lineHeight = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ── Botão Anotações ──
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(CorDestaque)
                .clickable { }
                .padding(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = CorDestaque
            ),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            Text(
                text = "Anotações",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}