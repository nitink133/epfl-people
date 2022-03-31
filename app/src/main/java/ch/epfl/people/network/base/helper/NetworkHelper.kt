package ch.epfl.people.network.base.helper

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author seriously? you know me, right? didn't you?
 * @date i don't know
 */
@Singleton
class NetworkHelper @Inject constructor(@ApplicationContext val context: Context) {
    private var networkType = -1

    val isNetworkAvailable: Boolean
        get() {
            val connectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            return if (connectivityManager != null) {
                val networkInfo = connectivityManager.activeNetworkInfo
                if (networkInfo != null) {
                    networkType = networkInfo.type
                }
                if (networkInfo == null) {
                    return false
                }
                val network = networkInfo.state
                network == NetworkInfo.State.CONNECTED || network == NetworkInfo.State.CONNECTING
            } else {
                false
            }
        }
}