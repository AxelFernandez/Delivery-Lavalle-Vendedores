package com.axelfernandez.deliverylavallevendedores.ui.profile

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.axelfernandez.deliverylavallevendedores.api.Api
import com.axelfernandez.deliverylavallevendedores.api.RetrofitFactory
import com.axelfernandez.deliverylavallevendedores.models.Company
import com.axelfernandez.deliverylavallevendedores.repository.CompanyRepository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.profile_fragment.view.*

class ProfileViewModel : ViewModel() {

    private lateinit var companyRepository : CompanyRepository

    fun getRepository(context: Context) {
        companyRepository = CompanyRepository(RetrofitFactory.buildService(Api::class.java, context))
    }
    fun bind(company : Company, context: Context, view : View){
        view.settings_name.text = company.name
        view.settings_address.text = company.address
        Picasso.with(context).load(company.photo).into(view.settings_image)
    }

    fun getCompanyData(){
        companyRepository.getCompanyFullData()
    }

    fun returnData(): LiveData<Company> {
        return companyRepository.returnCompany()
    }

    fun getAccountDebit(){
        companyRepository.getAccountDebit()
    }

    fun returnAccountDebit(): LiveData<String> {
        return companyRepository.returnCompanyAccountDebit()
    }

    fun fetchPendingInvoices(){
        companyRepository.hadPendingInvoices()
    }
    fun returnFetchPendingInvoices(): LiveData<Boolean> {
        return companyRepository.returnHadPendingInvoices()
    }
}