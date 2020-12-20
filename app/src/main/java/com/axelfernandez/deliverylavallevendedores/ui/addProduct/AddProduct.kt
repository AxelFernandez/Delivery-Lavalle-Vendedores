package com.axelfernandez.deliverylavallevendedores.ui.addProduct

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.axelfernandez.deliverylavallevendedores.R
import com.axelfernandez.deliverylavallevendedores.utils.FileUtil
import com.axelfernandez.deliverylavallevendedores.utils.LoginUtils
import com.axelfernandez.deliverylavallevendedores.utils.TypeOfView
import kotlinx.android.synthetic.main.add_product_fragment.view.*
import kotlinx.android.synthetic.main.add_product_fragment.view.available_now
import java.io.File

class AddProduct : Fragment() {

    companion object {
        fun newInstance() = AddProduct()
        private val REQUEST_SELECT_IMAGE_IN_ALBUM = 1

    }

    private lateinit var viewModel: AddProductViewModel
    private lateinit var photoSelected: File
    private var isPhotoSelected: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_product_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddProductViewModel::class.java)
        val user = LoginUtils.getUserFromSharedPreferences(requireContext())
        val view = view?:return
        val arguments = arguments?:return
        val args = AddProductArgs.fromBundle(arguments)
        val type = args.type

        view.available_now.textOn = "Si"
        view.available_now.textOff = "No"
        view.available_now.isChecked = true
        viewModel.requestCategory(user.token)
        viewModel.returnProductsCategory().observe(viewLifecycleOwner, Observer {
            if(it == null){
                Toast.makeText(requireContext(),getString(R.string.no_conection),
                    Toast.LENGTH_SHORT).show()
                return@Observer
            }
            val spinner = view.findViewById(R.id.spinner_category_product) as Spinner
            val adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item, it)
            spinner.adapter = adapter
        })

        view.save_product.setOnClickListener {
            val hasFieldsWithErrors = viewModel.validateFields(view,requireContext())
            if(hasFieldsWithErrors || !isPhotoSelected){
                return@setOnClickListener
            }
            val product = viewModel.buildProduct(view)
            viewModel.addNewProduct(user.token,product,photoSelected)
        }

        view.product_add_photo.setOnClickListener{
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            checkStoragePermissions(requireActivity(),intent)
        }
        if (type == TypeOfView.EDIT){

        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val view = view?:return
        if (requestCode == REQUEST_SELECT_IMAGE_IN_ALBUM && resultCode == Activity.RESULT_OK) {
            val selectedFilename = data?.data //The uri with the location of the file
            if (selectedFilename != null) {
                photoSelected = File(FileUtil.getPath(selectedFilename,requireContext())?:return)
                photoSelected = FileUtil.resizeImage(photoSelected)
                isPhotoSelected = true
                view.product_add_photo.text = getString(R.string.photo_selected)
                view.product_image.setImageURI(selectedFilename)
                view.product_image.isVisible = true
            }
        }
    }
    private val REQUEST_EXTERNAL_STORAGE = 3
    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )


    private fun checkStoragePermissions(activity: Activity, intent: Intent?) {
        // Check if we have write permission
        val permission = ActivityCompat.checkSelfPermission(
            activity,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                activity,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            )

        } else {
            startActivityForResult(intent, REQUEST_SELECT_IMAGE_IN_ALBUM)
        }
    }
}