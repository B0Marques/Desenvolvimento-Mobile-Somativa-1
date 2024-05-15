package com.example.somativaddm.controller

import android.content.Context
import androidx.room.Room
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
}