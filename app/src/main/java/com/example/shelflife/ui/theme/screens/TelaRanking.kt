package com.example.shelflife.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shelflife.R
import com.example.shelflife.model.Amigo
import com.example.shelflife.ui.theme.CorCartao
import com.example.shelflife.ui.theme.CorDestaque
import com.example.shelflife.ui.theme.CorFundo
import com.example.shelflife.ui.theme.CorTextoSecundario

enum class CriterioRanking(val rotulo: String) {
    TEMPO("Tempo"),
    LIVROS("Livros"),
    SEQUENCIA("Sequência")
}

@Composable
fun TelaRanking(amigos: List<Amigo>, modifier: Modifier = Modifier) {
    var criterio by remember { mutableStateOf(CriterioRanking.TEMPO) }
    var busca by remember { mutableStateOf("") }

    val ordenados = when (criterio) {
        CriterioRanking.TEMPO -> amigos.sortedByDescending { it.minutosLidos }
        CriterioRanking.LIVROS -> amigos.sortedByDescending { it.livrosLidos }
        CriterioRanking.SEQUENCIA -> amigos.sortedByDescending { it.sequenciaDias }
    }

    val visiveis = ordenados
        .withIndex()
        .filter { it.value.nome.contains(busca.trim(), ignoreCase = true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CorFundo)
            .padding(16.dp)
    ) {
        Text(
            text = "Ranking",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Quem mais leu entre seus amigos",
            color = CorTextoSecundario,
            fontSize = 13.sp
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = busca,
            onValueChange = { busca = it },
            label = { Text(text = "Buscar amigo", color = Color.White) },
            leadingIcon = { Text(text = "🔍") },
            trailingIcon = {
                if (busca.isNotEmpty()) {
                    IconButton(onClick = { busca = "" }) {
                        Text(text = "✕", color = Color.White, fontSize = 16.sp)
                    }
                }
            },
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

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CriterioRanking.entries.forEach { opcao ->
                FilterChip(
                    selected = criterio == opcao,
                    onClick = { criterio = opcao },
                    label = { Text(text = opcao.rotulo) },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = CorCartao,
                        labelColor = Color.White,
                        selectedContainerColor = CorDestaque,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))

        if (visiveis.isEmpty()) {
            Text(
                text = "Nenhum amigo encontrado.",
                color = CorTextoSecundario,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(visiveis, key = { it.value.id }) { item ->
                    ItemRanking(
                        posicao = item.index + 1,
                        amigo = item.value,
                        criterio = criterio
                    )
                }
            }
        }
    }
}

@Composable
private fun ItemRanking(posicao: Int, amigo: Amigo, criterio: CriterioRanking) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CorCartao)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = if (posicao == 1) "👑" else "$posicao",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.width(32.dp)
            )

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                if (amigo.imagemRes != null) {
                    Image(
                        painter = painterResource(id = amigo.imagemRes),
                        contentDescription = "Foto de ${amigo.nome}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Text(text = amigo.emoji, fontSize = 22.sp)
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = amigo.nome,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = valorDoCriterio(amigo, criterio),
                    color = CorTextoSecundario,
                    fontSize = 13.sp
                )
            }
        }
    }
}

private fun valorDoCriterio(amigo: Amigo, criterio: CriterioRanking): String =
    when (criterio) {
        CriterioRanking.TEMPO -> formatarTempo(amigo.minutosLidos)
        CriterioRanking.LIVROS ->
            if (amigo.livrosLidos == 1) "1 livro" else "${amigo.livrosLidos} livros"
        CriterioRanking.SEQUENCIA ->
            if (amigo.sequenciaDias == 1) "1 dia seguido" else "${amigo.sequenciaDias} dias seguidos"
    }

private fun formatarTempo(minutos: Int): String = "${minutos / 60}h ${minutos % 60}min"

@Preview(showBackground = true)
@Composable
private fun PreviaTelaRanking() {
    TelaRanking(
        amigos = listOf(
            Amigo(0, "Ovin", 5400, imagemRes = R.drawable.amigo_0, livrosLidos = 12, sequenciaDias = 21),
            Amigo(1, "Cogumelito", 8550, imagemRes = R.drawable.amigo_1, livrosLidos = 7, sequenciaDias = 4),
            Amigo(2, "Sapinho", 3000, imagemRes = R.drawable.amigo_2, livrosLidos = 15, sequenciaDias = 1),
            Amigo(3, "Nome", 1200, imagemRes = R.drawable.amigo_3, livrosLidos = 3, sequenciaDias = 9)
        )
    )
}