package com.example.shelflife.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shelflife.model.Amigo
import com.example.shelflife.model.Livro

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