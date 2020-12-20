package com.axelfernandez.deliverylavallevendedores.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.axelfernandez.deliverylavallevendedores.R
import com.axelfernandez.deliverylavallevendedores.models.ProductDetail


class OrderDetailAdapter(
    private val product: List<ProductDetail>  = ArrayList(),
    private val context: Context
    ) : RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return OrderDetailViewHolder(layoutInflater.inflate(R.layout.order_detail_detail_item,parent,false))
    }

    override fun getItemCount(): Int {
        return product.size
    }

    override fun onBindViewHolder(holder: OrderDetailViewHolder, position: Int) {
        var item : ProductDetail = product[position]
        holder.bind(item,context)
    }
    class OrderDetailViewHolder (itemView : View): RecyclerView.ViewHolder(itemView){
        private val description : TextView = itemView.findViewById(R.id.order_detail_detail_description) as TextView
        private val quantity : TextView = itemView.findViewById(R.id.order_detail_detail_quantity) as TextView
        private val subtotal : TextView = itemView.findViewById(R.id.order_detail_detail_subtotal) as TextView

        fun bind(item : ProductDetail, context: Context){
            description.text = item.description
            quantity.text = item.quantity
            subtotal.text = context.getString(R.string.detail_order_total, item.subtotal)
        }


    }


}