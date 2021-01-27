package com.axelfernandez.deliverylavallevendedores.ui.invoiceDetail

import android.content.Context
import android.content.pm.PackageManager
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.axelfernandez.deliverylavallevendedores.R
import com.axelfernandez.deliverylavallevendedores.adapter.StepAdapter
import com.axelfernandez.deliverylavallevendedores.models.Invoice
import kotlinx.android.synthetic.main.invoice_detail_fragment.view.*


class InvoiceDetailViewModel : ViewModel() {

    fun bind(invoice: Invoice, view: View, context: Context) {
        view.invoice_detail_period.title =
            context.getString(R.string.invoice_detail_period, invoice.period)

        view.invoice_detail_state.text =
            context.getString(R.string.invoice_detail_status, invoice.status)

        view.invoice_detail_mount.text =
            context.getString(R.string.invoice_detail_mount, invoice.mount.toString())

        var meaning = context.getString(R.string.invoice_detail_meaning_payed)
        if (invoice.status == context.getString(R.string.pending)) {
            meaning = context.getString(R.string.invoice_detail_meaning_pending)
            val steps = view.findViewById(R.id.invoice_detail_rv) as RecyclerView
            steps.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            steps.adapter = StepAdapter(getSteps(), context)
            view.layout_step_to_follow.isVisible = true
            view.send_invoice.isVisible = true
        }else{
            view.had_mercadoPago.isVisible = false

        }
        view.invoice_detail_description.text = meaning
    }

    fun isPackageInstalled(
        packageManager: PackageManager,
        packageName: String = "com.mercadopago.wallet"
    ): Boolean {
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    private fun getSteps():ArrayList<String>{
        val result = ArrayList<String>()
        result.add("Puedes realizar un envio de dinero mediante Mercado Pago con el monto a este mail: axel.fernandez0145@gmail.com")
        result.add("Tambien puedes realizar una Transferencia Bancaria a este Alias: Axel-Fernandez")
        result.add("Luego envia el comprobante a mi mail con el boton de abajo")
        result.add("Revisaremos el comprobante y acreditaremos el pago")
        return result
    }
}