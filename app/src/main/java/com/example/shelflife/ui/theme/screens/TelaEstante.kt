package com.example.shelflife.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items as itemsGrade
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shelflife.R
import com.example.shelflife.model.LivroHome
import com.example.shelflife.ui.theme.CorFundo
import com.example.shelflife.ui.theme.CorPlaceholder

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