package com.axelfernandez.deliverylavallevendedores.ui.orderDetail

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.axelfernandez.deliverylavallevendedores.R
import com.axelfernandez.deliverylavallevendedores.models.Order
import com.axelfernandez.deliverylavallevendedores.ui.companyRegister.CompanyRegisterFragment
import com.axelfernandez.deliverylavallevendedores.ui.home.HomeFragmentDirections
import com.axelfernandez.deliverylavallevendedores.utils.LoginUtils
import com.axelfernandez.deliverylavallevendedores.utils.ViewUtil
import kotlinx.android.synthetic.main.order_detail_fragment.view.*


class OrderDetail : Fragment() {

    companion object {
        fun newInstance() = OrderDetail()
    }
    private val REQUEST_CALL_PHONE = 3
    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.CALL_PHONE
    )
    private lateinit var viewModel: OrderDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.order_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(OrderDetailViewModel::class.java)
        val arguments =  arguments?:return
        val view = view?:return
        val user = LoginUtils.getUserFromSharedPreferences(requireContext())
        val toolbar = view.findViewById(R.id.toolbar) as Toolbar
        toolbar.setNavigationIcon(R.drawable.ic_back_button)
        toolbar.setNavigationOnClickListener(View.OnClickListener { requireActivity().onBackPressed() })
        val order: Order = OrderDetailArgs.fromBundle(arguments).argumentOrder
        viewModel.bind(order,view,requireContext())
        viewModel.fetchOrder(order.id, user.token)
        viewModel.returnDataAndBuild().observe(viewLifecycleOwner, Observer {
            viewModel.bind(order,view,requireContext())
        })
        viewModel.returnNewState().observe(viewLifecycleOwner, Observer {
            viewModel.bind(it,view,requireContext())
            ViewUtil.setSnackBar(requireView(), R.color.orange, getString(R.string.state_changed))
        })
        view.detail_order_show_location.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(OrderDetailDirections.actionOrderDetailToMapsLocation(order.address))
        }
        view.detail_order_call_client.setOnClickListener {
            checkPhonePermissions(requireActivity(),order)
        }
        view.detail_order_changeState.setOnClickListener {
            viewModel.setNewState(order.id, user.token)
        }
        view.detail_order_cancel_order.setOnClickListener {
            viewModel.cancelOrder(order.id, user.token)
        }

    }

    private fun checkPhonePermissions(activity: Activity, order: Order) {
        // Check if we have write permission
        val permission = ActivityCompat.checkSelfPermission(
            activity,
            Manifest.permission.CALL_PHONE
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                activity,
                PERMISSIONS_STORAGE,
                REQUEST_CALL_PHONE
            )
        } else {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + order.client.phone))
            startActivity(intent)
        }
    }

}