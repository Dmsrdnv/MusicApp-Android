package pl.holker.music_app_android.data.persistance

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "letters")
data class Letter(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "letter_id") val id: Int = 0,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "content") var content: String
)
