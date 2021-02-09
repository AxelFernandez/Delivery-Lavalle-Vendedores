package com.axelfernandez.deliverylavallevendedores.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.axelfernandez.deliverylavallevendedores.api.Api
import com.axelfernandez.deliverylavallevendedores.models.Company
import com.axelfernandez.deliverylavallevendedores.models.Invoice
import com.axelfernandez.deliverylavallevendedores.models.ProductCategory
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompanyRepository (
    private val     api : Api
){

    val data = MutableLiveData<String>()
    val dataAccountDebit = MutableLiveData<String>()
    val companyData = MutableLiveData<Company>()
    val invoices = MutableLiveData<List<Invoice>>()
    val pendingInvoices = MutableLiveData<Boolean>()
    val availability = MutableLiveData<Boolean>()

    fun registerCompany(
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
        api.registryCompany(file,name,description,phone,address,paymentMethod,deliveryMethod,category,limits,availableNow).enqueue(object :Callback<String>{

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

    fun getCompanyFullData(): MutableLiveData<Company> {
        api.getCompany().enqueue(object :
            Callback<Company> {
            override fun onFailure(call: Call<Company>, t: Throwable) {
                companyData.value = null
            }
            override fun onResponse(call: Call<Company>, response: Response<Company>) {
                companyData.value = response.body()
            }
        })
        return companyData
    }


    fun returnCompany(): LiveData<Company> {
        return companyData
    }

    fun registerCompanyNoImage(company: Company): MutableLiveData<String> {
        api.registryCompanyNoImage(company).enqueue(object :Callback<String>{

            override fun onFailure(call: Call<String>, t: Throwable) {
                data.value= null
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                data.value = response.body()
            }

        })
        return data
    }

    fun getAccountDebit(): MutableLiveData<String> {
        api.getAccountDebit().enqueue(object :Callback<String>{

            override fun onFailure(call: Call<String>, t: Throwable) {
                dataAccountDebit.value= null
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                dataAccountDebit.value = response.body()
            }

        })
        return dataAccountDebit
    }
    fun returnCompanyAccountDebit(): LiveData<String> {
        return dataAccountDebit
    }


    fun getInvoice(): MutableLiveData<List<Invoice>> {
        api.getInvoices().enqueue(object :Callback<List<Invoice>>{

            override fun onFailure(call: Call<List<Invoice>>, t: Throwable) {
                invoices.value= null
            }

            override fun onResponse(call: Call<List<Invoice>>, response: Response<List<Invoice>>) {
                invoices.value = response.body()
            }

        })
        return invoices
    }
    fun returnInvoices(): LiveData<List<Invoice>> {
        return invoices
    }


    fun hadPendingInvoices(): MutableLiveData<Boolean> {
        api.hadPendingInvoices().enqueue(object :Callback<Boolean>{

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                pendingInvoices.value= null
            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                pendingInvoices.value = response.body()
            }

        })
        return pendingInvoices
    }
    fun returnHadPendingInvoices(): LiveData<Boolean> {
        return pendingInvoices
    }


    fun getCompanyAvailability(): MutableLiveData<Boolean>{
        api.getCompanyAvailability().enqueue(object : Callback<Boolean>{
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                availability.value = null
            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                availability.value = response.body()
            }
        })
        return availability
    }

    fun postCompanyAvailability(available :Boolean): MutableLiveData<Boolean>{
        api.postCompanyAvailability(available).enqueue(object : Callback<Boolean>{
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                availability.value = null
            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                availability.value = response.body()
            }
        })
        return availability
    }

    fun returnAvailability(): LiveData<Boolean> {
        return availability
    }
}