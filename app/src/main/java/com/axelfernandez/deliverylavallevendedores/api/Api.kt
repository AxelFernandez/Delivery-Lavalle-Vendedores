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
    fun sendFirebaseToken(@Body firebaseToken: FirebaseToken, @Header("Authorization") token: String):Call<ConfirmationObject>

    @GET("company_category")
    fun getCompanyCategories(@Header("Authorization") token: String):Call<List<CompanyCategoryResponse>>

    @GET("product_category")
    fun getProductCategory(@Header("Authorization") token: String):Call<List<ProductCategory>>

    @POST("product_category")
    fun postProductCategory(@Body categoryRequest: ProductCategoryRequest, @Header("Authorization") token: String):Call<List<ProductCategory>>

    @GET("product")
    fun getProducts(@Header("Authorization") token: String):Call<List<Product>>

    @POST("product_category_delete")
    fun deleteProductCategory(@Body categoryRequest: ProductCategoryRequest, @Header("Authorization") token: String):Call<String>

    @GET("order_pending")
    fun getOrdersPending(@Header("Authorization") token: String):Call<List<Order>>

    @GET("order_in_progress")
    fun getOrdersInProgress(@Header("Authorization") token: String):Call<List<Order>>

    @GET("order_closed")
    fun getOrdersClosed(@Header("Authorization") token: String):Call<List<Order>>

    @POST("order_by_id")
    fun getOrderById(@Body id:String, @Header("Authorization") token: String):Call<Order>

    @POST("next_state")
    fun setOrderInNextState(@Body id:String, @Header("Authorization") token: String):Call<Order>

    @POST("cancel")
    fun cancelOrder(@Body id:String, @Header("Authorization") token: String):Call<Order>

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
    ): Call<String>

    @Multipart
    @POST("product")
    fun addProduct(@Header("Authorization") token: String,
                        @Part file: MultipartBody.Part,
                        @Part name : MultipartBody.Part,
                        @Part description : MultipartBody.Part,
                        @Part price : MultipartBody.Part,
                        @Part category : MultipartBody.Part,
                        @Part availableNow : MultipartBody.Part
    ): Call<String>
}