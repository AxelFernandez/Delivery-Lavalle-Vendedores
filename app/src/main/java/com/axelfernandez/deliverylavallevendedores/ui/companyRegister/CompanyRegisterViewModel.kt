package com.axelfernandez.deliverylavallevendedores.ui.companyRegister

import android.content.Context
import android.os.Build
import android.view.View
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.axelfernandez.deliverylavallevendedores.R
import com.axelfernandez.deliverylavallevendedores.api.Api
import com.axelfernandez.deliverylavallevendedores.api.RetrofitFactory
import com.axelfernandez.deliverylavallevendedores.models.Company
import com.axelfernandez.deliverylavallevendedores.models.CompanyCategoryResponse
import com.axelfernandez.deliverylavallevendedores.repository.CompanyCategoryRepository
import com.axelfernandez.deliverylavallevendedores.repository.CompanyRepository
import com.axelfernandez.deliverylavallevendedores.utils.LoginUtils
import com.axelfernandez.deliverylavallevendedores.utils.ViewUtil
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.company_register_fragment.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class CompanyRegisterViewModel : ViewModel() {

    private val companyCategoryRepository = CompanyCategoryRepository(RetrofitFactory.buildService(Api::class.java))
    private val companyRepository = CompanyRepository(RetrofitFactory.buildService(Api::class.java))

    fun validateFields(view: View, context: Context):Boolean{
        var result = false
        view.name.text.isNullOrEmpty().let {
            result = it
            if(it) {
                view.name.error = context.getString(R.string.required)
            }
        }
        view.description.text.isNullOrEmpty().let {
            result = it
            if(it) {
                view.description.error = context.getString(R.string.required)
            }
        }

        view.address.text.isNullOrEmpty().let {
            result = it
            if(it) {
                view.address.error = context.getString(R.string.required)
            }
        }
        view.phone.text.isNullOrEmpty().let {
            result = it
            if(it) {
                view.phone.error = context.getString(R.string.required)
            }
        }

        if( !view.payment_method_cash.isChecked && !view.payment_method_MercadoPago.isChecked){
            result = true
            ViewUtil.setSnackBar(view, R.color.orange,"Debes Seleccionar un metodo de pago")
        }
        if( !view.delivery_method_delivery.isChecked && !view.delivery_method_in_local.isChecked){
            result = true
            ViewUtil.setSnackBar(view, R.color.orange,"Debes Seleccionar un metodo de entrega")
        }
        return result
    }

    fun requestCategories(token : String){
        companyCategoryRepository.getCompanyCategory(token)
    }

    fun returnCategories():LiveData<List<CompanyCategoryResponse>>{
       return companyCategoryRepository.returnData()
    }

    fun registryCompany(token : String, company: Company,file : File, limits:String){
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
        val name = MultipartBody.Part.createFormData("name", company.name, requestFile)
        val description = MultipartBody.Part.createFormData("description", company.description, requestFile)
        val phone = MultipartBody.Part.createFormData("phone", company.phone, requestFile)
        val address = MultipartBody.Part.createFormData("address", company.address, requestFile)
        val availableNow = MultipartBody.Part.createFormData("availableNow", company.availableNow.toString(), requestFile)
        val paymentMethod = MultipartBody.Part.createFormData("paymentMethods", company.paymentMethods.toString(), requestFile)
        val deliveryMethod = MultipartBody.Part.createFormData("deliveryMethods", company.deliveryMethods.toString(), requestFile)
        val category = MultipartBody.Part.createFormData("category", company.category, requestFile)
        val limit = MultipartBody.Part.createFormData("limits", limits, requestFile)

        companyRepository.registerCompany(token, body,name,description,phone,address,paymentMethod,deliveryMethod,category,limit,availableNow)
    }
    fun returnCompanyRegistry(): LiveData<String> {
        return companyRepository.returnData()
    }

    fun buildCompany(view: View, context:Context,limits:String):Company{
        val paymentMethod = ArrayList<String>()
        val deliveryMethod = ArrayList<String>()

        if( view.payment_method_cash.isChecked){paymentMethod.add(context.getString(R.string.cash))}
        if( view.payment_method_MercadoPago.isChecked){paymentMethod.add(context.getString(R.string.mercado_pago))}
        if( view.delivery_method_in_local.isChecked){deliveryMethod.add(context.getString(R.string.retry_in_local))}
        if( view.delivery_method_delivery.isChecked){deliveryMethod.add(context.getString(R.string.delivery_method_delivery))}
        return Company(
            id = null,
            name = view.name.text.toString(),
            description = view.description.text.toString(),
            category = view.spinner_category.selectedItem.toString(),
            address = view.address.text.toString(),
            phone = view.phone.text.toString(),
            deliveryMethods = deliveryMethod,
            paymentMethods = paymentMethod,
            limits = limits,
            photo = null,
            availableNow = view.available_now.isChecked
        )

    }

    fun registryCompanyNoImage(token: String,company: Company){
        companyRepository.registerCompanyNoImage(token,company)
    }

    fun bind(view : View, context: Context, company: Company){
        view.name.setText(company.name)
        view.description.setText(company.description)
        view.address.setText(company.address)
        view.phone.setText(company.phone)
        view.company_register_add_photo.text = context.getString(R.string.photo_selected)
        Picasso.with(context).load(company.photo).into(view.company_image)
        view.company_image.isVisible = true
        view.available_now.isChecked = company.availableNow
        company.paymentMethods?.forEach {
            if(it == context.getString(R.string.cash)){
                view.payment_method_cash.isChecked = true
            }
            if(it == context.getString(R.string.mercado_pago)){
                view.payment_method_cash.isChecked = true
            }
        }
        company.deliveryMethods?.forEach {
            if(it == context.getString(R.string.retry_in_local)){
                view.delivery_method_in_local.isChecked = true
            }
            if(it == context.getString(R.string.delivery_method_delivery)){
                view.delivery_method_delivery.isChecked = true
            }
        }
    }

    fun getCompanyData(token:String){
        companyRepository.getCompanyData(token)
    }

    fun returnData(): LiveData<Company> {
        return companyRepository.returnCompany()
    }
}