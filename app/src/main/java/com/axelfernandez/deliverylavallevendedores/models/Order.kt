package com.axelfernandez.deliverylavallevendedores.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
data class Order(
    val id: String,
    val state: String,
    val address : Address,
    val dateCreated: String,
    val retryInLocal: Boolean,
    val paymentMethod: String,
    val total: String,
    val items : List<ProductDetail>,
    val client: Client
): Parcelable

data class OrderPost(
    val companyId : String,
    val addressId : String,
    val paymentMethod: String,
    val total: String,
    val items : List<ProductDetail>,
    val retryInLocal: Boolean
)
data class MeliLink(
    val isAvailable :Boolean?,
    val orderId: String?,
    val link:String?
)

data class Review(
    val description :String?,
    val order : String?,
    val rating:String,
    val userName:String? = null
)

@Parcelize
data class OrderResponse(
    val dateCreated : String?,
    val orderId : String?,
    val state: String,
    var responseCode: Int
) : Parcelable