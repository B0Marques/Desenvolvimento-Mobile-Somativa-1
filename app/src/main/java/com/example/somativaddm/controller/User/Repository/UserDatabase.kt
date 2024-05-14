package com.example.somativaddm.controller.User.Repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.somativaddm.controller.User.UserDTO

@Database(entities = [UserDTO::class], version = 2 )
public abstract class UserDatabase : RoomDatabase(){
    abstract fun UserRepository(): UserRepository

    companion object{
        private var INSTANCE: UserDatabase? = null

        public fun getInstance(context:Context):UserDatabase{
            if(INSTANCE == null){
                synchronized(UserDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "user_database"
                    ).addMigrations(MIGRATION_1_2)
                        .build()
                }
            }
            return INSTANCE !!
        }

        private val MIGRATION_1_2:Migration = object:Migration(1,2){
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE UserDTO ADD COLUMN user_name TEXT")
            }
        }
    }

}