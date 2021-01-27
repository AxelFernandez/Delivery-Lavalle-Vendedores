package com.axelfernandez.deliverylavallevendedores.ui.invoiceDetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.axelfernandez.deliverylavallevendedores.R
import com.axelfernandez.deliverylavallevendedores.models.Invoice
import kotlinx.android.synthetic.main.invoice_detail_fragment.view.*


class InvoiceDetailFragment : Fragment() {

    companion object {
        fun newInstance() = InvoiceDetailFragment()
    }

    private lateinit var viewModel: InvoiceDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.invoice_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(InvoiceDetailViewModel::class.java)
        val view = view?:return
        val arguments = arguments?:return
        val toolbar = view.findViewById(R.id.toolbar) as Toolbar
        toolbar.setNavigationIcon(R.drawable.ic_back_button)
        toolbar.setNavigationOnClickListener(View.OnClickListener { requireActivity().onBackPressed() })
        val invoice : Invoice = InvoiceDetailFragmentArgs.fromBundle(arguments).invoice
        val hadMercadoPago = viewModel.isPackageInstalled(requireActivity().packageManager)
        if(hadMercadoPago){
            view.had_mercadoPago.isVisible = true
            view.send_invoice.text = "Pagar con Mercado Pago o Enviar Comprobante"
        }
        viewModel.bind(invoice, view, requireContext())



        view.send_invoice.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:axel.fernandez0145@gmail.com")
            var textDescription = "Elige tu app de correo"
            if (hadMercadoPago){
                textDescription = "Elige Mercado Pago si vas a enviar dinero, o Mail si vas a enviar el comprobante"
            }
            startActivity(Intent.createChooser(intent, textDescription))
        }
    }

}