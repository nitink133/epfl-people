package ch.epfl.people.network.base.model

import androidx.annotation.Keep
import ch.epfl.people.network.base.model.Response.*
import ch.epfl.people.network.base.model.Response.Error

/**
 * Represents a network bound resource. Each subclass represents the resource's state:
 * - [Loading]: the resource is being retrieved from network.
 * - [Success]: the resource has been retrieved (available in [Success.data] field)
 * - [Error]: the resource retrieving has failed
 */
@Keep
sealed class Response<out T> {
    data class Success<out T>(val data: T) : Response<T>()
    data class Error(val error: ch.epfl.people.network.base.model.Error) : Response<Nothing>()
    object Loading : Response<Nothing>()
    object IsEmpty : Response<Nothing>()
    object Initial : Response<Nothing>()
}
