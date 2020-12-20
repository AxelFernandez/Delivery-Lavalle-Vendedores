package com.axelfernandez.deliverylavallevendedores.ui.category

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.axelfernandez.deliverylavallevendedores.api.Api
import com.axelfernandez.deliverylavallevendedores.api.RetrofitFactory
import com.axelfernandez.deliverylavallevendedores.models.Product
import com.axelfernandez.deliverylavallevendedores.models.ProductCategory
import com.axelfernandez.deliverylavallevendedores.models.ProductCategoryRequest
import com.axelfernandez.deliverylavallevendedores.repository.OrderRepository
import com.axelfernandez.deliverylavallevendedores.repository.ProductRepository

class CategoryViewModel : ViewModel() {

    private val productRepository = ProductRepository(RetrofitFactory.buildService(Api::class.java))

    fun solicitCategory(token: String){
        productRepository.solicitProductCategory(token)
    }
    fun returnCategory(): LiveData<List<ProductCategory>> {
        return productRepository.returnProductCategory()
    }

    fun deleteCategory(token: String, categoryRequest: ProductCategoryRequest){
        productRepository.deleteNewProductCategory(token,categoryRequest)
    }
    fun returnResponseDeleted(): LiveData<String> {
        return productRepository.returnProductDeleted()
    }
}