package com.axelfernandez.deliverylavallevendedores.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.axelfernandez.deliverylavallevendedores.R
import com.axelfernandez.deliverylavallevendedores.models.ProductCategory

class ProductCategoryAdapter (
    private val product: List<ProductCategory>  = ArrayList(),
    private val editClickListener: (ProductCategory) -> Unit,
    private val deleteClickListener: (ProductCategory) -> Unit

):RecyclerView.Adapter<ProductCategoryAdapter.ProductCategoryViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductCategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductCategoryViewHolder(layoutInflater.inflate(R.layout.item_category, parent, false))
    }

    override fun getItemCount(): Int {
        return product.size
    }

    override fun onBindViewHolder(holder: ProductCategoryViewHolder, position: Int) {
        val item : ProductCategory = product[position]
        holder.bind(item, editClickListener, deleteClickListener)
    }
    class ProductCategoryViewHolder (itemView : View): RecyclerView.ViewHolder(itemView){
        private val description : TextView = itemView.findViewById(R.id.item_category_name) as TextView
        private val quantity : TextView = itemView.findViewById(R.id.item_category_quantity) as TextView
        private val edit : ImageView = itemView.findViewById(R.id.item_category_edit) as ImageView
        private val delete : ImageView = itemView.findViewById(R.id.item_category_delete) as ImageView

        fun bind(
            item: ProductCategory,
            editClickListener: (ProductCategory) -> Unit,
            deleteClickListener: (ProductCategory) -> Unit
        ){
            description.text = item.description
            quantity.text = item.quantity
            edit.setOnClickListener { editClickListener(item) }
            delete.setOnClickListener { deleteClickListener(item) }
        }


    }


}