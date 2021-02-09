package com.axelfernandez.deliverylavallevendedores.ui.splash

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.axelfernandez.deliverylavallevendedores.api.Api
import com.axelfernandez.deliverylavallevendedores.api.RetrofitFactory
import com.axelfernandez.deliverylavallevendedores.models.UserResponse
import com.axelfernandez.deliverylavallevendedores.repository.LoginRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class SplashViewModel : ViewModel() {


    private lateinit var login : LoginRepository

    fun getRepository(context: Context) {
        login= LoginRepository(RetrofitFactory.buildService(Api::class.java, context))
    }

    fun loginGoogle(account : GoogleSignInAccount){
        login.getToken(account.idToken?:return)
    }
    fun returnData(): LiveData<UserResponse> {
        return login.returnData()
    }
}