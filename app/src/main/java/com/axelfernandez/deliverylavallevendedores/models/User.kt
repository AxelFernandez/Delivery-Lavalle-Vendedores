package com.axelfernandez.deliverylavallevendedores.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class User(
    var username: String?,
    val email: String,
    val givenName: String,
    val familyName: String,
    val photo: String,
    var phone: String?,
    var token: String
    )

data class UserResponse(
    val is_new :Boolean,
    val completeRegistry : Boolean,
    val username :String,
    val access_token :String,
    val refresh_token :String
)

data class ConfirmationObject(
    val done :Boolean
)
@Parcelize
data class Client(
    val name :String,
    val email: String,
    val phone: String,
    val photo: String
): Parcelable


data class FirebaseToken(
    val token: String,
    val isSeller: Boolean = true
)
