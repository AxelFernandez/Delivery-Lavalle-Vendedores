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

    fun getCompanyData(token: String): MutableLiveData<Company> {
        api.getCompany("Bearer %s".format(token)).enqueue(object :
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

    fun registerCompanyNoImage(token: String, company: Company): MutableLiveData<String> {
        api.registryCompanyNoImage("Bearer %s".format(token),company).enqueue(object :Callback<String>{

            override fun onFailure(call: Call<String>, t: Throwable) {
                data.value= null
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                data.value = response.body()
            }

        })
        return data
    }

    fun getAccountDebit(token: String): MutableLiveData<String> {
        api.getAccountDebit("Bearer %s".format(token)).enqueue(object :Callback<String>{

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


    fun getInvoices(token: String): MutableLiveData<List<Invoice>> {
        api.getInvoices("Bearer %s".format(token)).enqueue(object :Callback<List<Invoice>>{

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


    fun hadPendingInvoices(token: String): MutableLiveData<Boolean> {
        api.hadPendingInvoices("Bearer %s".format(token)).enqueue(object :Callback<Boolean>{

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
}