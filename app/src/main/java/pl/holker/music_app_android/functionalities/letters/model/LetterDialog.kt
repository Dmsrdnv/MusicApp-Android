package pl.holker.music_app_android.functionalities.letters.model

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_add_letter.*
import pl.holker.music_app_android.R
import pl.holker.music_app_android.data.persistance.Letter
import pl.holker.music_app_android.functionalities.letters.LettersVM

class LetterDialog(var viewModel: LettersVM, val mode: LetterType, val letter: Letter? = null) :
    DialogFragment() {

    private val TAG = LetterDialog::class.java.name

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.dialog_add_letter, null))
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onStart() {
        super.onStart()

        initView()

        dialog.dialog_add_letter_btn_discard.setOnClickListener {
            dialog.cancel()
        }

        dialog.dialog_add_letter_btn_save.setOnClickListener {
            when (mode) {
                LetterType.ADD -> {
                    viewModel.event.value = LetterEvent.InsertLetter(
                        dialog.dialog_add_letter_et_title.text.toString(),
                        dialog.dialog_add_letter_et_content.text.toString()
                    )
                    dialog.cancel()
                }
                LetterType.EDIT -> {
                    if (letter != null) {
                        letter.title = dialog.dialog_add_letter_et_title.text.toString()
                        letter.content = dialog.dialog_add_letter_et_content.text.toString()
                        viewModel.event.value = LetterEvent.EditLetter(letter)
                        dialog.cancel()
                    } else {
                        Log.e(TAG, "Cannot edit null letter. Paste current letter object to dialog")
                    }
                }
            }

        }
    }

    private fun initView() {
        if (mode == LetterType.EDIT && letter != null) {
            dialog.dialog_add_letter_et_title.setText(letter.title)
            dialog.dialog_add_letter_et_content.setText(letter.content)
        }
    }
}