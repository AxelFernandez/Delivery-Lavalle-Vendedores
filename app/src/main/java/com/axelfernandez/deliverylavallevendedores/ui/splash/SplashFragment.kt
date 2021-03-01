package com.axelfernandez.deliverylavallevendedores.ui.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.axelfernandez.deliverylavallevendedores.BuildConfig
import com.axelfernandez.deliverylavallevendedores.HomeActivity
import com.axelfernandez.deliverylavallevendedores.R
import com.axelfernandez.deliverylavallevendedores.api.Api
import com.axelfernandez.deliverylavallevendedores.api.RetrofitFactory
import com.axelfernandez.deliverylavallevendedores.models.User
import com.axelfernandez.deliverylavallevendedores.repository.LoginRepository
import com.axelfernandez.deliverylavallevendedores.utils.LoginUtils
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import java.lang.Exception

class SplashFragment : Fragment() {

    companion object {
        fun newInstance() =
            SplashFragment()
    }

    private lateinit var viewModel: SplashViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        FirebaseCrashlytics.getInstance().setCustomKey("environment", BuildConfig.BUILD_TYPE)
        return inflater.inflate(R.layout.splash_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        viewModel.getRepository(requireContext())
        val isLoginReady = LoginUtils.getIsLoginReady(requireContext())
        var remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (isLoginReady) {
                    val googleSignInAccount = GoogleSignIn.getLastSignedInAccount(requireContext())
                    if (googleSignInAccount?.idToken != null) {
                        viewModel.loginGoogle(googleSignInAccount)
                    }else{
                        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(BuildConfig.tokenGoogleClient)
                            .requestEmail()
                            .requestProfile()
                            .build()
                        val googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
                        googleSignInClient.silentSignIn().addOnCompleteListener {
                            val account: GoogleSignInAccount? = it.result
                            viewModel.loginGoogle(account?:return@addOnCompleteListener)
                        }
                    }

                    viewModel.returnData().observe(viewLifecycleOwner, Observer {
                        if (it == null) {
                            findNavController(this).navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
                            return@Observer
                        }
                        val user: User = LoginUtils.getUserFromSharedPreferences(requireContext())
                        user.token = it.access_token
                        LoginUtils.setIsLoginReady(requireContext(), it.completeRegistry)
                        LoginUtils.putUserToSharedPreferences(requireContext(), user)

                        val intent = Intent(context, HomeActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        activity?.finish()
                    })
                } else {
                    //RedirectToLogin
                    findNavController(this).navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
                }
            }
    }

}