package com.axelfernandez.deliverylavallevendedores.ui.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.axelfernandez.deliverylavallevendedores.api.Api
import com.axelfernandez.deliverylavallevendedores.api.RetrofitFactory
import com.axelfernandez.deliverylavallevendedores.models.Product
import com.axelfernandez.deliverylavallevendedores.models.ProductCategory
import com.axelfernandez.deliverylavallevendedores.repository.ProductRepository

class ProductsViewModel : ViewModel() {

    private val productRepository = ProductRepository(RetrofitFactory.buildService(Api::class.java))

    fun solicitProduct(token:String){
        productRepository.getProducts(token)
    }
    fun returnProducts(): LiveData<List<Product>> {
        return productRepository.returnProducts()
    }
    fun deleteProduct(token: String, product: Product){
        productRepository.deleteProduct(token, product)
    }
    fun returnConfirmDeleted(): LiveData<String> {
        return productRepository.returnConfirmationProductAdded()
    }
    fun getCategories(token: String){
        productRepository.solicitProductCategory(token)
    }
    fun returnCategories(): LiveData<List<ProductCategory>> {
        return productRepository.returnProductCategory()
    }
}