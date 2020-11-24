package com.axelfernandez.deliverylavallevendedores.api

import com.axelfernandez.deliverylavallevendedores.models.*
import com.axelfernandez.deliverylavallevendedores.models.User
import com.axelfernandez.deliverylavallevendedores.models.UserResponse
import okhttp3.MultipartBody
import retrofit2.Call;
import retrofit2.http.*

interface Api {

    @Headers("Content-Type: application/json")
    @POST("google")
    fun loginWithGoogle(@Body userData: User):Call<UserResponse>

    @POST("firebase_token")
    fun sendFirebaseToken(@Body firebaseToken: String, @Header("Authorization") token: String):Call<ConfirmationObject>

    @GET("company_category")
    fun getCompanyCategories(@Header("Authorization") token: String):Call<List<CompanyCategoryResponse>>


    @Multipart
    @POST("company")
    fun registryCompany(@Header("Authorization") token: String,
                        @Part file: MultipartBody.Part,
                        @Part name : MultipartBody.Part,
                        @Part description : MultipartBody.Part,
                        @Part phone : MultipartBody.Part,
                        @Part address : MultipartBody.Part,
                        @Part paymentMethod : MultipartBody.Part,
                        @Part deliveryMethod : MultipartBody.Part,
                        @Part category : MultipartBody.Part,
                        @Part limits : MultipartBody.Part,
                        @Part availableNow : MultipartBody.Part
    )

            : Call<String>

}