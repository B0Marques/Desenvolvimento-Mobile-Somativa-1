package com.example.somativaddm.controller

import android.content.Context
import androidx.room.Room
import com.example.somativaddm.controller.Game.GameDAO
import com.example.somativaddm.controller.Game.GameDatabase
import com.example.somativaddm.controller.Game.GameRepository
import com.example.somativaddm.controller.User.Model.UserDAO
import com.example.somativaddm.controller.User.Model.UserDatabase
import com.example.somativaddm.controller.User.Model.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext app: Context) =
        Room.databaseBuilder(app, UserDatabase::class.java,"user.sqlite").allowMainThreadQueries().build()


    @Singleton
    @Provides
    fun provideDao(db:UserDatabase) = db.userDao()

    @Singleton
    @Provides
    fun provideRepository(dao:UserDAO) = UserRepository(dao)

    @Singleton
    @Provides
    fun providesGameDatabase(@ApplicationContext app:Context)=
        Room.databaseBuilder(app,GameDatabase::class.java,
            "game.sqlite").allowMainThreadQueries().build()

    @Singleton
    @Provides
    fun provideGameDao(db:GameDatabase) = db.gameDao()
    @Singleton
    @Provides
    fun providePlatformDao(db:GameDatabase) = db.platformDao()
    @Singleton
    @Provides
    fun provideGamePlatformJoinDao(db:GameDatabase) = db.gamePlatformJoinDao()
    @Singleton
    @Provides
    fun provideGameCategoryJoinDao(db:GameDatabase) = db.gameCategoryJoinDao()

    @Singleton
    @Provides
    fun provideCategoryDao(db:GameDatabase) = db.categoryDao()

    @Singleton
    @Provides
    fun provideGameRepository(db: GameDatabase) = GameRepository(
        gameDao = db.gameDao(),
        PlatformDao = db.platformDao(),
        categoryDAO = db.categoryDao(),
        gameCategoryJoinDAO = db.gameCategoryJoinDao(),
        gamePlatformJoinDAO = db.gamePlatformJoinDao()
    )
}