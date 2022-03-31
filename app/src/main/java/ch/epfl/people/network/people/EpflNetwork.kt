package ch.epfl.people.network.people

import android.content.Context
import ch.epfl.people.R
import ch.epfl.people.network.base.ApiConstants
import ch.epfl.people.network.base.helper.NetworkHelper
import ch.epfl.people.network.base.model.Error
import ch.epfl.people.network.base.model.Response
import ch.epfl.people.network.people.model.People
import ch.epfl.people.parser.PeopleDataParser
import ch.epfl.people.utils.functional.LogUtils
import co.allcommerce.myservice.di.IoDispatcher
import co.allcommerce.myservice.di.MainDispatcher
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.lang.Exception
import javax.inject.Inject

@ViewModelScoped
class EpflNetwork @Inject constructor(
    @ApplicationContext val context: Context,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher val mainDispatcher: CoroutineDispatcher,
    private val networkHelper: NetworkHelper
) {
    suspend fun getPeoples(): Response<List<People>> {
        if (!networkHelper.isNetworkAvailable) {
            return withContext(mainDispatcher) {
                delay(100)
                Response.Error(
                    error = Error(
                        code = ApiConstants.ErrorCode.NO_INTERNET_ERROR,
                        message = context.getString(R.string.err_no_internet_network)
                    )
                )
            }
        }

        return withContext(ioDispatcher) {
            try {
                val peoplesList = PeopleDataParser.parseJsoupDocumentToPeoplesList(
                    Jsoup.connect("https://www.epfl.ch/labs/chili/people/").get()
                )

                if (peoplesList.isNullOrEmpty()) Response.IsEmpty
                else Response.Success(peoplesList)
            }catch (e:Exception){
                Response.Error(Error(message = e.message))
            }

        }
    }

    suspend fun getPeopleInfo(id:String): Response<People> {
        if (!networkHelper.isNetworkAvailable) {
            return withContext(mainDispatcher) {
                delay(100)
                Response.Error(
                    error = Error(
                        code = ApiConstants.ErrorCode.NO_INTERNET_ERROR,
                        message = context.getString(R.string.err_no_internet_network)
                    )
                )
            }
        }

        return withContext(ioDispatcher) {
            try {
                val peopleInfo = PeopleDataParser.parseJsoupDocumentToPeoplesInfo(
                    Jsoup.connect("https://people.epfl.ch/${id}").get()
                )

                LogUtils.data("Nitin", peopleInfo)
                if (peopleInfo == null) Response.IsEmpty
                else Response.Success(peopleInfo)
            }catch (e:Exception){
                Response.Error(Error(message = e.message))
            }

        }
    }

}