package com.example.somativaddm.controller.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import com.example.somativaddm.controller.AppModule
import com.example.somativaddm.controller.Game.Game
import com.example.somativaddm.controller.Game.GameRepository
import com.example.somativaddm.controller.Game.Singleton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllGamesActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = AppModule().provideGameRepository(AppModule().providesGameDatabase(applicationContext))

        setContent{
            Column( verticalArrangement = Arrangement.SpaceBetween) {
                val coroutineScope = rememberCoroutineScope()
                val gameList by remember{ mutableStateOf(mutableListOf<Game>()) }
                var isLoading by remember {
                    mutableStateOf(true)
                }
                val launch = coroutineScope.launch {
                    loadGameList(gameList, repository)
                    isLoading = false
                }
                if(isLoading)
                    LoadingScreen()
                else
                    GameList(gameList = gameList, context = LocalContext.current)

                //val gameList = createSampleGameList(25)

            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GameCard(game: Game, context:Context){
    var isExpanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(8.dp)
            .combinedClickable (
                onClick = {
                    isExpanded = !isExpanded
                },
                onLongClick = {
                    Singleton.selectedGame = game
                    val intent = Intent(context, GameViewActivity::class.java)
                    startActivity(context, intent, null)

                }
            ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)

    ) {
        Column(modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {

            GameImage(game = game)

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
@Composable
fun GameList(gameList: List<Game>, context: Context){
    LazyVerticalGrid(
        modifier = Modifier.padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.Center,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp)

    ){
        items(
            items = gameList,
            itemContent = {
                GameCard(game = it,context)
            }
        )
    }
}

private suspend fun loadGameList(gameList:MutableList<Game>, repository:GameRepository){
        val fetchedGameList = repository.getAllGames()
        gameList.clear()
        gameList.addAll(fetchedGameList)
}

@Composable
fun LoadingScreen(){
    Box(
        modifier = Modifier.fillMaxWidth()
    ){
        CircularProgressIndicator()
    }
}
@Composable
fun topBar(context: Context, text:String, Activity: () -> Unit){
    Surface(modifier = Modifier.fillMaxWidth() ) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween){
                Spacer(modifier = Modifier.height(5.dp))
            IconButton(onClick = {
                Activity
            }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "ArrowBack",
                    modifier = Modifier.size(32.dp))
            }

            Surface(modifier =Modifier.fillMaxWidth()) {
                Text(
                    text = text,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    color = Color.LightGray,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

        }
        Spacer(modifier = Modifier.height(55.dp))

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

@Composable
private fun GameImage(game: Game) {
    AsyncImage(
        model = game.thumbURL,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(8.dp)
            .size(100.dp)
            .clip(RoundedCornerShape(corner = CornerSize(16.dp))))
}