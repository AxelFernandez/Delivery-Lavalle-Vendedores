package com.axelfernandez.deliverylavallevendedores.ui.addCategory

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.axelfernandez.deliverylavallevendedores.R
import com.axelfernandez.deliverylavallevendedores.models.ProductCategoryRequest
import com.axelfernandez.deliverylavallevendedores.utils.LoginUtils
import com.axelfernandez.deliverylavallevendedores.utils.TypeOfView
import kotlinx.android.synthetic.main.add_category_fragment.view.*

class AddCategory : Fragment() {

    companion object {
        fun newInstance() = AddCategory()
    }

    private lateinit var viewModel: AddCategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_category_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddCategoryViewModel::class.java)
        val view = view?:return
        val field : EditText = view.findViewById(R.id.add_category_field)
        val user = LoginUtils.getUserFromSharedPreferences(requireContext())
        val arguments = arguments?:return
        val typeOff = AddCategoryArgs.fromBundle(arguments).Type
        val description = AddCategoryArgs.fromBundle(arguments).description
        val categoryRequest = ProductCategoryRequest(typeOff.value)
        if (typeOff == TypeOfView.EDIT){
            val descriptionOld = description?:return
            categoryRequest.descriptionOld = descriptionOld
            field.setText(descriptionOld)
        }


        viewModel.returnCategory().observe(viewLifecycleOwner, Observer {
            findNavController(this).popBackStack()
        })
        view.save_category.setOnClickListener {
            if(field.text.isNullOrEmpty()){
                view.add_title_category.error = getString(R.string.required)
            }else{
                categoryRequest.description = field.text.toString()
                viewModel.postNewCategory(user.token, categoryRequest)
            }
        }
    }
}