package pl.holker.music_app_android.functionalities.start

import android.graphics.PorterDuff
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_start.*
import pl.holker.music_app_android.R
import pl.holker.music_app_android.databinding.ActivityStartBinding
import pl.holker.music_app_android.di.Injectable
import pl.holker.music_app_android.di.ViewModelInjectionFactory
import pl.holker.music_app_android.functionalities.location_notes.LocationNotesFragment
import pl.holker.music_app_android.functionalities.typical_notes.TypicalNotesFragment
import javax.inject.Inject

class StartActivity : AppCompatActivity(), Injectable, HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidInjector

    private val TAG = StartActivity::class.java.name

    private lateinit var binding: ActivityStartBinding
    private lateinit var viewModel: StartActivityVM
    @Inject
    lateinit var viewModelInjectionFactory: ViewModelInjectionFactory<StartActivityVM>

    private lateinit var mSelectionsPagerAdapter: SelectionsFragmentAdapter
    val tabIcons = arrayListOf<Int>(
            R.drawable.ic_notes, R.drawable.ic_map_48
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initFragments()
    }

    private fun initFragments() {
        mSelectionsPagerAdapter = SelectionsFragmentAdapter(supportFragmentManager)
        setupViewPager()
        start_tl_tabs.setupWithViewPager(start_vp_container)
        start_vp_container.currentItem = 0
        setupFragmentsIcons()
    }


    private fun setupViewPager() {
        val fragmentAdapter = mSelectionsPagerAdapter
        fragmentAdapter.addFragment("Notes", TypicalNotesFragment())
        fragmentAdapter.addFragment("Locations", LocationNotesFragment())
        start_vp_container.adapter = mSelectionsPagerAdapter
    }

    private fun setupFragmentsIcons() {
        start_tl_tabs.getTabAt(0)?.setIcon(tabIcons[0])
        start_tl_tabs.getTabAt(0)?.icon?.setColorFilter(
                resources.getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN
        )
        start_tl_tabs.getTabAt(1)?.setIcon(tabIcons[1])
        start_tl_tabs.getTabAt(1)?.icon?.setColorFilter(
                resources.getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN
        )
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_start)
        viewModel =
                ViewModelProviders.of(this, viewModelInjectionFactory)
                        .get(StartActivityVM::class.java)
        binding.viewModel = viewModel
    }
}
