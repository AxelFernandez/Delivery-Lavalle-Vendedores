package com.axelfernandez.deliverylavallevendedores.ui.meliLink

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.axelfernandez.deliverylavallevendedores.api.Api
import com.axelfernandez.deliverylavallevendedores.api.RetrofitFactory
import com.axelfernandez.deliverylavallevendedores.models.MeliLink
import com.axelfernandez.deliverylavallevendedores.repository.OrderRepository

class MeliLinkViewModel : ViewModel() {
    private lateinit var orderRepository  :OrderRepository

    fun getRepository(context: Context) {
        orderRepository = OrderRepository(RetrofitFactory.buildService(Api::class.java,context))
    }
    fun fetchMeliLink(orderId: String){
        orderRepository.getMeliLinkByOrderId(orderId)
    }
    fun returnExistedMeliLink(): LiveData<MeliLink> {
        return orderRepository.returnMeliLink()
    }

    fun sendMeliLink(meliLink: MeliLink){
        orderRepository.sendMeliLink(meliLink)
    }

    fun returnConfirmation(): LiveData<String> {
        return orderRepository.returnMeliLinkConfirmation()
    }


}