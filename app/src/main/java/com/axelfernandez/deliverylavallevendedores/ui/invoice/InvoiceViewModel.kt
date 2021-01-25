package com.axelfernandez.deliverylavallevendedores.ui.invoice

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.axelfernandez.deliverylavallevendedores.api.Api
import com.axelfernandez.deliverylavallevendedores.api.RetrofitFactory
import com.axelfernandez.deliverylavallevendedores.models.Invoice
import com.axelfernandez.deliverylavallevendedores.repository.CompanyRepository

class InvoiceViewModel : ViewModel() {

    private val companyRepository = CompanyRepository(RetrofitFactory.buildService(Api::class.java))


    fun getInvoices(token:String){
        companyRepository.getInvoices(token)
    }
    fun returnInvoices(): LiveData<List<Invoice>> {
        return companyRepository.returnInvoices()
    }
}