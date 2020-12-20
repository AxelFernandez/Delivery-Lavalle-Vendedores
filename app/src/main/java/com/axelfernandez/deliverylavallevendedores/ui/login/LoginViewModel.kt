package com.axelfernandez.deliverylavallevendedores.ui.login

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
    private val loginRepository = LoginRepository(RetrofitFactory.buildService(Api::class.java))

    fun loginGetToken(user: User){
        loginRepository.getToken(user)
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

    fun sendFirebaseToken(token: String, firebaseToken: FirebaseToken) {
        loginRepository.sendToken(token, firebaseToken)

    }
    // TODO: Implement the ViewModel
}