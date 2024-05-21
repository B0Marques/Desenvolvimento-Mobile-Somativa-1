package com.example.somativaddm.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.somativaddm.R
import com.example.somativaddm.controller.Activities.AllGamesActivity
import com.example.somativaddm.controller.Activities.LoginActivity
import com.example.somativaddm.controller.Game.GameDatabase
import com.example.somativaddm.controller.Game.GameRepository
import com.example.somativaddm.controller.Game.GameViewModel
import com.example.somativaddm.controller.User.Model.UserRepository
import com.example.somativaddm.controller.viewmodel.MainViewModel
import com.example.somativaddm.databinding.ActivityMainBinding
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this, R.layout.activity_main
        )

        binding.mainRecyclerView.layoutManager =
            GridLayoutManager(this, 2)


        //val intent = Intent(this, LoginActivity::class.java)
        val intent = Intent(this, LoginActivity::class.java)
        /*
        val db = AppModule().providesGameDatabase(applicationContext)
        db.clearAllTables()
            val isDatabaseEmpty = isDatabaseEmpty(db)
                if(isDatabaseEmpty)
                    Log.d("DATABASE", "IS EMPTY")
                else
                    Log.d("DATABASE", "IS NOT EMPTY")

         */

        startActivity(intent)

        }
       fun isDatabaseEmpty(db:GameDatabase):Boolean{
           val gameCount = AppModule().provideGameDao(db).getAll()
           return gameCount.isEmpty()
       }

    }


