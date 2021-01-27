package com.axelfernandez.deliverylavallevendedores.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.axelfernandez.deliverylavallevendedores.R

class StepAdapter(
    private val steps: List<String> = ArrayList(),
    private val context: Context
) : RecyclerView.Adapter<StepAdapter.StepViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return StepViewHolder(
            layoutInflater.inflate(
                R.layout.item_step_to_invoice,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return steps.size
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        val item: String = steps[position]
        holder.bind(item)
    }


    class StepViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var step: TextView = itemView.findViewById(R.id.item_step_invoice_text) as TextView

        fun bind(item: String){
            step.text = item
        }
    }


}