package com.example.somativaddm.controller.things

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.somativaddm.model.FreeToGamesClient
import com.example.somativaddm.model.Game
import kotlinx.coroutines.launch

@Composable
fun GameContent() {
    var games by remember { mutableStateOf<List<Game>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val response = FreeToGamesClient.getFreeGames()
            games = response
        }
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2)  ,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(games.size) { index ->
            val game = games[index]
            GameListItem(game = game)
            }
    }
}

@Preview
@Composable
fun previewContent() {
    GameContent()
}