package com.axelfernandez.deliverylavallevendedores.ui.meliLink

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.axelfernandez.deliverylavallevendedores.R
import com.axelfernandez.deliverylavallevendedores.models.MeliLink
import com.axelfernandez.deliverylavallevendedores.utils.LoginUtils
import com.axelfernandez.deliverylavallevendedores.utils.ViewUtil
import kotlinx.android.synthetic.main.meli_link_fragment.view.*

class MeliLinkFragment : Fragment() {

    companion object {
        fun newInstance() = MeliLinkFragment()
    }

    private lateinit var viewModel: MeliLinkViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.meli_link_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val view = view?:return
        val toolbar = view.findViewById(R.id.toolbar) as Toolbar
        toolbar.setNavigationIcon(R.drawable.ic_back_button)
        toolbar.setNavigationOnClickListener(View.OnClickListener { requireActivity().onBackPressed() })
        viewModel = ViewModelProvider(this).get(MeliLinkViewModel::class.java)
        val arguments = arguments ?: return
        val orderId = MeliLinkFragmentArgs.fromBundle(arguments).orderId
        val user = LoginUtils.getUserFromSharedPreferences(requireContext())
        viewModel.fetchMeliLink(orderId, user.token)
        viewModel.returnExistedMeliLink().observe(viewLifecycleOwner, Observer {
            val isAvailable = it.isAvailable ?: return@Observer
            if (isAvailable) {
                view.add_meli_link_label.setText( it.link?:return@Observer)
            }
        })

        view.send_meli_link_button.setOnClickListener {
            val link :String = view.add_meli_link_label.text.toString()
            val meliLink = MeliLink(null,orderId,link)
            viewModel.sendMeliLink(meliLink,user.token)
        }

        viewModel.returnConfirmation().observe(viewLifecycleOwner, Observer {
            if (it == null) {
                ViewUtil.setSnackBar(view,R.color.orange,"No se pudo enviar el link, Intentalo de nuevo")
                return@Observer
            }
            ViewUtil.setSnackBar(view,R.color.green,"Link enviado correctamente")
            findNavController().popBackStack()
        })
    }

}