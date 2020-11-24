package com.axelfernandez.deliverylavallevendedores.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.axelfernandez.deliverylavallevendedores.api.Api
import com.axelfernandez.deliverylavallevendedores.models.CompanyCategoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CompanyCategoryRepository (
    private val api : Api
){
    val data = MutableLiveData<List<CompanyCategoryResponse>>()

    fun getCompanyCategory(token: String): MutableLiveData<List<CompanyCategoryResponse>> {
        api.getCompanyCategories("Bearer %s".format(token)).enqueue(object : Callback<List<CompanyCategoryResponse>> {
            override fun onFailure(call: Call<List<CompanyCategoryResponse>>, t: Throwable) {
                data.value = null

            }
            override fun onResponse(call: Call<List<CompanyCategoryResponse>>, response: Response<List<CompanyCategoryResponse>>) {
                data.value = response.body()

            }
        })
        return data
    }

    fun returnData(): LiveData<List<CompanyCategoryResponse>> {
        return data
    }

}