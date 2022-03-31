package co.allcommerce.myservice.base.fragment

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    abstract fun showProgress(msg: String?)
    abstract fun hideProgress()
    open fun onBackPressed(): Boolean {
        return true
    }
}