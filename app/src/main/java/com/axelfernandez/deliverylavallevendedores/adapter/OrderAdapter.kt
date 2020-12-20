package com.axelfernandez.deliverylavallevendedores.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.axelfernandez.deliverylavallevendedores.R
import com.axelfernandez.deliverylavallevendedores.models.Order

class OrderAdapter(
    private val orders: List<Order>  = ArrayList(),
    private val context: Context,
    private val colorForState : Int,
    private val itemClickListener: (Order) -> Unit
    ) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return OrderViewHolder(layoutInflater.inflate(R.layout.item_order,parent,false))
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val item : Order = orders[position]
        holder.bind(item,context, itemClickListener,colorForState)
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    class OrderViewHolder (itemView : View): RecyclerView.ViewHolder(itemView){
        var id : TextView = itemView.findViewById(R.id.item_order_id) as TextView
        var state : TextView = itemView.findViewById(R.id.item_order_state) as TextView
        var client : TextView = itemView.findViewById(R.id.item_order_client) as TextView
        var layout : LinearLayout = itemView.findViewById(R.id.item_order_cardview) as LinearLayout

        fun bind(order : Order, context: Context,
                 itemClickListener: (Order) -> Unit,
                 color: Int
        ){
            id.text = context.getString(R.string.order_number, order.id)
            state.text = context.getString(R.string.order_state,order.state)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                state.setTextColor(context.getColor(color))
            }
            client.text = context.getString(R.string.client_name,order.client.name)
            layout.setOnClickListener { itemClickListener(order) }

        }
    }

}