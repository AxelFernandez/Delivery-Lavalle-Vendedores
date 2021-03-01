package com.axelfernandez.deliverylavallevendedores.ui.products

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
import com.axelfernandez.deliverylavallevendedores.adapter.ProductAdapter
import com.axelfernandez.deliverylavallevendedores.models.Product
import com.axelfernandez.deliverylavallevendedores.utils.TypeOfView
import com.axelfernandez.deliverylavallevendedores.utils.ViewUtil
import kotlinx.android.synthetic.main.fragment_products.view.*

class ProductsFragment : Fragment() {

    private lateinit var productsViewModel: ProductsViewModel
    private lateinit var productRv: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        productsViewModel =
            ViewModelProviders.of(this).get(ProductsViewModel::class.java)
        productsViewModel.getRepository(requireContext())
        val root = inflater.inflate(R.layout.fragment_products, container, false)
        var hadNoCategories : Boolean = false
        productRv = root.findViewById(R.id.products_rv) as RecyclerView

        productsViewModel.solicitProduct()
        productsViewModel.getCategories()


        productsViewModel.returnCategories().observe(viewLifecycleOwner, Observer {
            if (it == null || it.isEmpty()){
                hadNoCategories = true
                return@Observer
            }
        })
        productsViewModel.returnProducts().observe(viewLifecycleOwner, Observer {
            if (it == null){
                ViewUtil.setSnackBar(root,R.color.orange,getString(R.string.server_error))
                return@Observer
            }
            if (it.isEmpty()){
                root.product_empty?.isVisible = true
            }
            productRv.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL,false)
            val productAdapter = ProductAdapter(it,requireContext(), {onEditSelected(it)}, {onDeleteSelected(it)})
            productRv.adapter = productAdapter
            root.progress_bar_product.isVisible = false

        })
        productsViewModel.returnConfirmDeleted().observe(viewLifecycleOwner, Observer {
            if (it == null){
                ViewUtil.setSnackBar(root,R.color.orange, getString(R.string.no_conection))
                return@Observer
            }
            ViewUtil.setSnackBar(root,R.color.green, getString(R.string.deleted))
            productsViewModel.solicitProduct()
        })
        root.product_add.setOnClickListener {
            if (hadNoCategories){
                ViewUtil.setSnackBar(root,R.color.orange, "No hay categorias, debes crear una antes de continuar")
                return@setOnClickListener
            }
            findNavController(this).navigate(ProductsFragmentDirections.actionNavigationProductsToAddProduct(null))
        }
        return root
    }
    private fun onEditSelected(product: Product){
        findNavController(this).navigate(ProductsFragmentDirections.actionNavigationProductsToAddProduct(product))

    }
    private fun onDeleteSelected(product: Product){
        productsViewModel.deleteProduct(product)
    }
}