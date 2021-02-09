package com.axelfernandez.deliverylavallevendedores.ui.orderDetail

import android.content.Context
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.axelfernandez.deliverylavallevendedores.R
import com.axelfernandez.deliverylavallevendedores.adapter.OrderDetailAdapter
import com.axelfernandez.deliverylavallevendedores.api.Api
import com.axelfernandez.deliverylavallevendedores.api.RetrofitFactory
import com.axelfernandez.deliverylavallevendedores.models.Order
import com.axelfernandez.deliverylavallevendedores.repository.OrderRepository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.component_title.view.*
import kotlinx.android.synthetic.main.order_detail_fragment.view.*

class OrderDetailViewModel : ViewModel() {

    private lateinit var orderRepository :OrderRepository

    fun getRepository(context: Context) {
        orderRepository = OrderRepository(RetrofitFactory.buildService(Api::class.java, context))
    }

    fun fetchOrder(orderId: String){
        orderRepository.solicitOrderById(orderId)
    }

    fun returnDataAndBuild(): LiveData<Order> {
        return orderRepository.returnOrderById()
    }

    fun setNewState(orderId: String){
        orderRepository.solicitSetNewState(orderId)
    }
    fun cancelOrder(orderId: String){
        orderRepository.cancelOrder(orderId)
    }
    fun returnNewState(): LiveData<Order> {
        return orderRepository.returnNewStateOrder()
    }

    fun bind(order: Order, v : View, context :Context){
        v.detail_order_id.component_title_title.text = context.getString(R.string.detail_order_id, order.id)
        v.detail_order_client_state.text = context.getString(R.string.detail_order_state, order.state)
        v.detail_order_payment_method.text = context.getString(R.string.detail_order_payment_method, order.paymentMethod)
        v.detail_order_client_name.text= context.getString(R.string.detail_order_client_name,order.client.name)
        if (order.retryInLocal){
            v.detail_order_client_address.text= context.getString(R.string.retry_in_local_label)
            v.detail_order_show_location.isVisible = false

        }else{
            v.detail_order_client_address.text= context.getString(R.string.detail_order_client_address,order.address.street, order.address.number, order.address.district, order.address.floor, order.address.reference).replace("null","")
            //If doesn't, Must redirect to Map fragment
            if(order.address.location.isNullOrEmpty()){
                v.detail_order_show_location.isVisible = false
            }
        }
        v.detail_order_client_phone.text= context.getString(R.string.detail_order_client_phone, order.client.phone)
        v.order_detail_total.text= context.getString(R.string.detail_order_total, order.total)
        Picasso.with(context).load(order.client.photo).into(v.detail_order_client_image)

        val productRv = v.findViewById(R.id.rv_order_detail_products) as RecyclerView
        productRv.isNestedScrollingEnabled = false
        productRv.layoutManager =  LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        productRv.adapter = OrderDetailAdapter(order.items, context)

        if(order.paymentMethod == context.getString(R.string.mercado_pago)){
            v.detail_order_meli_link.isVisible = true
            v.detail_order_meli_link.text = context.getString(R.string.mercado_pago_label)
        }
        v.detail_order_cancel_order.isVisible = false
        when (order.state){
            context.getString(R.string.pending) ->{
                v.detail_order_changeState.text = context.getString(R.string.preparing)
                v.detail_order_cancel_order.isVisible = true
                v.detail_order_cancel_order.text = context.getString(R.string.canceled)
                v.detail_order_client_state.setTextColor(context.resources.getColor(R.color.green))


            }
            context.getString(R.string.preparing) ->{
                if(order.retryInLocal){
                    v.detail_order_changeState.text = context.getString(R.string.ready_to_retire)
                }else{
                    v.detail_order_changeState.text = context.getString(R.string.on_way)
                }
                v.detail_order_client_state.setTextColor(context.resources.getColor(R.color.blue))
                v.detail_order_changeState.background = context.getDrawable(R.color.blue)



            }
            context.getString(R.string.on_way) -> {
                v.detail_order_changeState.text = context.getString(R.string.delivered)
                v.detail_order_client_state.setTextColor(context.resources.getColor(R.color.red))
                v.detail_order_changeState.background = context.getDrawable(R.color.red)
            }
            context.getString(R.string.ready_to_retire) -> {
                v.detail_order_changeState.text = context.getString(R.string.delivered)
                v.detail_order_client_state.setTextColor(context.resources.getColor(R.color.red))
                v.detail_order_changeState.background = context.getDrawable(R.color.red)
            }
            context.getString(R.string.delivered) -> {
                v.detail_order_changeState.text = context.getString(R.string.delivered)
                v.detail_order_changeState.background = context.getDrawable(R.color.grey)
                v.detail_order_changeState.isEnabled = false


            }
            context.getString(R.string.canceled) -> {
                v.detail_order_changeState.text = context.getString(R.string.canceled)
                v.detail_order_changeState.background = context.getDrawable(R.color.grey)
                v.detail_order_changeState.isEnabled = false

            }



        }





    }
}