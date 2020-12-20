package com.axelfernandez.deliverylavallevendedores.ui.addCategory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.axelfernandez.deliverylavallevendedores.api.Api
import com.axelfernandez.deliverylavallevendedores.api.RetrofitFactory
import com.axelfernandez.deliverylavallevendedores.models.ProductCategory
import com.axelfernandez.deliverylavallevendedores.models.ProductCategoryRequest
import com.axelfernandez.deliverylavallevendedores.repository.ProductRepository

class AddCategoryViewModel : ViewModel() {

    private val productRepository = ProductRepository(RetrofitFactory.buildService(Api::class.java))

    fun postNewCategory(token: String, categoryRequest: ProductCategoryRequest){
        productRepository.postNewProductCategory(token, categoryRequest)
    }


    fun returnCategory(): LiveData<List<ProductCategory>> {
        return productRepository.returnProductCategory()
    }

}