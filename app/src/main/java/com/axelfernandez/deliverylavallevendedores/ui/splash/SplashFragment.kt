package com.axelfernandez.deliverylavallevendedores.ui.splash

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.axelfernandez.deliverylavallevendedores.api.Api
import com.axelfernandez.deliverylavallevendedores.api.RetrofitFactory
import com.axelfernandez.deliverylavallevendedores.MainActivity
import com.axelfernandez.deliverylavallevendedores.R
import com.axelfernandez.deliverylavallevendedores.models.User
import com.axelfernandez.deliverylavallevendedores.repository.LoginRepository
import com.axelfernandez.deliverylavallevendedores.utils.LoginUtils
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

class SplashFragment : Fragment() {

    companion object {
        fun newInstance() =
            SplashFragment()
    }

    private lateinit var viewModel: SplashViewModel
    val login = LoginRepository(RetrofitFactory.buildService(Api::class.java))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.splash_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        var editor = requireContext().getSharedPreferences("userSession", Context.MODE_PRIVATE)
        val is_login_ready = editor.getBoolean(getString(R.string.is_login_ready),false)
        var remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    Log.d("4", "Config params updated: $updated")

                } else {
                    Log.e("TAG", "Can't Connect to Firebase")
                }
                if(is_login_ready){
                    login.getToken(LoginUtils.getUserFromSharedPreferences(requireContext()))
                    login.returnData().observe(viewLifecycleOwner, Observer {
                        if(it == null){
                            Toast.makeText(requireContext(),"No hay conexion a internet, intentalo de nuevo mas tarde",
                                Toast.LENGTH_SHORT).show()
                        }
                        val it = it?:return@Observer
                        val user : User = LoginUtils.getUserFromSharedPreferences(requireContext())
                        user.token = it.access_token
                        LoginUtils.putUserToSharedPreferences(requireContext(),user)
                        TODO("RedirectTo Home")
                    })
                }else{
                    //RedirectToLogin
                    findNavController(this).navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())

//                    val intent = Intent(requireContext(), MainActivity::class.java)
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
//                    startActivity(intent)
//                    requireActivity().finish()
                }
            }    }

}