package pl.holker.music_app_android.functionalities.letters.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.holker.music_app_android.data.persistance.Letter
import pl.holker.music_app_android.functionalities.letters.LettersVM

class LetterAdapter(var items: List<Letter>, val viewModel: LettersVM) :
    RecyclerView.Adapter<LetterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LetterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return LetterViewHolder(inflater, parent, viewModel)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: LetterViewHolder, position: Int) {
        val letter = items[position]
        holder.bind(letter)
    }
}