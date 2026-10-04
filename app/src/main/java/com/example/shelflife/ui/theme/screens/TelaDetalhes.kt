package com.example.shelflife.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.shelflife.model.LivroHome
import com.example.shelflife.ui.theme.CorCartao
import com.example.shelflife.ui.theme.CorDestaque
import com.example.shelflife.ui.theme.CorFundo
import com.example.shelflife.ui.theme.CorPlaceholder
import com.example.shelflife.ui.theme.CorTextoSecundario

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