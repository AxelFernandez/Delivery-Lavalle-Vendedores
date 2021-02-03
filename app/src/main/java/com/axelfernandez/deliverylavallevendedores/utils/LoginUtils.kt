package com.axelfernandez.deliverylavallevendedores.utils

import android.content.Context
import android.content.SharedPreferences
import com.axelfernandez.deliverylavallevendedores.R
import com.axelfernandez.deliverylavallevendedores.models.Address
import com.axelfernandez.deliverylavallevendedores.models.Company
import com.axelfernandez.deliverylavallevendedores.models.User

class LoginUtils {
    companion object{

        fun getUserFromSharedPreferences(context: Context):User{
            val sharedPreferences : SharedPreferences = context.getSharedPreferences("userSession", Context.MODE_PRIVATE)
            return User(
                email = sharedPreferences.getString(context.getString(R.string.email),null).toString(),
                givenName = sharedPreferences.getString(context.getString(R.string.given_name),null).toString(),
                familyName = sharedPreferences.getString(context.getString(R.string.family_name),null).toString(),
                photo = sharedPreferences.getString(context.getString(R.string.picture),null).toString(),
                phone = sharedPreferences.getString(context.getString(R.string.phone),null).toString(),
                username = sharedPreferences.getString(context.getString(R.string.username),null).toString(),
                token = sharedPreferences.getString(context.getString(R.string.token),null).toString()

            )
        }
        fun putUserToSharedPreferences(context: Context, new_user: User){
            val editor :SharedPreferences.Editor = context.getSharedPreferences("userSession", Context.MODE_PRIVATE).edit()
            editor.putString("email",new_user.email)
            editor.putString("given_name",new_user.givenName)
            editor.putString("family_name",new_user.familyName)
            editor.putString("picture",new_user.photo)
            editor.putString("phone",new_user.phone)
            editor.putString("username",new_user.username)
            editor.putString("token",new_user.token)
            editor.apply()
        }

        fun saveInMapLimits(limits :String, context: Context){
            val sharedPreferences : SharedPreferences.Editor = context.getSharedPreferences("userSession", Context.MODE_PRIVATE).edit()
            sharedPreferences.putString(context.getString(R.string.company_limits),limits).apply()
        }
        fun getSaveInMapsLimits(context: Context):String?{
            val sharedPreferences : SharedPreferences = context.getSharedPreferences("userSession", Context.MODE_PRIVATE)
            return sharedPreferences.getString(context.getString(R.string.company_limits),null)
        }


        fun saveDefaultCompany(context: Context,company: Company){
            var editor :SharedPreferences.Editor = context.getSharedPreferences("userSession", Context.MODE_PRIVATE).edit()
            editor.putString("id",company.id)
            editor.putString("name",company.name)
            editor.putString("description",company.description)
            editor.putString("phone",company.phone)
            editor.putString("photo",company.photo)
            editor.putString("address",company.address)
            editor.putBoolean("availableNow",company.availableNow)
            editor.putFloat("rating",company.rating)
            editor.putStringSet("paymentMethods",company.paymentMethods?.toSet())
            editor.putStringSet("deliveryMethods",company.deliveryMethods?.toSet())
            editor.putString("category",company.category)
            editor.putString("limits",company.limits)
            editor.apply()
        }
        fun getDefaultCompany(context: Context):Company{
            val sharedPreferences : SharedPreferences = context.getSharedPreferences("userSession", Context.MODE_PRIVATE)
            return Company(
                id = sharedPreferences.getString("id","null").toString(),
                name = sharedPreferences.getString("name","null").toString(),
                description = sharedPreferences.getString("description","null").toString(),
                phone = sharedPreferences.getString("phone","null").toString(),
                photo = sharedPreferences.getString("photo","null").toString(),
                address = sharedPreferences.getString("address","null").toString(),
                category = sharedPreferences.getString("category","null").toString(),
                limits = sharedPreferences.getString("limits","null").toString(),
                rating = sharedPreferences.getFloat("rating",0f),
                availableNow = sharedPreferences.getBoolean("availableNow",false),
                paymentMethods = sharedPreferences.getStringSet("paymentMethods", ArrayList<String>().toSet())?.toList() as List<String>,
                deliveryMethods = sharedPreferences.getStringSet("deliveryMethods",ArrayList<String>().toSet())?.toList() as List<String>
            )
        }

        fun removeUserData(context: Context){
            var editor :SharedPreferences.Editor = context.getSharedPreferences("userSession", Context.MODE_PRIVATE).edit()
            editor.clear().apply()

        }
        fun saveSessionToken(token:String, context: Context){
            val editor :SharedPreferences.Editor = context.getSharedPreferences("userSession", Context.MODE_PRIVATE).edit()
            editor.putString("token", token).apply()
        }
        fun setIsLoginReady(context: Context, status: Boolean){
            var editor :SharedPreferences.Editor = context.getSharedPreferences("userSession", Context.MODE_PRIVATE).edit()
            editor.putBoolean(context.getString(R.string.is_login_ready),status)
            editor.apply()
        }

        fun getIsLoginReady(context: Context):Boolean{
            val sharedPreferences : SharedPreferences = context.getSharedPreferences("userSession", Context.MODE_PRIVATE)
            return sharedPreferences.getBoolean(context.getString(R.string.is_login_ready),false)

        }
    }
}

