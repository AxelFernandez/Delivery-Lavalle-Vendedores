package com.axelfernandez.deliverylavallevendedores.ui.closedOrders

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.axelfernandez.deliverylavallevendedores.R
import com.axelfernandez.deliverylavallevendedores.adapter.OrderAdapter
import com.axelfernandez.deliverylavallevendedores.models.Order
import com.axelfernandez.deliverylavallevendedores.models.User
import com.axelfernandez.deliverylavallevendedores.ui.home.HomeFragmentDirections
import com.axelfernandez.deliverylavallevendedores.utils.LoginUtils
import kotlinx.android.synthetic.main.closed_orders_fragment.view.*

class ClosedOrders : Fragment() {

    companion object {
        fun newInstance() = ClosedOrders()
    }

    private lateinit var viewModel: ClosedOrdersViewModel
    lateinit var closedRv : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.closed_orders_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ClosedOrdersViewModel::class.java)
        val v = view?:return
        closedRv = v.findViewById(R.id.closed_orders_rv) as RecyclerView
        val toolbar = v.findViewById(R.id.toolbar) as Toolbar
        toolbar.setNavigationIcon(R.drawable.ic_back_button)
        toolbar.setNavigationOnClickListener(View.OnClickListener { requireActivity().onBackPressed() })
        val user : User = LoginUtils.getUserFromSharedPreferences(requireContext())
        viewModel.solicitClosedOrders(user.token)
        viewModel.returnClosedPending().observe(viewLifecycleOwner, Observer {
            if (it == null){
                view?.closed_orders_empty?.isVisible = true
                return@Observer
            }
            closedRv.setHasFixedSize(true)
            closedRv.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL,false)
            val orderAdapter = OrderAdapter(it,requireContext(), R.color.grey) {onItemSelected(it)}
            closedRv.adapter = orderAdapter
        })

    }
    private fun onItemSelected(order : Order){
        NavHostFragment.findNavController(this).navigate(ClosedOrdersDirections.actionClosedOrdersToOrderDetail(order))
    }
}