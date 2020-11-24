package com.axelfernandez.deliverylavallevendedores.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.axelfernandez.deliverylavallevendedores.api.Api
import com.axelfernandez.deliverylavallevendedores.models.Company
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompanyRepository (
    private val api : Api
){

    val data = MutableLiveData<String>()

    fun registerCompany(token: String,
                        file : MultipartBody.Part,
                        name : MultipartBody.Part,
                        description : MultipartBody.Part,
                        phone : MultipartBody.Part,
                        address : MultipartBody.Part,
                        paymentMethod : MultipartBody.Part,
                        deliveryMethod : MultipartBody.Part,
                        category : MultipartBody.Part,
                        limits : MultipartBody.Part,
                        availableNow : MultipartBody.Part

    ): MutableLiveData<String> {
        api.registryCompany("Bearer %s".format(token),file,name,description,phone,address,paymentMethod,deliveryMethod,category,limits,availableNow).enqueue(object :Callback<String>{

            override fun onFailure(call: Call<String>, t: Throwable) {
                data.value= null
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                data.value = response.body()
            }

        })
        return data
    }

    fun returnData(): LiveData<String> {
        return data
    }

}