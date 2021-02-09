package com.axelfernandez.deliverylavallevendedores.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.axelfernandez.deliverylavallevendedores.api.Api
import com.axelfernandez.deliverylavallevendedores.api.RetrofitFactory
import com.axelfernandez.deliverylavallevendedores.models.Company
import com.axelfernandez.deliverylavallevendedores.models.Order
import com.axelfernandez.deliverylavallevendedores.repository.CompanyRepository
import com.axelfernandez.deliverylavallevendedores.repository.OrderRepository

class HomeViewModel : ViewModel() {

    private lateinit var orderRepository :OrderRepository
    private lateinit var companyRepository :CompanyRepository

    fun getRepository(context: Context) {
        orderRepository  = OrderRepository(RetrofitFactory.buildService(Api::class.java,context))
        companyRepository = CompanyRepository(RetrofitFactory.buildService(Api::class.java, context))
    }

    fun solicitPendingOrders() {
        orderRepository.solicitOrderPending()
    }

    fun returnPendingOrders(): LiveData<List<Order>> {
        return orderRepository.returnOrdersPending()
    }

    fun solicitInProgressOrders() {
        orderRepository.solicitOrderInProgress()
    }

    fun returnInProgressOrders(): LiveData<List<Order>> {
        return orderRepository.returnOrdersInProgress()
    }

    fun getAvailability() {
        companyRepository.getCompanyAvailability()
    }

    fun postAvailability(available: Boolean) {
        companyRepository.postCompanyAvailability(available)
    }

    fun returnAvailability(): LiveData<Boolean> {
        return companyRepository.returnAvailability()
    }

}