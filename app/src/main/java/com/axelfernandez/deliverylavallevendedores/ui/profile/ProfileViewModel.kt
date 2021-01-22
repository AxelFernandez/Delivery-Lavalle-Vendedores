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
    // TODO: Implement the ViewModel

    private val companyRepository = CompanyRepository(RetrofitFactory.buildService(Api::class.java))

    fun bind(company : Company, context: Context, view : View){
        view.settings_name.text = company.name
        view.settings_address.text = company.address
        Picasso.with(context).load(company.photo).into(view.settings_image)
    }

    fun getCompanyData(token:String){
        companyRepository.getCompanyData(token)
    }

    fun returnData(): LiveData<Company> {
        return companyRepository.returnCompany()
    }

    fun getAccountDebit(token:String){
        companyRepository.getAccountDebit(token)
    }

    fun returnAccountDebit(): LiveData<String> {
        return companyRepository.returnCompanyAccountDebit()
    }


}