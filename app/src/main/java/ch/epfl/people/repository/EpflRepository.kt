package ch.epfl.people.repository

import ch.epfl.people.network.base.model.Response
import ch.epfl.people.network.people.EpflNetwork
import ch.epfl.people.network.people.model.People
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class EpflRepository @Inject constructor(private val epflNetwork: EpflNetwork) {

    suspend fun getPeoples(): Response<List<People>> {
        return epflNetwork.getPeoples()
    }

    suspend fun getPeopleInfo(id:String): Response<People> {
        return epflNetwork.getPeopleInfo(id)
    }
}