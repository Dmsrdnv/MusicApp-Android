package pl.holker.music_app_android.data.persistance

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface LetterDao {
    @Query("SELECT * FROM letters")
    fun getAllLetters(): Flowable<List<Letter>>

    @Query("SELECT * FROM location_letters")
    fun getLocationLetters(): Flowable<List<LetterLocation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addLetter(letter: Letter): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addLocationLetter(letterLocation: LetterLocation): Completable

    @Query("DELETE FROM letters")
    fun deleteAllLetters(): Completable

    @Query("DELETE FROM letters WHERE letter_id = :letterId")
    fun deleteLetterById(letterId: Int): Completable

    @Query("DELETE FROM location_letters")
    fun deleteAllLocationLetters()

    @Query("DELETE FROM location_letters WHERE letter_location_id = :letterId")
    fun deleteLocationLetterById(letterId: Int)

    @Update
    fun updateLetter(vararg letters: Letter): Completable
}