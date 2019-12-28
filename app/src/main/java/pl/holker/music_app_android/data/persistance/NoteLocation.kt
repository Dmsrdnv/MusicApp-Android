package pl.holker.music_app_android.data.persistance

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_notes")
data class NoteLocation(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "note_location_id") val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "x") val x: Float,
    @ColumnInfo(name = "y") val y: Float
)