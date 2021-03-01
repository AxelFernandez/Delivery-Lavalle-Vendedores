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
import com.axelfernandez.deliverylavallevendedores.models.ProductCategory
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
        viewModel.getRepository(requireContext())
        val view = view?:return
        val field : EditText = view.findViewById(R.id.add_category_field)
        val arguments = arguments?:return
        val prodCategory: ProductCategory = AddCategoryArgs.fromBundle(arguments).productCategory?: ProductCategory(description = null)
        prodCategory.let {
            field.setText(it.description)
        }
        viewModel.returnCategory().observe(viewLifecycleOwner, Observer {
            findNavController(this).popBackStack()
        })
        view.save_category.setOnClickListener {
            it.save_category.isEnabled = false
            if(field.text.isNullOrEmpty()){
                view.add_title_category.error = getString(R.string.required)
            }else{
                prodCategory.description = field.text.toString()
                viewModel.postNewCategory(prodCategory)
            }
        }
    }
}