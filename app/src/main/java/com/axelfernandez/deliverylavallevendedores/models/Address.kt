package com.axelfernandez.deliverylavallevendedores.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    var id: String,
    var street: String,
    var number: String,
    var district: String,
    var floor: String?,
    var reference: String?,
    var location: String?
): Parcelable

data class AddressResponse(
    val addressId :String
)

