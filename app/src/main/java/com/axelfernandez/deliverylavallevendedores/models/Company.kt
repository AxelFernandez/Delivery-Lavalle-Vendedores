package com.axelfernandez.deliverylavallevendedores.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Company(
    var id :String?,
    val name :String,
    val description :String,
    val phone :String,
    var photo: String?,
    val rating :Float = 0f,
    val address :String,
    val availableNow :Boolean,
    val paymentMethods :List<String>?,
    val deliveryMethods :List<String>?,
    var category :String?,
    val limits :String
): Parcelable

data class CompanyCategoryResponse(
    val description :String,
    val photo :String
){
    override fun toString(): String {
        return this.description
    }
}

@Parcelize
data class Invoice(
    val period : String,
    val mount : Float,
    val status : String,
    val dateCreated : String,
    val datePayed : String?
):Parcelable

