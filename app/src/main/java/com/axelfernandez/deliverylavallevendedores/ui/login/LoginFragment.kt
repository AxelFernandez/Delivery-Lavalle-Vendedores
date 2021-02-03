package com.axelfernandez.deliverylavallevendedores.ui.login

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.axelfernandez.deliverylavallevendedores.HomeActivity
import com.axelfernandez.deliverylavallevendedores.R
import com.axelfernandez.deliverylavallevendedores.models.FirebaseToken
import com.axelfernandez.deliverylavallevendedores.ui.splash.SplashFragmentDirections
import com.axelfernandez.deliverylavallevendedores.utils.LoginUtils
import com.axelfernandez.deliverylavallevendedores.utils.TypeOfView
import com.axelfernandez.deliverylavallevendedores.utils.ViewUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.login_fragment.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {
    val RC_SIGN_IN: Int = 1
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions
    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v =  inflater.inflate(R.layout.login_fragment, container, false)

        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.token_client_id))
            .requestProfile()
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(activity?.application!!, mGoogleSignInOptions)

        v.google_button_sigin.setOnClickListener{
            signIn()
        }
        v.terms_and_conditions.movementMethod = LinkMovementMethod.getInstance();
        return v
    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
        view?.login_progress_bar?.visibility = View.VISIBLE

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val view = view ?: return
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val googleToken = account.idToken?:return
                viewModel.loginGetToken(googleToken)
                viewModel.returnToken().observe(viewLifecycleOwner, Observer {
                    if (it == null) {
                        ViewUtil.setSnackBar(
                            requireView(),
                            R.color.red,
                            getString(R.string.no_conection)
                        )
                        return@Observer
                    }
                    LoginUtils.putUserToSharedPreferences(requireContext(), it.user)
                    LoginUtils.saveSessionToken(it.access_token,requireContext())
                    LoginUtils.setIsLoginReady(requireContext(),it.completeRegistry)
                    fetchFirebase()
                    if (it.is_new || !it.completeRegistry) {
                        findNavController(this).navigate(LoginFragmentDirections.actionLoginFragmentToMapsFragment(TypeOfView.ADD))
                    } else {
                        val intent = Intent(context, HomeActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        activity?.finish()
                    }
                })

            } catch (e: ApiException) {
                Log.e(TAG,e.message!!)
            }
        }
    }

    private fun fetchFirebase() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(
            OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(
                        "TAG",
                        "Fetching FCM registration token failed",
                        task.exception
                    )
                    return@OnCompleteListener
                }
                val token = task.result
                val editor =
                    activity?.getSharedPreferences("userSession", Context.MODE_PRIVATE)
                        ?.edit() ?: return@OnCompleteListener
                editor.putString("token_firebase", token).apply()
                val user = LoginUtils.getUserFromSharedPreferences(requireContext())
                viewModel.sendFirebaseToken(user.token, FirebaseToken(token))
            })
    }
}