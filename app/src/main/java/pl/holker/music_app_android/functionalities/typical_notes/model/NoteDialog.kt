package pl.holker.music_app_android.functionalities.typical_notes.model

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_add_note.*
import pl.holker.music_app_android.R
import pl.holker.music_app_android.data.persistance.Note
import pl.holker.music_app_android.functionalities.typical_notes.TypicalNotesVM

class NoteDialog(var viewModel: TypicalNotesVM, val mode: NoteType, val note: Note? = null) :
    DialogFragment() {

    private val TAG = NoteDialog::class.java.name

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.dialog_add_note, null))
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onStart() {
        super.onStart()

        initView()

        dialog.dialog_add_note_btn_discard.setOnClickListener {
            dialog.cancel()
        }

        dialog.dialog_add_note_btn_save.setOnClickListener {
            when (mode) {
                NoteType.ADD -> {
                    viewModel.event.value = TypicalNoteEvent.InsertNote(
                        dialog.dialog_add_note_et_title.text.toString(),
                        dialog.dialog_add_note_et_content.text.toString()
                    )
                    dialog.cancel()
                }
                NoteType.EDIT -> {
                    if (note != null) {
                        note.title = dialog.dialog_add_note_et_title.text.toString()
                        note.content = dialog.dialog_add_note_et_content.text.toString()
                        viewModel.event.value = TypicalNoteEvent.EditNote(note)
                        dialog.cancel()
                    } else {
                        Log.e(TAG, "Cannot edit null note. Paste current note object to dialog")
                    }
                }
            }

        }
    }

    private fun initView() {
        if (mode == NoteType.EDIT && note != null) {
            dialog.dialog_add_note_et_title.setText(note.title)
            dialog.dialog_add_note_et_content.setText(note.content)
        }
    }
}