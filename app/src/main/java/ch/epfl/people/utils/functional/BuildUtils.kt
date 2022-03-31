package ch.epfl.people.utils.functional

import ch.epfl.people.BuildConfig


/**
 * @author Nitin Khanna
 * @date 16/11/2021
 */
object BuildUtils {
    @JvmStatic
    fun isDebug(): Boolean {
        return BuildConfig.DEBUG
    }
}