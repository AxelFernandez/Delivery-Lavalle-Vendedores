package com.axelfernandez.deliverylavallevendedores.ui.category

import android.content.Context
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

    private lateinit var productRepository :ProductRepository
    fun getRepository(context: Context) {
        productRepository = ProductRepository(RetrofitFactory.buildService(Api::class.java, context))
    }

    fun solicitCategory(){
        productRepository.solicitProductCategory()
    }
    fun returnCategory(): LiveData<List<ProductCategory>> {
        return productRepository.returnProductCategory()
    }

    fun deleteCategory(categoryRequest: ProductCategoryRequest){
        productRepository.deleteNewProductCategory(categoryRequest)
    }
    fun returnResponseDeleted(): LiveData<String> {
        return productRepository.returnProductDeleted()
    }
}