package com.axelfernandez.deliverylavallevendedores.ui.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.axelfernandez.deliverylavallevendedores.api.Api
import com.axelfernandez.deliverylavallevendedores.api.RetrofitFactory
import com.axelfernandez.deliverylavallevendedores.models.FirebaseToken
import com.axelfernandez.deliverylavallevendedores.models.User
import com.axelfernandez.deliverylavallevendedores.models.UserResponse
import com.axelfernandez.deliverylavallevendedores.repository.LoginRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class LoginViewModel : ViewModel() {
    private lateinit var loginRepository :LoginRepository

    fun getRepository(context: Context) {
        loginRepository  = LoginRepository(RetrofitFactory.buildService(Api::class.java, context))
    }
    fun loginGetToken(userToken: String){
        loginRepository.getToken(userToken)
    }

    fun returnToken(): LiveData<UserResponse> {
        return loginRepository.returnData()
    }

    fun createUser(account: GoogleSignInAccount): User {

        return User(email = account.email!!,
            givenName = account.givenName!!,
            familyName = account.familyName!!,
            photo = account.photoUrl.toString().split('=').get(0),
            username = null,
            phone = null,
            token = ""
        )
    }

    fun sendFirebaseToken(firebaseToken: FirebaseToken) {
        loginRepository.sendToken(firebaseToken)

    }
}