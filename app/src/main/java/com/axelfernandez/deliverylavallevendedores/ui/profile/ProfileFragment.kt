package com.axelfernandez.deliverylavallevendedores.ui.profile

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.axelfernandez.deliverylavallevendedores.R
import com.axelfernandez.deliverylavallevendedores.utils.LoginUtils
import com.axelfernandez.deliverylavallevendedores.utils.TypeOfView
import kotlinx.android.synthetic.main.profile_fragment.view.*
import kotlinx.android.synthetic.main.reviews_fragment.view.*

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        val view = view?:return
        val user = LoginUtils.getUserFromSharedPreferences(requireContext())
        viewModel.getCompanyData(user.token)
        viewModel.getAccountDebit(user.token)
        viewModel.fetchPendingInvoices(user.token)
        viewModel.returnData().observe(viewLifecycleOwner, Observer {
            viewModel.bind(it,requireContext(),view)
            LoginUtils.saveDefaultCompany(requireContext(),it)
            LoginUtils.saveInMapLimits(it.limits,requireContext())
        })

        viewModel.returnAccountDebit().observe(viewLifecycleOwner, Observer {
            view.usage_detail.text = getString(R.string.usage_label,it)
        })

        viewModel.returnFetchPendingInvoices().observe(viewLifecycleOwner, Observer {
            if (it == null){
                return@Observer
            }
            if (it == true){
                view.invoice_pending.isVisible = true
            }
        })

        view.settings_update.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(ProfileFragmentDirections.actionNavigationProfileToMapsFragment(TypeOfView.EDIT))
        }

        view.settings_sells.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(ProfileFragmentDirections.actionNavigationProfileToClosedOrders())
        }
        view.settings_score.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(ProfileFragmentDirections.actionNavigationProfileToReviewsFragment())
        }
        view.settings_usage.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(ProfileFragmentDirections.actionNavigationProfileToInvoiceFragment())
        }
        view.settings_logout.setOnClickListener {
            LoginUtils.removeUserData(requireContext())
            NavHostFragment.findNavController(this).navigate(ProfileFragmentDirections.actionNavigationProfileToLoginFragment2())
        }
    }

}