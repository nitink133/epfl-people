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

    object SelectorPath{
        const val NAME = "#main > div:nth-child(1) > div.d-flex.flex-wrap.justify-content-between.align-items-baseline > h1"
        const val IMAGE_URL = "#main > div:nth-child(1) > div.row.mt-5 > div > div > div:nth-child(1) > div:nth-child(1) > div > img"
        const val POSITION = "#main > div:nth-child(1) > div.row.mt-5 > div > div > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > h4"
        const val EMAIL = "#main > div:nth-child(1) > div.row.mt-5 > div > div > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > p:nth-child(2) > a.btn.btn-sm.btn-primary"
        const val CONTACT_NUMBER = "#main > div:nth-child(1) > div.row.mt-5 > div > div > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > p:nth-child(2) > span > a"
        const val WEBSITE = "#main > div:nth-child(1) > div.row.mt-5 > div > div > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > p:nth-child(2) > a.btn.btn-sm.btn-secondary"
        const val CITIZENSHIP = "#main > div:nth-child(1) > div.row.mt-5 > div > div > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > p:nth-child(3)"
        const val LINKEDIN_URL = "#main > div:nth-child(1) > div.row.mt-5 > div > div > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > div > div:nth-child(1) > a"
        const val TWITTER_URL = "#main > div:nth-child(1) > div.row.mt-5 > div > div > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > div > div:nth-child(2) > a"
        const val FIELD_OF_EXPERTISE = "#main > div:nth-child(2) > div > div > div:nth-child(2) > div"
        const val BIOGRAPHY = "#biography-text > div:nth-child(1)"
        const val MISSION = "#biography-text > div:nth-child(2)"

    }
}