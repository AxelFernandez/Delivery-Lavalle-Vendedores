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
    fun loginWithGoogle(@Body googleUserToken: String):Call<UserResponse>

    @POST("firebase_token")
    fun sendFirebaseToken(@Body firebaseToken: FirebaseToken):Call<ConfirmationObject>

    @PUT("firebase_token")
    fun deleteFirebaseToken(@Body firebaseToken: FirebaseToken):Call<ConfirmationObject>

    @GET("company_category")
    fun getCompanyCategories():Call<List<CompanyCategoryResponse>>

    @GET("product_category")
    fun getProductCategory():Call<List<ProductCategory>>

    @POST("product_category")
    fun postProductCategory(@Body categoryRequest: ProductCategory):Call<List<ProductCategory>>

    @GET("product")
    fun getProducts():Call<List<Product>>

    @POST("product")
    fun updateProduct(@Body product:Product):Call<String>

    @POST("product_delete")
    fun deleteProduct(@Body product:Product):Call<String>

    @POST("product_category_delete")
    fun deleteProductCategory(@Body categoryRequest: ProductCategoryRequest):Call<String>

    @GET("order_pending")
    fun getOrdersPending():Call<List<Order>>

    @GET("order_in_progress")
    fun getOrdersInProgress():Call<List<Order>>

    @GET("order_closed")
    fun getOrdersClosed():Call<List<Order>>

    @POST("order_by_id")
    fun getOrderById(@Body id:String):Call<Order>

    @POST("next_state")
    fun setOrderInNextState(@Body id:String):Call<Order>

    @POST("cancel")
    fun cancelOrder(@Body id:String):Call<Order>

    @GET("company")
    fun getCompany():Call<Company>

    @POST("company")
    fun registryCompanyNoImage(@Body company : Company):Call<String>

    @GET("account_debit")
    fun getAccountDebit():Call<String>

    @GET("reviews")
    fun getReviews(@Query("company") clientId:String):Call<List<Review>>

    @POST("getMeliLink")
    fun getMeLiLink(@Body orderId: String):Call<MeliLink>

    @POST("sendMeliLink")
    fun sendMeLiLink(@Body link: MeliLink):Call<String>

    @GET("invoices")
    fun getInvoices():Call<List<Invoice>>

    @GET("hadPendingInvoices")
    fun hadPendingInvoices():Call<Boolean>

    @GET("company_availability")
    fun getCompanyAvailability():Call<Boolean>

    @POST("company_availability")
    fun postCompanyAvailability(@Body availability: Boolean):Call<Boolean>

    @Multipart
    @POST("company")
    fun registryCompany(
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
    fun addProduct(
                        @Part file: MultipartBody.Part,
                        @Part name : MultipartBody.Part,
                        @Part description : MultipartBody.Part,
                        @Part price : MultipartBody.Part,
                        @Part category : MultipartBody.Part,
                        @Part availableNow : MultipartBody.Part,
    ): Call<String>

        @Multipart
        @POST("product")
        fun updateProductWithImage(
            @Part file: MultipartBody.Part,
            @Part name : MultipartBody.Part,
            @Part description : MultipartBody.Part,
            @Part price : MultipartBody.Part,
            @Part category : MultipartBody.Part,
            @Part availableNow : MultipartBody.Part,
            @Part id : MultipartBody.Part
        ): Call<String>
}