package com.axelfernandez.deliverylavallevendedores.ui.addProduct

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.axelfernandez.deliverylavallevendedores.R
import com.axelfernandez.deliverylavallevendedores.api.Api
import com.axelfernandez.deliverylavallevendedores.api.RetrofitFactory
import com.axelfernandez.deliverylavallevendedores.models.Product
import com.axelfernandez.deliverylavallevendedores.models.ProductCategory
import com.axelfernandez.deliverylavallevendedores.repository.ProductRepository
import kotlinx.android.synthetic.main.add_product_fragment.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class AddProductViewModel : ViewModel() {

    private val productRepository = ProductRepository(RetrofitFactory.buildService(Api::class.java))

    fun validateFields(view: View, context: Context):Boolean{
        var result = false
        view.add_name_product_field.text.isNullOrEmpty().let {
            result = it
            if(it) {
                view.add_name_product_field.error = context.getString(R.string.required)
            }
        }
        view.add_name_description_field.text.isNullOrEmpty().let {
            result = it
            if(it) {
                view.add_name_description_field.error = context.getString(R.string.required)
            }
        }
        view.add_price_product_field.text.isNullOrEmpty().let {
            result = it
            if(it) {
                view.add_price_product_field.error = context.getString(R.string.required)
            }
        }
        return result
    }

    fun requestCategory(token :String){
        productRepository.solicitProductCategory(token)
    }

    fun returnProductsCategory(): LiveData<List<ProductCategory>> {
        return productRepository.returnProductCategory()
    }

    fun addNewProduct(token: String, product: Product, file : File){
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
        val name = MultipartBody.Part.createFormData("name", product.name, requestFile)
        val description = MultipartBody.Part.createFormData("description", product.description, requestFile)
        val price = MultipartBody.Part.createFormData("price", product.price, requestFile)
        val availableNow = MultipartBody.Part.createFormData("availableNow", product.availableNow.toString(), requestFile)
        val category = MultipartBody.Part.createFormData("category", product.category, requestFile)
        productRepository.addProduct(token, body,name,description,price, category,availableNow)
    }

    fun confirmProductAdded(): LiveData<String> {
        return productRepository.returnConfirmationProductAdded()
    }

    fun buildProduct(view: View):Product{
        return Product(id=null,
            name= view.add_name_product_field.text.toString(),
            description = view.add_name_description_field.text.toString(),
            availableNow = view.available_now.isChecked,
            price = view.add_price_product_field.text.toString(),
            category = view.spinner_category_product.selectedItem.toString(),
            photo = null
        )
    }
}