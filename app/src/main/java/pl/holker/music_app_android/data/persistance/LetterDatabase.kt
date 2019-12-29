package pl.holker.music_app_android.data.persistance

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Letter::class, LetterLocation::class], version = 1, exportSchema = false)
abstract class LetterDatabase : RoomDatabase() {
    abstract fun letterDao(): LetterDao

    companion object {
        @Volatile
        private var INSTANCE: LetterDatabase? = null

        fun getInstance(context: Context): LetterDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, LetterDatabase::class.java, "test1"
        ).build()
    }
}