package ch.epfl.people.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.epfl.people.network.base.model.Response
import ch.epfl.people.network.people.model.People
import ch.epfl.people.repository.EpflRepository
import ch.epfl.people.utils.functional.LogUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleListViewModel @Inject constructor(
    private val epflRepository: EpflRepository
) : ViewModel() {
    private val _responseStateForPeoplesList: MutableStateFlow<Response<List<People>>> =
        MutableStateFlow(Response.Initial)
    val responseStateForPeoplesList: StateFlow<Response<List<People>>> = _responseStateForPeoplesList

     fun getPeoples() {
        viewModelScope.launch {
            _responseStateForPeoplesList.value = Response.Loading
            val response = epflRepository.getPeoples()
            _responseStateForPeoplesList.value = response
        }
    }

    fun getPeopleInfo(id:String) {
        viewModelScope.launch {
            _responseStateForPeoplesList.value = Response.Loading
            val response = epflRepository.getPeopleInfo(id)
            _responseStateForPeoplesList.value = Response.Initial
        }
    }

    init {
        getPeoples()
    }

}