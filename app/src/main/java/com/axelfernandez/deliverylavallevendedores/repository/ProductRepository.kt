package com.axelfernandez.deliverylavallevendedores.repository

import android.content.ClipDescription
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.axelfernandez.deliverylavallevendedores.R
import com.axelfernandez.deliverylavallevendedores.api.Api
import com.axelfernandez.deliverylavallevendedores.models.Order
import com.axelfernandez.deliverylavallevendedores.models.Product
import com.axelfernandez.deliverylavallevendedores.models.ProductCategory
import com.axelfernandez.deliverylavallevendedores.models.ProductCategoryRequest
import com.axelfernandez.deliverylavallevendedores.utils.ViewUtil
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository(private val api : Api){

    val dataCategory = MutableLiveData<List<ProductCategory>>()
    val dataProduct = MutableLiveData<List<Product>>()
    val dataDeleted = MutableLiveData<String>()

    fun solicitProductCategory(token: String): MutableLiveData<List<ProductCategory>> {
        api.getProductCategory("Bearer %s".format(token)).enqueue(object :
            Callback<List<ProductCategory>> {
            override fun onFailure(call: Call<List<ProductCategory>>, t: Throwable) {
                dataCategory.value = null
            }
            override fun onResponse(call: Call<List<ProductCategory>>, response: Response<List<ProductCategory>>) {
                dataCategory.value = response.body()
            }
        })
        return dataCategory
    }

    fun postNewProductCategory(token: String, categoryRequest: ProductCategoryRequest): MutableLiveData<List<ProductCategory>>{
        api.postProductCategory(categoryRequest, "Bearer %s".format(token)).enqueue(object :
            Callback<List<ProductCategory>> {
            override fun onFailure(call: Call<List<ProductCategory>>, t: Throwable) {
                dataCategory.value = null
            }
            override fun onResponse(call: Call<List<ProductCategory>>, response: Response<List<ProductCategory>>) {
                dataCategory.value = response.body()
            }
        })
        return dataCategory
    }
    fun returnProductCategory(): LiveData<List<ProductCategory>> {
        return dataCategory
    }

    fun deleteNewProductCategory(token: String, categoryRequest: ProductCategoryRequest): MutableLiveData<String>{
        api.deleteProductCategory(categoryRequest, "Bearer %s".format(token)).enqueue(object :
            Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                dataDeleted.value = null
            }
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.code() == 200){
                    dataDeleted.value = "Eliminado Correctamente"
                }else if (response.code() == 400){
                    dataDeleted.value = "Hay productos en esta categoria, debes moverlos a otra antes"
                }

            }
        })
        return dataDeleted
    }

    fun returnProductDeleted(): LiveData<String> {
        return dataDeleted
    }
    fun getProducts(token: String): MutableLiveData<List<Product>>{
        api.getProducts("Bearer %s".format(token)).enqueue(object :
            Callback<List<Product>> {
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                dataProduct.value = null
            }
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                dataProduct.value = response.body()
            }
        })
        return dataProduct
    }
    fun returnProducts(): LiveData<List<Product>> {
        return dataProduct
    }


    val data = MutableLiveData<String>()

    fun addProduct(token: String,
                        file : MultipartBody.Part,
                        name : MultipartBody.Part,
                        description : MultipartBody.Part,
                        price : MultipartBody.Part,
                        category : MultipartBody.Part,
                        availableNow : MultipartBody.Part

    ): MutableLiveData<String> {
        api.addProduct("Bearer %s".format(token),file,name,description,price, category, availableNow).enqueue(object :Callback<String>{

            override fun onFailure(call: Call<String>, t: Throwable) {
                data.value= null
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                data.value = response.body()
            }

        })
        return data
    }

    fun returnConfirmationProductAdded(): LiveData<String> {
        return data
    }


}