package pl.holker.music_app_android.functionalities.typical_notes.model

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.card_note.view.*
import pl.holker.music_app_android.R
import pl.holker.music_app_android.data.persistance.Note
import pl.holker.music_app_android.functionalities.typical_notes.TypicalNotesVM

class TypicalNoteViewHolder(
    val inflater: LayoutInflater,
    val parent: ViewGroup,
    val viewModel: TypicalNotesVM
) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.card_note, parent, false)) {

    private val TAG = TypicalNoteViewHolder::class.java.name


    private val _disposable = CompositeDisposable()

    fun bind(note: Note) {
        itemView.card_note_tv_title.text = note.title
        itemView.card_note_tv_content.text = note.content
        itemView.card_note_ll_main.setOnLongClickListener {
            val builder = AlertDialog.Builder(itemView.context)
            builder.setItems(
                R.array.note_options
            ) { _, which ->
                when (which) {
                    0 -> {
                        Log.i(TAG, "Edit was pressed")
                        viewModel.event.value = TypicalNoteEvent.ShowEditDialog(note)
                    }
                    1 -> {
                        Log.i(TAG, "Delete one was pressed")
                        deleteNote(note.id)
                    }
                    else -> Log.e(TAG, "Empty was picked")
                }
            }
            builder.show()
            return@setOnLongClickListener true
        }
    }

    private fun deleteNote(id: Int) {
        _disposable.add(
            viewModel.deleteById(id).subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()
            ).subscribe({
                //TODO: Done
            }, { error ->
                Log.e(TAG, "Error while deleting note : ${error.message}")
            })
        )
    }
}