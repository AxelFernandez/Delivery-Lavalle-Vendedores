package com.axelfernandez.deliverylavallevendedores.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.axelfernandez.deliverylavallevendedores.api.Api
import com.axelfernandez.deliverylavallevendedores.api.RetrofitFactory
import com.axelfernandez.deliverylavallevendedores.models.Order
import com.axelfernandez.deliverylavallevendedores.repository.CompanyRepository
import com.axelfernandez.deliverylavallevendedores.repository.OrderRepository

class HomeViewModel : ViewModel() {

    private val orderRepository = OrderRepository(RetrofitFactory.buildService(Api::class.java))


    fun solicitPendingOrders(token: String){
        orderRepository.solicitOrderPending(token)
    }
    fun returnPendingOrders(): LiveData<List<Order>> {
        return orderRepository.returnOrdersPending()
    }


    fun solicitInProgressOrders(token: String){
        orderRepository.solicitOrderInProgress(token)
    }
    fun returnInProgressOrders(): LiveData<List<Order>> {
        return orderRepository.returnOrdersInProgress()
    }



}