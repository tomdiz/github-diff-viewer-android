package com.example.diffviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.example.diffviewer.databinding.ActivityMainBinding
import com.example.diffviewer.utils.addFragment
import io.reactivex.plugins.RxJavaPlugins


class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Define the listener for binding
        mBinding =  DataBindingUtil.setContentView(this, R.layout.activity_main)

        RxJavaPlugins.setErrorHandler {
            Log.e("Error", it.localizedMessage)
        }

        val userNameFragment = UserNameFragment()
        addFragment(userNameFragment, R.id.fragment_container)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_exit -> {
                finishAffinity()
                true
            }
             android.R.id.home -> {
                 onBackPressed()
                 true
             }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
