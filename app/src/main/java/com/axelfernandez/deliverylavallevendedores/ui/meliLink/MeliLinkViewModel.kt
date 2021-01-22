package com.axelfernandez.deliverylavallevendedores.ui.meliLink

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.axelfernandez.deliverylavallevendedores.api.Api
import com.axelfernandez.deliverylavallevendedores.api.RetrofitFactory
import com.axelfernandez.deliverylavallevendedores.models.MeliLink
import com.axelfernandez.deliverylavallevendedores.repository.OrderRepository

class MeliLinkViewModel : ViewModel() {
    private val orderRepository = OrderRepository(RetrofitFactory.buildService(Api::class.java))

    fun fetchMeliLink(orderId: String, token:String){
        orderRepository.getMeliLinkByOrderId(orderId,token)
    }
    fun returnExistedMeliLink(): LiveData<MeliLink> {
        return orderRepository.returnMeliLink()
    }

    fun sendMeliLink(meliLink: MeliLink, token: String){
        orderRepository.sendMeliLink(token,meliLink)
    }

    fun returnConfirmation(): LiveData<String> {
        return orderRepository.returnMeliLinkConfirmation()
    }


}