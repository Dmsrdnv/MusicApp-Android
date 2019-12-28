package pl.holker.music_app_android.functionalities.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import pl.holker.music_app_android.R
import pl.holker.music_app_android.databinding.ActivityMainBinding
import pl.holker.music_app_android.di.Injectable
import pl.holker.music_app_android.di.ViewModelInjectionFactory
import pl.holker.music_app_android.functionalities.start.StartActivity
import javax.inject.Inject

class MainActivity : AppCompatActivity(), Injectable {

    private val TAG = MainActivity::class.java.name

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityVM

    @Inject
    lateinit var viewModelInjectionFactory: ViewModelInjectionFactory<MainActivityVM>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()

        if (isLogged()) {
            toStartActivity(true)
        } else {
            createNewUserLocally()
            toStartActivity(false)
        }
    }

    private fun createNewUserLocally() {
        val sharedPreferences = this.getSharedPreferences(getString(R.string.shared_pref), Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putInt(getString(R.string.user_id), Math.random().toInt())
            apply()
        }
    }

    private fun toStartActivity(isLogged: Boolean) {
        val i = Intent(applicationContext, StartActivity::class.java)
        i.putExtra(getString(R.string.is_logged), isLogged)
        startActivity(i)
        finish()
    }

    private fun isLogged(): Boolean {
        val sharedPreferences = this.getSharedPreferences(getString(R.string.shared_pref), Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt(getString(R.string.user_id), 0)
        Log.i(TAG, "Getting user id: $userId")
        return userId != 0
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, viewModelInjectionFactory).get(MainActivityVM::class.java)
        binding.viewModel = viewModel
    }
}
