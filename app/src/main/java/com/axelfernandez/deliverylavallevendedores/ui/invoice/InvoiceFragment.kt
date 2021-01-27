package com.axelfernandez.deliverylavallevendedores.ui.invoice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.axelfernandez.deliverylavallevendedores.R
import com.axelfernandez.deliverylavallevendedores.adapter.InvoiceAdapter
import com.axelfernandez.deliverylavallevendedores.models.Invoice
import com.axelfernandez.deliverylavallevendedores.utils.LoginUtils
import kotlinx.android.synthetic.main.invoice_fragment.view.*

class InvoiceFragment : Fragment() {

    companion object {
        fun newInstance() = InvoiceFragment()
    }

    private lateinit var viewModel: InvoiceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.invoice_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val v = view ?: return
        viewModel = ViewModelProvider(this).get(InvoiceViewModel::class.java)
        val user = LoginUtils.getUserFromSharedPreferences(requireContext())
        viewModel.getInvoices(user.token)
        val toolbar = v.findViewById(R.id.toolbar) as Toolbar
        toolbar.setNavigationIcon(R.drawable.ic_back_button)
        toolbar.setNavigationOnClickListener(View.OnClickListener { requireActivity().onBackPressed() })
        val reviews = v.findViewById(R.id.invoice_rv) as RecyclerView
        viewModel.returnInvoices().observe(viewLifecycleOwner, Observer {
            if (it == null || it.isEmpty()) {
                v.no_invoices_layout.isVisible = true
                return@Observer
            }
            val animation: LayoutAnimationController =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_anim_fall_down)
            reviews.layoutAnimation = animation
            reviews.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            reviews.adapter = InvoiceAdapter(it, requireContext(), { onItemInvoiceClicked(it) })
        })
    }

    fun onItemInvoiceClicked(invoice: Invoice) {
        NavHostFragment.findNavController(this)
            .navigate(InvoiceFragmentDirections.actionInvoiceFragmentToInvoiceDetailFragment(invoice))
    }
}