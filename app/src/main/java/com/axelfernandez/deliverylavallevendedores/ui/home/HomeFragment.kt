package com.axelfernandez.deliverylavallevendedores.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.axelfernandez.deliverylavallevendedores.R
import com.axelfernandez.deliverylavallevendedores.adapter.OrderAdapter
import com.axelfernandez.deliverylavallevendedores.models.Order
import com.axelfernandez.deliverylavallevendedores.utils.LoginUtils
import com.axelfernandez.deliverylavallevendedores.utils.ViewUtil
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    lateinit var orderAdapter : OrderAdapter
    lateinit var pendingRv : RecyclerView
    lateinit var progressrv : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        pendingRv = root.findViewById(R.id.rv_pending) as RecyclerView
        progressrv = root.findViewById(R.id.rv_in_process) as RecyclerView

        val user = LoginUtils.getUserFromSharedPreferences(requireContext())
        homeViewModel.solicitPendingOrders(user.token)
        homeViewModel.solicitInProgressOrders(user.token)

        homeViewModel.returnPendingOrders().observe(viewLifecycleOwner, Observer {
            if(it == null){
                ViewUtil.setSnackBar(root,R.color.red,getString(R.string.no_conection))
                return@Observer
            }
            pendingRv.setHasFixedSize(true)
            pendingRv.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL,false)
            orderAdapter = OrderAdapter(it,requireContext(), R.color.red) {onItemSelected(it)}
            pendingRv.adapter = orderAdapter
        })

        homeViewModel.returnInProgressOrders().observe(viewLifecycleOwner, Observer {
            if(it == null){
                ViewUtil.setSnackBar(root,R.color.red,getString(R.string.no_conection))
                return@Observer
            }
            progressrv.setHasFixedSize(true)
            progressrv.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL,false)
            orderAdapter = OrderAdapter(it,requireContext(),R.color.blue) {onItemSelected(it)}
            progressrv.adapter = orderAdapter
        })
        root.button_orders_closed.setOnClickListener {
            findNavController(this).navigate(HomeFragmentDirections.actionNavigationHomeToClosedOrders())
        }

        return root
    }

    private fun onItemSelected(order : Order){
        findNavController(this).navigate(HomeFragmentDirections.actionNavigationHomeToOrderDetail(order))
    }

}