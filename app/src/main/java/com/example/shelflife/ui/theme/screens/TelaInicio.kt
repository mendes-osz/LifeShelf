package com.example.shelflife.ui.theme.screens

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items as itemsGrade
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shelflife.R
import com.example.shelflife.model.AmigoHome
import com.example.shelflife.model.LivroHome
import com.example.shelflife.ui.theme.CorCartao
import com.example.shelflife.ui.theme.CorDestaque
import com.example.shelflife.ui.theme.CorFundo
import com.example.shelflife.ui.theme.CorTextoSecundario

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