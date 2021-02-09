package com.axelfernandez.deliverylavallevendedores.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.axelfernandez.deliverylavallevendedores.api.Api
import com.axelfernandez.deliverylavallevendedores.models.MeliLink
import com.axelfernandez.deliverylavallevendedores.models.Order
import com.axelfernandez.deliverylavallevendedores.models.Review
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderRepository(
    private val api: Api
) {
    val dataPending = MutableLiveData<List<Order>>()
    fun solicitOrderPending(): MutableLiveData<List<Order>> {
        api.getOrdersPending().enqueue(object :
            Callback<List<Order>> {
            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                dataPending.value = null
            }

            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                dataPending.value = response.body()
            }
        })
        return dataPending
    }

    fun returnOrdersPending(): LiveData<List<Order>> {
        return dataPending
    }


    val dataInProgress = MutableLiveData<List<Order>>()
    fun solicitOrderInProgress(): MutableLiveData<List<Order>> {
        api.getOrdersInProgress().enqueue(object :
            Callback<List<Order>> {
            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                dataInProgress.value = null

            }

            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                dataInProgress.value = response.body()

            }
        })
        return dataInProgress
    }

    fun returnOrdersInProgress(): LiveData<List<Order>> {
        return dataInProgress
    }


    val dataClosed = MutableLiveData<List<Order>>()
    fun solicitOrderClosed(): MutableLiveData<List<Order>> {
        api.getOrdersClosed().enqueue(object :
            Callback<List<Order>> {
            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                dataInProgress.value = null

            }

            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                dataInProgress.value = response.body()

            }
        })
        return dataInProgress
    }

    fun returnOrdersClosed(): LiveData<List<Order>> {
        return dataInProgress
    }


    val dataOrderById = MutableLiveData<Order>()
    fun solicitOrderById(orderId: String): MutableLiveData<Order> {
        api.getOrderById(orderId).enqueue(object :
            Callback<Order> {
            override fun onFailure(call: Call<Order>, t: Throwable) {
                dataOrderById.value = null
            }

            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                dataOrderById.value = response.body()
            }
        })
        return dataOrderById
    }

    fun returnOrderById(): LiveData<Order> {
        return dataOrderById
    }


    val dataOrderToNewState = MutableLiveData<Order>()
    fun solicitSetNewState(orderId: String): MutableLiveData<Order> {
        api.setOrderInNextState(orderId).enqueue(object :
            Callback<Order> {
            override fun onFailure(call: Call<Order>, t: Throwable) {
                dataOrderToNewState.value = null
            }

            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                dataOrderToNewState.value = response.body()
            }
        })
        return dataOrderToNewState
    }

    fun cancelOrder(orderId: String): MutableLiveData<Order> {
        api.cancelOrder(orderId).enqueue(object :
            Callback<Order> {
            override fun onFailure(call: Call<Order>, t: Throwable) {
                dataOrderToNewState.value = null
            }

            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                dataOrderToNewState.value = response.body()
            }
        })
        return dataOrderToNewState
    }

    fun returnNewStateOrder(): LiveData<Order> {
        return dataOrderToNewState
    }

    val reviews = MutableLiveData<List<Review>>()
    fun getReviews(companyId: String): MutableLiveData<List<Review>> {
        api.getReviews(companyId)
            .enqueue(object : Callback<List<Review>> {
                override fun onFailure(call: Call<List<Review>>, t: Throwable) {
                    reviews.value = null
                }

                override fun onResponse(
                    call: Call<List<Review>>,
                    response: Response<List<Review>>
                ) {
                    reviews.value = response.body()
                }
            })
        return reviews
    }

    fun returnReviews(): LiveData<List<Review>> {
        return reviews
    }

    val meliLink = MutableLiveData<MeliLink>()

    fun returnMeliLink(): LiveData<MeliLink> {
        return meliLink
    }

    fun getMeliLinkByOrderId(orderId: String): MutableLiveData<MeliLink> {
        api.getMeLiLink(orderId).enqueue(object : Callback<MeliLink> {
            override fun onFailure(call: Call<MeliLink>, t: Throwable) {
                meliLink.value = null
            }

            override fun onResponse(call: Call<MeliLink>, response: Response<MeliLink>) {
                meliLink.value = response.body()
            }
        })
        return meliLink

    }

    val meliLinkId = MutableLiveData<String>()
    fun sendMeliLink(link: MeliLink): MutableLiveData<String> {
        api.sendMeLiLink(link).enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                meliLinkId.value = null
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                meliLinkId.value = response.body()
            }
        })
        return meliLinkId
    }

    fun returnMeliLinkConfirmation(): LiveData<String> {
        return meliLinkId
    }
}