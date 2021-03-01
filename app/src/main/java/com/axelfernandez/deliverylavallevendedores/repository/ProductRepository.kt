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
import com.axelfernandez.deliverylavallevendedores.utils.TypeOfView
import com.axelfernandez.deliverylavallevendedores.utils.ViewUtil
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository(private val api : Api){

    val dataCategory = MutableLiveData<List<ProductCategory>>()
    val dataProduct = MutableLiveData<List<Product>>()
    val dataDeleted = MutableLiveData<String>()

    fun solicitProductCategory(): MutableLiveData<List<ProductCategory>> {
        api.getProductCategory().enqueue(object :
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

    fun postNewProductCategory(categoryRequest: ProductCategory): MutableLiveData<List<ProductCategory>>{
        api.postProductCategory(categoryRequest).enqueue(object :
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

    fun deleteNewProductCategory(categoryRequest: ProductCategoryRequest): MutableLiveData<String>{
        api.deleteProductCategory(categoryRequest).enqueue(object :
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
    fun getProducts(): MutableLiveData<List<Product>>{
        api.getProducts().enqueue(object :
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

    fun updateProductWithImage(
                        file : MultipartBody.Part,
                        name : MultipartBody.Part,
                        description : MultipartBody.Part,
                        price : MultipartBody.Part,
                        category : MultipartBody.Part,
                        availableNow : MultipartBody.Part,
                        id:  MultipartBody.Part
    ): MutableLiveData<String> {
        api.updateProductWithImage(file,name,description,price, category, availableNow,id).enqueue(object :Callback<String>{

            override fun onFailure(call: Call<String>, t: Throwable) {
                data.value= null
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                data.value = response.body()
            }

        })
        return data
    }

    fun addProductWithImage(
        file : MultipartBody.Part,
        name : MultipartBody.Part,
        description : MultipartBody.Part,
        price : MultipartBody.Part,
        category : MultipartBody.Part,
        availableNow : MultipartBody.Part,
    ): MutableLiveData<String> {
        api.addProduct(file,name,description,price, category, availableNow).enqueue(object :Callback<String>{

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

    fun updateProduct(product: Product): MutableLiveData<String> {
        api.updateProduct(product).enqueue(object : Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                data.value = response.body()
            }

        })
        return data
    }

    fun deleteProduct(product: Product): MutableLiveData<String> {
        api.deleteProduct(product).enqueue(object : Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                data.value = response.body()
            }

        })
        return data
    }

}