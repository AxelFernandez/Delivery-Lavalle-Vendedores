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
import com.axelfernandez.deliverylavallevendedores.utils.ViewUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.crashlytics.FirebaseCrashlytics
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
        viewModel.getRepository(requireContext())
        val view = view?:return
        viewModel.getCompanyData()
        viewModel.getAccountDebit()
        viewModel.fetchPendingInvoices()
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
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.token_client_id))
                .requestEmail()
                .requestProfile()
                .build()
            val googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
            googleSignInClient.signOut().addOnSuccessListener {
              LoginUtils.removeUserData(requireContext())
                NavHostFragment.findNavController(this).navigate(ProfileFragmentDirections.actionNavigationProfileToLoginFragment2())
            }.addOnFailureListener {
                ViewUtil.setSnackBar(requireView(),R.color.orange,getString(R.string.we_have_a_problem_sign_out))
                FirebaseCrashlytics.getInstance().recordException(it)
            }
        }
    }

}