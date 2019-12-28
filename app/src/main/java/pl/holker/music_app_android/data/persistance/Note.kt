package pl.holker.music_app_android.data.persistance

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "note_id") val id: Int = 0,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "content") var content: String
)
