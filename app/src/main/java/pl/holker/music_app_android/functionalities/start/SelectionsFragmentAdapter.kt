package pl.holker.music_app_android.functionalities.start

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class SelectionsFragmentAdapter(fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager) {

    private val mFragmentsList = arrayListOf<Fragment>()
    private val mFragmentsTitles = arrayListOf<String>()

    override fun getItem(position: Int): Fragment = mFragmentsList.get(position)

    override fun getCount(): Int = mFragmentsList.size

    override fun getPageTitle(position: Int): CharSequence? = null //mFragmentsTitles.get(position)

    fun addFragment(title: String, fragment: Fragment) {
        mFragmentsList.add(fragment)
        mFragmentsTitles.add(title)
    }
}