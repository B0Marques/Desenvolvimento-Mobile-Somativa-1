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
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import com.example.somativaddm.controller.AppModule
import com.example.somativaddm.controller.Game.Game
import com.example.somativaddm.controller.Game.GameRepository
import com.example.somativaddm.controller.Game.Singleton
import com.example.somativaddm.controller.ui.theme.Pink40
import com.example.somativaddm.controller.ui.theme.Purple80
import com.example.somativaddm.controller.ui.theme.PurpleGrey80
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllGamesActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = AppModule().provideGameRepository(AppModule().providesGameDatabase(applicationContext))

        setContent{
            Surface(color = PurpleGrey80) {
                Column( verticalArrangement = Arrangement.SpaceBetween) {
                    val coroutineScope = rememberCoroutineScope()
                    val gameList by remember{ mutableStateOf(mutableListOf<Game>()) }
                    var isLoading by remember {
                        mutableStateOf(true)
                    }
                    coroutineScope.launch {
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
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GameCard(game: Game, context:Context){
    var isExpanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(8.dp)
            .combinedClickable(
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
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardColors(Purple80, Pink40,Color.Gray,Color.Gray)

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
        val fetchedGameList = repository.getAllGamesOrdered()
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