package ch.epfl.people.navigation

import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppNavigation @Inject constructor(
    @ApplicationContext val context: Context,
) {


    fun openChromeTab(activity: AppCompatActivity?, tabIntent: CustomTabsIntent, uri: Uri?) {
        if (activity == null || uri == null) return
        tabIntent.intent.setPackage("com.android.chrome")
        tabIntent.launchUrl(activity, uri)
    }
}