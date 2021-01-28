package com.axelfernandez.deliverylavallevendedores.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.axelfernandez.deliverylavallevendedores.api.Api
import com.axelfernandez.deliverylavallevendedores.api.RetrofitFactory
import com.axelfernandez.deliverylavallevendedores.models.Company
import com.axelfernandez.deliverylavallevendedores.models.Order
import com.axelfernandez.deliverylavallevendedores.repository.CompanyRepository
import com.axelfernandez.deliverylavallevendedores.repository.OrderRepository

class HomeViewModel : ViewModel() {

    private val orderRepository = OrderRepository(RetrofitFactory.buildService(Api::class.java))
    private val companyRepository = CompanyRepository(RetrofitFactory.buildService(Api::class.java))


    fun solicitPendingOrders(token: String) {
        orderRepository.solicitOrderPending(token)
    }

    fun returnPendingOrders(): LiveData<List<Order>> {
        return orderRepository.returnOrdersPending()
    }

    fun solicitInProgressOrders(token: String) {
        orderRepository.solicitOrderInProgress(token)
    }

    fun returnInProgressOrders(): LiveData<List<Order>> {
        return orderRepository.returnOrdersInProgress()
    }

    fun getAvailability(token: String) {
        companyRepository.getCompanyAvailability(token)
    }

    fun postAvailability(token: String, available: Boolean) {
        companyRepository.postCompanyAvailability(token, available)
    }

    fun returnAvailability(): LiveData<Boolean> {
        return companyRepository.returnAvailability()
    }

}