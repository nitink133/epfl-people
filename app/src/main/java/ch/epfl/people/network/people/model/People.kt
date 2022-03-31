package ch.epfl.people.network.people.model

data class People(
    var id: String? = null,
    var name: String? = null,
    var position: String? = null,
    var email: String? = null,
    var contact: String? = null,
    var image: String? = null
)