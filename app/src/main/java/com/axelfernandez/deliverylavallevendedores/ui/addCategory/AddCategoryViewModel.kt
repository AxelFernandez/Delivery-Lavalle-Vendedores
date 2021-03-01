package com.axelfernandez.deliverylavallevendedores.ui.addCategory

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.axelfernandez.deliverylavallevendedores.api.Api
import com.axelfernandez.deliverylavallevendedores.api.RetrofitFactory
import com.axelfernandez.deliverylavallevendedores.models.ProductCategory
import com.axelfernandez.deliverylavallevendedores.models.ProductCategoryRequest
import com.axelfernandez.deliverylavallevendedores.repository.ProductRepository

class AddCategoryViewModel : ViewModel() {

    private lateinit var productRepository: ProductRepository

    fun getRepository(context: Context) {
        productRepository =
            ProductRepository(RetrofitFactory.buildService(Api::class.java, context))
    }

    fun postNewCategory(categoryRequest: ProductCategory) {
        productRepository.postNewProductCategory(categoryRequest)
    }


    fun returnCategory(): LiveData<List<ProductCategory>> {
        return productRepository.returnProductCategory()
    }

}