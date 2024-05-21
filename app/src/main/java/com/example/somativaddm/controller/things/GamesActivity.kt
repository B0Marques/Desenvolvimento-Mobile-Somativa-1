package com.example.somativaddm.controller.things

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.somativaddm.controller.ui.theme.SomativaDDMTheme

class GamesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SomativaDDMTheme {
                myGames()
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun myGames () {
    Scaffold (
        content = {
            GameContent()
        }

    )
}

@Preview
@Composable
fun gamesPreview () {
    myGames()
}