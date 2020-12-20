package com.axelfernandez.deliverylavallevendedores.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.axelfernandez.deliverylavallevendedores.R
import com.axelfernandez.deliverylavallevendedores.adapter.ProductCategoryAdapter
import com.axelfernandez.deliverylavallevendedores.models.ProductCategory
import com.axelfernandez.deliverylavallevendedores.models.ProductCategoryRequest
import com.axelfernandez.deliverylavallevendedores.models.User
import com.axelfernandez.deliverylavallevendedores.utils.LoginUtils
import com.axelfernandez.deliverylavallevendedores.utils.TypeOfView
import com.axelfernandez.deliverylavallevendedores.utils.ViewUtil
import kotlinx.android.synthetic.main.fragment_category.view.*
import java.util.concurrent.atomic.AtomicBoolean

class CategoryFragment : Fragment() {

    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var categoryRv: RecyclerView
    private lateinit var user: User
    private var atomicBoolean = AtomicBoolean(false)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        categoryViewModel =
            ViewModelProviders.of(this).get(CategoryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_category, container, false)
        user =  LoginUtils.getUserFromSharedPreferences(requireContext())
        categoryRv = root.findViewById(R.id.category_rv) as RecyclerView
        categoryViewModel.solicitCategory(user.token)
        categoryViewModel.returnCategory().observe(viewLifecycleOwner, Observer {
            if (it == null){
                root.category_empty?.isVisible = true
                return@Observer
            }
            categoryRv.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL,false)
            val orderAdapter = ProductCategoryAdapter(it, {onEditSelected(it)}, {onDeleteSelected(it)})
            categoryRv.adapter = orderAdapter

        })
        root.category_add.setOnClickListener {
            findNavController(this).navigate(CategoryFragmentDirections.actionNavigationCategoryToAddCategory(null, TypeOfView.ADD))
        }
        categoryViewModel.returnResponseDeleted().observe(viewLifecycleOwner, Observer {
            if (atomicBoolean.compareAndSet(true,false)){
                ViewUtil.setSnackBar(root,R.color.orange,it)
                categoryViewModel.solicitCategory(user.token)
            }

        })
        return root
    }
    private fun onEditSelected(productCategory: ProductCategory){
        findNavController(this).navigate(CategoryFragmentDirections.actionNavigationCategoryToAddCategory(productCategory.description, TypeOfView.EDIT))

    }
    private fun onDeleteSelected(productCategory: ProductCategory){
        atomicBoolean.set(true)
        categoryViewModel.deleteCategory(user.token, ProductCategoryRequest(TypeOfView.DELETE.value, productCategory.description))
    }
}