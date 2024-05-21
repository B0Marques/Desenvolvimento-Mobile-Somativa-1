package com.example.somativaddm.controller.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.example.somativaddm.controller.Game.Game
import com.example.somativaddm.controller.Game.Singleton

class GameViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val currentGame: Game = Singleton.selectedGame!!
        super.onCreate(savedInstanceState)
        setContent {
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.Start) {
                BoxWithConstraints {

                    Card(shape = RoundedCornerShape(25.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                        modifier = Modifier.fillMaxSize()) {
                        //headerBar(context = LocalContext.current)


                        Column(modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.Start) {

                            Header(game = currentGame, containerHeight = this@BoxWithConstraints.maxHeight)
                            GameInfo(game = currentGame)
                        }
                    }

                }

            }
        }
    }
}
@Composable
private fun GameImage(game: Game, containerHeight:Dp) {
    AsyncImage(
        model = game.thumbURL,
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .padding(8.dp)
            .heightIn(max = containerHeight / 2)
            .fillMaxWidth()
            .clip(RoundedCornerShape(25.dp)))
}

@Composable
private fun Header(game: Game,containerHeight:Dp){
    GameImage(game = game, containerHeight)
}

@Composable
private fun GameInfo(game:Game)
{
    Column {
        Title(game = game)
        GameProperty(label = "Description", value = game.shortDescription)
        GameProperty(label = "Developer", value = game.developer)
        GameProperty(label = "Platform", value = game.platform)
        GameProperty(label = "Genre", value = game.genre)
        GameProperty(label = "Release Date", value = game.releaseDate)
        GameProperty(label = "Game URL", value = game.gameURL)

    }
}
@Composable
private fun Title(game:Game){
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = game.title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
    }
}
@Composable
private fun GameProperty(label:String, value:String){
    Column (modifier = Modifier.padding(16.dp)){
        HorizontalDivider()
        Text(text = label,
            modifier = Modifier.height(24.dp),
            style = MaterialTheme.typography.bodySmall)

        Text(text = value,
            modifier = Modifier.height(24.dp),
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Visible)
    }
}

@Composable
fun headerBar(context: Context){
    Surface(modifier = Modifier.fillMaxWidth() ) {
            Spacer(modifier = Modifier.height(5.dp))
            IconButton(onClick = {
                val intent = Intent(context, AllGamesActivity::class.java)
                ContextCompat.startActivity(context, intent, null)
            }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "ArrowBack",
                    modifier = Modifier.size(32.dp))
            }
        }
        Spacer(modifier = Modifier.height(55.dp))

}