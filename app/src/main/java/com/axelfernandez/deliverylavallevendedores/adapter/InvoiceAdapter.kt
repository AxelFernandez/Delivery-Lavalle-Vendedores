package com.axelfernandez.deliverylavallevendedores.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.axelfernandez.deliverylavallevendedores.R
import com.axelfernandez.deliverylavallevendedores.component.TitleComponent
import com.axelfernandez.deliverylavallevendedores.models.Invoice

class InvoiceAdapter(

    private val invoices: List<Invoice> = ArrayList(),
    private val context: Context,
    private val itemClickListener: (Invoice) -> Unit
) : RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvoiceViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return InvoiceViewHolder(
            layoutInflater.inflate(
                R.layout.item_invoice,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return invoices.size
    }

    override fun onBindViewHolder(holder: InvoiceViewHolder, position: Int) {
        val item: Invoice = invoices[position]
        holder.bind(item, itemClickListener,context)
    }

    class InvoiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var period: TitleComponent = itemView.findViewById(R.id.item_invoice_period) as TitleComponent
        var state: TextView = itemView.findViewById(R.id.item_invoice_state) as TextView
        var mount: TextView = itemView.findViewById(R.id.item_invoice_mount) as TextView
        var layout: CardView = itemView.findViewById(R.id.item_invoice_card_view) as CardView

        fun bind(
            invoice: Invoice,
            itemClickListener: (Invoice) -> Unit,
            context: Context
        ) {

            period.title = context.getString(R.string.period_label, invoice.period)
            state.text = invoice.status
            mount.text = context.getString(R.string.detail_order_total, invoice.mount.toString())
            layout.setOnClickListener { itemClickListener(invoice) }

        }
    }

}


