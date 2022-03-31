package ch.epfl.people.network.base

/**
 * @author Nitin Khanna
 * @date 18/11/2021
 */
object ApiConstants {
    const val SOURCE = "android"

    object ErrorCode {
        const val DEFAULT_ERROR = "default_error"
        const val UNAUTHORIZED = "unauthorized"
        const val NO_INTERNET_ERROR = "no_network"

        fun isGenericError(errorCode: String?): Boolean {
            if (errorCode.isNullOrEmpty()) return false
            return errorCode == UNAUTHORIZED || errorCode == NO_INTERNET_ERROR || errorCode == DEFAULT_ERROR
        }
    }

    object ApiCode {
        const val GET_PEOPLES_LIST = 0
        const val GET_PEOPLE_INFO = 1
    }
}