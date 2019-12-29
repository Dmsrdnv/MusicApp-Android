package pl.holker.music_app_android.functionalities.letters.model

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.card_letter.view.*
import pl.holker.music_app_android.R
import pl.holker.music_app_android.data.persistance.Letter
import pl.holker.music_app_android.functionalities.letters.LettersVM

class LetterViewHolder(
    val inflater: LayoutInflater,
    val parent: ViewGroup,
    val viewModel: LettersVM
) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.card_letter, parent, false)) {

    private val TAG = LetterViewHolder::class.java.name


    private val _disposable = CompositeDisposable()

    fun bind(letter: Letter) {
        itemView.card_letter_tv_title.text = letter.title
        itemView.card_letter_tv_content.text = letter.content
        itemView.card_letter_ll_main.setOnLongClickListener {
            val builder = AlertDialog.Builder(itemView.context)
            builder.setItems(
                R.array.letter_options
            ) { _, which ->
                when (which) {
                    0 -> {
                        Log.i(TAG, "Edit was pressed")
                        viewModel.event.value = LetterEvent.ShowEditDialog(letter)
                    }
                    1 -> {
                        Log.i(TAG, "Delete one was pressed")
                        deleteLetter(letter.id)
                    }
                    else -> Log.e(TAG, "Empty was picked")
                }
            }
            builder.show()
            return@setOnLongClickListener true
        }
    }

    private fun deleteLetter(id: Int) {
        _disposable.add(
            viewModel.deleteById(id).subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()
            ).subscribe({
                //TODO: Done
            }, { error ->
                Log.e(TAG, "Error while deleting letter : ${error.message}")
            })
        )
    }
}