package pl.holker.music_app_android.functionalities.letters

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
import kotlinx.android.synthetic.main.fragment_letters.*
import kotlinx.android.synthetic.main.fragment_letters.floatingActionButton
import pl.holker.music_app_android.R
import pl.holker.music_app_android.databinding.FragmentLettersBinding
import pl.holker.music_app_android.di.Injectable
import pl.holker.music_app_android.di.ViewModelInjectionFactory
import pl.holker.music_app_android.functionalities.letters.model.*
import javax.inject.Inject
import kotlin.math.roundToInt

class LettersFragment @Inject constructor() : Fragment(), Injectable {

    private val TAG = LettersFragment::class.java.name
    private lateinit var _viewModel: LettersVM
    private lateinit var _binding: FragmentLettersBinding
    private val _disposable = CompositeDisposable()
    private lateinit var _adapter: LetterAdapter


    @Inject
    lateinit var viewModelInjectionFactory: ViewModelInjectionFactory<LettersVM>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_letters, container, false)
        _viewModel = ViewModelProviders.of(this, viewModelInjectionFactory)
            .get(LettersVM::class.java)

        _binding.viewModel = _viewModel
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _adapter = LetterAdapter(listOf(), _viewModel)
        card_letter_recycler_view.layoutManager = GridLayoutManager(context, 2)
        card_letter_recycler_view.addItemDecoration(GridSpacingItemDecoration(2, dpToPx(10), true))
        card_letter_recycler_view.adapter = _adapter
    }

    override fun onStart() {
        super.onStart()


        initObservables()

        floatingActionButton.setOnClickListener {
            val dialog = LetterDialog(viewModel = _viewModel, mode = LetterType.ADD)
            dialog.show(childFragmentManager, "Add letter")
        }

        //Pull letter list
        _disposable.add(
            _viewModel.getAmount().subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()
            ).subscribe({ letterList ->
                Log.i(TAG, "Start pulling letter list")
                _adapter.items = letterList
                _adapter.notifyDataSetChanged()
                Log.i(TAG, "Pulling was finished. Items in list : ${letterList.size}")
            }, { error ->
                Log.e(TAG, "Error while pulling letters : ${error.message}")
            })
        )
    }

    private fun initObservables() {
        _viewModel.event.observe(this, Observer { action ->
            when (action) {
                is LetterEvent.InsertLetter -> {
                    _disposable.add(
                        _viewModel.insertLetter(
                            action.title,
                            action.content
                        ).subscribeOn(Schedulers.io()).observeOn(
                            AndroidSchedulers.mainThread()
                        ).subscribe({
                        }, { error ->
                            Log.e(TAG, "Error while adding a new letter : ${error.message}")
                        })
                    )
                }
                is LetterEvent.EditLetter -> {
                    _disposable.add(
                        _viewModel.updateLetter(action.letter)
                            .subscribeOn(Schedulers.io()).observeOn(
                                AndroidSchedulers.mainThread()
                            ).subscribe({

                            }, {
                                Log.i(TAG, "Error while updating letter : ${it.message}")
                            })
                    )
                }
                is LetterEvent.ShowEditDialog -> {
                    val dialog =
                        LetterDialog(viewModel = _viewModel, mode = LetterType.EDIT, letter = action.letter)
                    dialog.show(childFragmentManager, "Add letter")
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