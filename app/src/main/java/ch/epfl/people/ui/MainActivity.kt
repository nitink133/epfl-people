package ch.epfl.people.ui

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import ch.epfl.people.R
import ch.epfl.people.databinding.ActivityMainBinding
import ch.epfl.people.ui.fragment.PeopleListFragment
import ch.epfl.people.utils.functional.ToastUtils
import co.allcommerce.myservice.base.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Nitin Khanna
 * @date 31/03/2022 11:45PM
 * @song https://open.spotify.com/track/3u6zpa0QXDr9A2mnZFBkzE?si=279574c9dc1c4a5d
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    }

    override fun onBackPressed() {
        val fragment = getVisibleFragment()
        val doBack = fragment?.onBackPressed() ?: false
        if (!doBack) return
        if (supportFragmentManager.backStackEntryCount > 0 || fragment !is PeopleListFragment) {
            super.onBackPressed()
        } else if (!doubleBackToExitPressedOnce) {
            doubleBackToExitPressedOnce = true
            ToastUtils.showMessage(this, getString(R.string.msg_please_press_again_to_exit))
            Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
        } else {
            super.onBackPressed()
            return
        }
    }

    private fun getVisibleFragment(): BaseFragment? {
        val navHostFragment: NavHostFragment? =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        if (navHostFragment?.childFragmentManager?.fragments.isNullOrEmpty()) return null
        return navHostFragment?.childFragmentManager?.fragments!![0] as? BaseFragment
    }
}