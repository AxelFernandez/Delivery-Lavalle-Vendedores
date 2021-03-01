package com.axelfernandez.deliverylavallevendedores.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    var id :String?,
    val name :String,
    var description :String,
    val photo :String?,
    val price :String,
    val category :String,
    val availableNow :Boolean?
): Parcelable

@Parcelize
class Products: ArrayList<Product>(), Parcelable

@Parcelize
data class ProductCategoryRequest(
    val type :String,
    var description :String? = null,
    var descriptionOld : String? = null,
    var id: String? = null
): Parcelable

@Parcelize
data class ProductCategory(
    val id: String? = null,
    var description: String?,
    val quantity: String? = null
): Parcelable{
    override fun toString(): String {
        return description?:""
    }
}

@Parcelize
data class ProductDetail(
    val id :String,
    val quantity: String,
    val description: String,
    val subtotal: String
) : Parcelable
