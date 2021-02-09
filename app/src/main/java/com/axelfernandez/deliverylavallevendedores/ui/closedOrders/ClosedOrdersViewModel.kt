package com.axelfernandez.deliverylavallevendedores.ui.closedOrders

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.axelfernandez.deliverylavallevendedores.R
import com.axelfernandez.deliverylavallevendedores.adapter.OrderAdapter
import com.axelfernandez.deliverylavallevendedores.api.Api
import com.axelfernandez.deliverylavallevendedores.api.RetrofitFactory
import com.axelfernandez.deliverylavallevendedores.models.Order
import com.axelfernandez.deliverylavallevendedores.repository.OrderRepository

class ClosedOrdersViewModel : ViewModel() {

    private lateinit var orderRepository :OrderRepository

    fun getRepository(context: Context) {
        orderRepository = OrderRepository(RetrofitFactory.buildService(Api::class.java, context))
    }
    fun solicitClosedOrders(){
        orderRepository.solicitOrderClosed()
    }
    fun returnClosedPending(): LiveData<List<Order>> {
        return orderRepository.returnOrdersClosed()
    }


}