package com.axelfernandez.deliverylavallevendedores.ui.map

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.axelfernandez.deliverylavallevendedores.BuildConfig
import com.axelfernandez.deliverylavallevendedores.R
import com.axelfernandez.deliverylavallevendedores.utils.LoginUtils
import com.axelfernandez.deliverylavallevendedores.utils.TypeOfView
import com.axelfernandez.deliverylavallevendedores.utils.ViewUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.app_bar.view.*
import kotlinx.android.synthetic.main.maps_fragment.view.*

class MapsFragment : Fragment() {

    companion object {
        fun newInstance() = MapsFragment()
    }

    private lateinit var viewModel: MapsViewModel
    private lateinit var mapWebView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.maps_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MapsViewModel::class.java)
        val view = view?:return
        val type = MapsFragmentArgs.fromBundle(arguments ?: return).type
        mapWebView = view.findViewById(R.id.webview) as WebView
        mapWebView.addJavascriptInterface(JavascriptInterface(requireContext()), "MyFunction");
        mapWebView.settings.setJavaScriptEnabled(true)
        mapWebView.loadUrl("file:///android_asset/maps.html");
        view.app_bar_1.text = getString(R.string.app_bar_1_maps)
        view.app_bar_2.text = getString(R.string.app_bar_2_maps)

        view.map_next_step.setOnClickListener {
            val limit = LoginUtils.getSaveInMapsLimits(requireContext())
            if (limit.isNullOrEmpty()){
                ViewUtil.setSnackBar(view, R.color.orange, "No has definido aun la zona")
            }else{
                findNavController(this).navigate(
                    MapsFragmentDirections.actionMapsFragmentToCompanyRegisterFragment(
                        limit,
                        type
                    )
                )
            }
        }

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val type = MapsFragmentArgs.fromBundle(arguments ?: return).type

                if (type == TypeOfView.ADD) {
                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(BuildConfig.tokenGoogleClient)
                        .requestEmail()
                        .requestProfile()
                        .build()
                    val googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
                    googleSignInClient.signOut().addOnSuccessListener {
                        findNavController(requireParentFragment()).popBackStack()
                    }
                }else{
                    findNavController(requireParentFragment()).popBackStack()
                }

            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }


    class JavascriptInterface(context: Context){
        private var mContext : Context = context

        @android.webkit.JavascriptInterface
        fun onButtonClick(toast: String) {
            LoginUtils.saveInMapLimits(toast, mContext)
        }
        @android.webkit.JavascriptInterface
        fun getLimitsSaved(): String? {
            return LoginUtils.getSaveInMapsLimits(mContext)?:return null
        }

    }

}

