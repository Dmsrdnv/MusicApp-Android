package pl.holker.music_app_android.functionalities.typical_notes

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_typical_notes.*
import pl.holker.music_app_android.R
import pl.holker.music_app_android.databinding.FragmentTypicalNotesBinding
import pl.holker.music_app_android.di.Injectable
import pl.holker.music_app_android.di.ViewModelInjectionFactory
import pl.holker.music_app_android.functionalities.typical_notes.model.*
import javax.inject.Inject
import kotlin.math.roundToInt

class TypicalNotesFragment @Inject constructor() : Fragment(), Injectable {

    private val TAG = TypicalNotesFragment::class.java.name
    private lateinit var _viewModel: TypicalNotesVM
    private lateinit var _binding: FragmentTypicalNotesBinding
    private val _disposable = CompositeDisposable()
    private lateinit var _adapter: TypicalNoteAdapter


    @Inject
    lateinit var viewModelInjectionFactory: ViewModelInjectionFactory<TypicalNotesVM>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_typical_notes, container, false)
        _viewModel = ViewModelProviders.of(this, viewModelInjectionFactory)
            .get(TypicalNotesVM::class.java)

        _binding.viewModel = _viewModel
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _adapter = TypicalNoteAdapter(listOf(), _viewModel)
        card_note_recycler_view.layoutManager = GridLayoutManager(context, 2)
        card_note_recycler_view.addItemDecoration(GridSpacingItemDecoration(2, dpToPx(10), true))
        card_note_recycler_view.adapter = _adapter
    }

    override fun onStart() {
        super.onStart()


        initObservables()

        floatingActionButton.setOnClickListener {
            val dialog = NoteDialog(viewModel = _viewModel, mode = NoteType.ADD)
            dialog.show(childFragmentManager, "Add note")
        }

        //Pull note list
        _disposable.add(
            _viewModel.getAmount().subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()
            ).subscribe({ noteList ->
                Log.i(TAG, "Start pulling note list")
                _adapter.items = noteList
                _adapter.notifyDataSetChanged()
                Log.i(TAG, "Pulling was finished. Items in list : ${noteList.size}")
            }, { error ->
                Log.e(TAG, "Error while pulling notes : ${error.message}")
            })
        )
    }

    private fun initObservables() {
        _viewModel.event.observe(this, Observer { action ->
            when (action) {
                is TypicalNoteEvent.InsertNote -> {
                    _disposable.add(
                        _viewModel.insertNote(
                            action.title,
                            action.content
                        ).subscribeOn(Schedulers.io()).observeOn(
                            AndroidSchedulers.mainThread()
                        ).subscribe({
                        }, { error ->
                            Log.e(TAG, "Error while adding a new note : ${error.message}")
                        })
                    )
                }
                is TypicalNoteEvent.EditNote -> {
                    _disposable.add(
                        _viewModel.updateNote(action.note)
                            .subscribeOn(Schedulers.io()).observeOn(
                                AndroidSchedulers.mainThread()
                            ).subscribe({

                            }, {
                                Log.i(TAG, "Error while updating note : ${it.message}")
                            })
                    )
                }
                is TypicalNoteEvent.ShowEditDialog -> {
                    val dialog =
                        NoteDialog(viewModel = _viewModel, mode = NoteType.EDIT, note = action.note)
                    dialog.show(childFragmentManager, "Add note")
                }
            }
        })
    }

    private fun dpToPx(dp: Int): Int {
        val resource = resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            resource.displayMetrics
        ).roundToInt()
    }
}