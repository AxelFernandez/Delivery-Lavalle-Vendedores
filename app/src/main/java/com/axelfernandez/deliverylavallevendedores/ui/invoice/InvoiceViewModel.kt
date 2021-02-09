package com.axelfernandez.deliverylavallevendedores.ui.invoice

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.axelfernandez.deliverylavallevendedores.api.Api
import com.axelfernandez.deliverylavallevendedores.api.RetrofitFactory
import com.axelfernandez.deliverylavallevendedores.models.Invoice
import com.axelfernandez.deliverylavallevendedores.repository.CompanyRepository

class InvoiceViewModel : ViewModel() {

    private lateinit var companyRepository :CompanyRepository

    fun getRepository(context: Context) {
        companyRepository  = CompanyRepository(RetrofitFactory.buildService(Api::class.java, context))
    }


    fun getInvoices(){
        companyRepository.getInvoice()
    }
    fun returnInvoices(): LiveData<List<Invoice>> {
        return companyRepository.returnInvoices()
    }
}