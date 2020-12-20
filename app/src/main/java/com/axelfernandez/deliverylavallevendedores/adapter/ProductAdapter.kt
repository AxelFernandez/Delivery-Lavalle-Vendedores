package com.axelfernandez.deliverylavallevendedores.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.axelfernandez.deliverylavallevendedores.R
import com.axelfernandez.deliverylavallevendedores.models.Product
import com.squareup.picasso.Picasso

class ProductAdapter(
    private val product: List<Product> = ArrayList(),
    private val context: Context,
    private val editClickListener: (Product) -> Unit,
    private val deleteClickListener: (Product) -> Unit
) :RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductAdapter.ProductViewHolder(
            layoutInflater.inflate(
                R.layout.item_products,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return product.size
    }

    override fun onBindViewHolder(holder: ProductAdapter.ProductViewHolder, position: Int) {
        val item : Product = product[position]
        holder.bind(item, editClickListener, deleteClickListener,context)
    }

    class ProductViewHolder (itemView : View): RecyclerView.ViewHolder(itemView){
        private val title : TextView = itemView.findViewById(R.id.item_product_title) as TextView
        private val subtitle : TextView = itemView.findViewById(R.id.item_product_subtitle) as TextView
        private val price : TextView = itemView.findViewById(R.id.item_product_price) as TextView
        private val image : ImageView = itemView.findViewById(R.id.item_product_image) as ImageView
        private val edit : ImageView = itemView.findViewById(R.id.item_product_edit) as ImageView
        private val delete : ImageView = itemView.findViewById(R.id.item_product_delete) as ImageView

        fun bind(
            item: Product,
            editClickListener: (Product) -> Unit,
            deleteClickListener: (Product) -> Unit,
            context: Context
        ){
            title.text = item.name
            subtitle.text = item.description
            price.text = item.price
            Picasso.with(context).load(item.photo).into(image)
            edit.setOnClickListener { editClickListener(item) }
            delete.setOnClickListener { deleteClickListener(item) }
        }


    }

}