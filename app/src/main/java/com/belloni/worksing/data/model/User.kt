package com.belloni.worksing.data.model

import com.google.gson.annotations.SerializedName

// Wrapper for the whole API response
data class UserApiResponse(
    @SerializedName("data") val data: List<User>
)

// Represents a single user object
data class User(
    @SerializedName("ID_users") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("email") val email: String,
    @SerializedName("dni") val dni: Long,
    @SerializedName("phone") val phone: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("state") val state: Boolean,
    @SerializedName("typeUser") val typeUser: TypeUser,
    @SerializedName("specialties") val specialties: List<Specialty>
)

// Represents the user type object
data class TypeUser(
    @SerializedName("ID_type_user") val id: Int,
    @SerializedName("name") val name: String
)

// Represents a single specialty object
data class Specialty(
    @SerializedName("ID_specialty") val id: Int,
    @SerializedName("name") val name: String
)
