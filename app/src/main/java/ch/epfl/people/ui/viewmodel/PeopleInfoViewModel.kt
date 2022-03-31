package ch.epfl.people.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.epfl.people.network.base.model.Response
import ch.epfl.people.network.people.model.People
import ch.epfl.people.repository.EpflRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleInfoViewModel @Inject constructor(
    private val epflRepository: EpflRepository
) : ViewModel() {
    private val _responseStateForPeopleInfo: MutableStateFlow<Response<People>> =
        MutableStateFlow(Response.Initial)
    val responseStateForPeopleInfo: StateFlow<Response<People>> = _responseStateForPeopleInfo

    fun getPeopleInfo(id: String) {
        viewModelScope.launch {
            _responseStateForPeopleInfo.value = Response.Loading
            val response = epflRepository.getPeopleInfo(id)
            _responseStateForPeopleInfo.value = response
        }
    }

}