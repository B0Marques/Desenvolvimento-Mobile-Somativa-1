package com.example.somativaddm.controller.Activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.somativaddm.controller.AppModule
import com.example.somativaddm.controller.Game.Game
import com.example.somativaddm.controller.Game.GameRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class AllGamesActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = AppModule().provideGameRepository(AppModule().providesGameDatabase(applicationContext))
        
        setContent{
            Surface {

                val gameList by remember{ mutableStateOf(mutableListOf<Game>()) }
                LaunchedEffect(key1 = true){
                    loadGameList(gameList,repository)
                }


                //val gameList = createSampleGameList(25)
                GameList(gameList = gameList)
            }
        }
    }
}
private suspend fun loadGameList(gameList:MutableList<Game>, repository:GameRepository){
    withContext(Dispatchers.IO){

        val fetchedGameList = repository.getAllGames()
        gameList.clear()
        gameList.addAll(fetchedGameList)
    }
}
@Composable
fun GameList(gameList: List<Game>, pageSize:Int = 10){
    val scrollState = rememberLazyGridState()
    LazyVerticalGrid(columns = GridCells.Fixed(2),
        state = scrollState,
        contentPadding = PaddingValues(16.dp)
    ){
        this.items(gameList.take(pageSize)) { game -> GameCard(game = game) }
    }
}

@Composable
fun GameCard(game: Game){
    var isExpanded by remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier
            .padding(8.dp)
            .clickable { isExpanded = !isExpanded }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            /*
            Icon(painter = painterResource(android.graphics.drawable.Icon.TYPE_RESOURCE), contentDescription = "Game Icon",
                modifier = Modifier.size(32.dp).align(Alignment.CenterHorizontally))
                
             */
            Icon(imageVector = Icons.Filled.Image,
                contentDescription = "Game Icon",
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.CenterHorizontally))
            Text(
                text = game.title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = if (isExpanded) MaterialTheme.colorScheme.primary else Color.Black
            )
            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Developer: ${game.developer}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Release Date: ${game.releaseDate}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Platform: ${game.platform}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Genre: ${game.genre}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewGameCard() {
    val game = Game(
        id = 1,
        title = "Example Game",
        thumbURL = "https://example.com/image.jpg",
        shortDescription = "Description",
        gameURL = "https://example.com/game",
        developer = "Developer",
        releaseDate = "2024-05-17",
        platform = "Platform",
        genre = "Genre"
    )
    GameCard(game = game)
}
@Composable
fun createSampleGameList(size:Int): List<Game> {
    val returnList: MutableList<Game> = mutableListOf<Game>().toMutableList()
    for(i in 1..size){
        val newGame= Game(
            id = i,
            title = "Example Game ${i}",
            thumbURL = "https://example.com/image.jpg",
            shortDescription = "Description",
            gameURL = "https://example.com/game",
            developer = "Developer",
            releaseDate = "2024-05-17",
            platform = "Platform",
            genre = "Genre"
        )
        returnList+=newGame
    }

    return returnList
}
