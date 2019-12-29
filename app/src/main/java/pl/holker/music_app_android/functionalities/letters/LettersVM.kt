package pl.holker.music_app_android.functionalities.letters

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.Completable
import io.reactivex.Flowable
import pl.holker.music_app_android.data.persistance.Letter
import pl.holker.music_app_android.data.persistance.LetterDatabase
import pl.holker.music_app_android.functionalities.letters.model.LetterEvent
import javax.inject.Inject

class LettersVM @Inject constructor(application: Application) : AndroidViewModel(application) {
    var datasource = LetterDatabase.getInstance(application.applicationContext).letterDao()
    var event = MutableLiveData<LetterEvent>()

    fun insertLetter(title: String, content: String): Completable {
        val letter = Letter(0, title, content)
        return datasource.addLetter(letter)
    }

    fun getAmount(): Flowable<List<Letter>> {
        val list = datasource.getAllLetters()
        return list.map { listLetter: List<Letter> ->
            listLetter
        }
    }

    fun deleteById(id: Int): Completable {
        return datasource.deleteLetterById(id)
    }

    fun updateLetter(letter: Letter): Completable {
        return datasource.updateLetter(letters = *arrayOf(letter))
    }

}