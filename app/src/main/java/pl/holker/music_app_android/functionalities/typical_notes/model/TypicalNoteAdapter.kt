package pl.holker.music_app_android.functionalities.typical_notes.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.holker.music_app_android.data.persistance.Note
import pl.holker.music_app_android.functionalities.typical_notes.TypicalNotesVM

class TypicalNoteAdapter(var items: List<Note>, val viewModel: TypicalNotesVM) :
    RecyclerView.Adapter<TypicalNoteViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypicalNoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TypicalNoteViewHolder(inflater, parent, viewModel)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TypicalNoteViewHolder, position: Int) {
        val note = items[position]
        holder.bind(note)
    }
}