package com.example.somativaddm.controller.things

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.somativaddm.model.Game

@Composable
fun GameListItem(game: Game) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Gray
        ),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(text = game.title)
                Text(text = game.genre)
                Text(text = game.platform)
            }
    }
}

@Preview
@Composable
fun PreviewGameItem () {
//    var games by remember { mutableStateOf<List<Game>>(emptyList()) }
//    val coroutineScope = rememberCoroutineScope()
//
//    LaunchedEffect(Unit) {
//        coroutineScope.launch {
//            val response = FreeToGamesClient.getFreeGames()
//            games = response
//        }
//    }
    val game = Game(123, "AAA", "a","a","a","aa","abc", "aa","sgsg","123", "sdagsa")
    GameListItem(game)
}