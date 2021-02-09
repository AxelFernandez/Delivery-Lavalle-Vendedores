package com.axelfernandez.deliverylavallevendedores.ui.products

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.axelfernandez.deliverylavallevendedores.api.Api
import com.axelfernandez.deliverylavallevendedores.api.RetrofitFactory
import com.axelfernandez.deliverylavallevendedores.models.Product
import com.axelfernandez.deliverylavallevendedores.models.ProductCategory
import com.axelfernandez.deliverylavallevendedores.repository.ProductRepository

class ProductsViewModel : ViewModel() {

    private lateinit var productRepository : ProductRepository

    fun getRepository(context: Context) {
        productRepository = ProductRepository(RetrofitFactory.buildService(Api::class.java, context))
    }

    fun solicitProduct(){
        productRepository.getProducts()
    }
    fun returnProducts(): LiveData<List<Product>> {
        return productRepository.returnProducts()
    }
    fun deleteProduct(product: Product){
        productRepository.deleteProduct(product)
    }
    fun returnConfirmDeleted(): LiveData<String> {
        return productRepository.returnConfirmationProductAdded()
    }
    fun getCategories(){
        productRepository.solicitProductCategory()
    }
    fun returnCategories(): LiveData<List<ProductCategory>> {
        return productRepository.returnProductCategory()
    }
}