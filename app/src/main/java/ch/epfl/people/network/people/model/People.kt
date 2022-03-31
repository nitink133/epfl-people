package ch.epfl.people.network.people.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class People(
    @SerializedName("biography")
    var biography: String? = null,
    @SerializedName("citizenship")
    var citizenship: String? = null,
    @SerializedName("contact_number")
    var contactNumber: String? = null,
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("field_of_expertise")
    var fieldOfExpertise: String? = null,
    @SerializedName("image_url")
    var imageUrl: String? = null,
    @SerializedName("linkedin_profile")
    var linkedinProfile: String? = null,
    @SerializedName("mission")
    var mission: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("position")
    var position: String? = null,
    @SerializedName("twitter_profile")
    var twitterProfile: String? = null,
    @SerializedName("website")
    var website: String? = null,
    @SerializedName("office")
    var office: String? = null
)