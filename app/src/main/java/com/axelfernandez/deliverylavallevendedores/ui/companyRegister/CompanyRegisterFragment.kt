package com.axelfernandez.deliverylavallevendedores.ui.companyRegister

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.axelfernandez.deliverylavallevendedores.HomeActivity
import com.axelfernandez.deliverylavallevendedores.R
import com.axelfernandez.deliverylavallevendedores.models.Company
import com.axelfernandez.deliverylavallevendedores.utils.FileUtil
import com.axelfernandez.deliverylavallevendedores.utils.LoginUtils
import com.axelfernandez.deliverylavallevendedores.utils.ViewUtil
import kotlinx.android.synthetic.main.company_register_fragment.view.*
import java.io.File


class CompanyRegisterFragment : Fragment() {

    companion object {
        fun newInstance() = CompanyRegisterFragment()
        private val REQUEST_SELECT_IMAGE_IN_ALBUM = 1
    }

    private lateinit var viewModel: CompanyRegisterViewModel
    private lateinit var photoSelected: File
    private lateinit var company: Company
    private var isPhotoSelected: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.company_register_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CompanyRegisterViewModel::class.java)
        val user = LoginUtils.getUserFromSharedPreferences(requireContext())
        val view = view?:return
        val arguments = arguments?:return
        view.available_now.textOn = "Si"
        view.available_now.textOff = "No"
        view.available_now.isChecked = true
        viewModel.requestCategories(user.token)
        viewModel.returnCategories().observe(viewLifecycleOwner, Observer {
            if(it == null){
                Toast.makeText(requireContext(),getString(R.string.no_conection),
                    Toast.LENGTH_SHORT).show()
                return@Observer
            }
            val spinner = view.findViewById(R.id.spinner_category) as Spinner
            val adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item, it)
            spinner.adapter = adapter
        })

        view.company_register_add_photo.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            checkStoragePermissions(requireActivity(),intent)
        }

        view.save_company.setOnClickListener {
            val hasFieldsWithErrors = viewModel.validateFields(view,requireContext())
            if(hasFieldsWithErrors && !isPhotoSelected){
                return@setOnClickListener
            }
            val user = LoginUtils.getUserFromSharedPreferences(requireContext())
            val limits = arguments.getString(getString(R.string.arguments_limits))?:return@setOnClickListener
            company = viewModel.buildCompany(view,requireContext(),limits)
            viewModel.registryCompany(user.token, company, photoSelected, limits)
            view.progress_bar_registry.isVisible = true
        }

        viewModel.returnCompanyRegistry().observe(viewLifecycleOwner, Observer {
            if(it == null){
                Toast.makeText(requireContext(),getString(R.string.no_conection),
                    Toast.LENGTH_SHORT).show()
                return@Observer
            }
            company.id = it
            LoginUtils.saveDefaultCompany(requireContext(),company)

            val intent = Intent(context, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            activity?.finish()


        })
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val view = view?:return
        val contentResolver = activity?.contentResolver?:return
        if (requestCode == REQUEST_SELECT_IMAGE_IN_ALBUM && resultCode == RESULT_OK) {
            val selectedFilename = data?.data //The uri with the location of the file
            if (selectedFilename != null) {
                photoSelected = File(FileUtil.getPath(selectedFilename,requireContext())?:return)
                photoSelected = FileUtil.resizeImage(photoSelected)
                isPhotoSelected = true
                view.company_register_add_photo.text = getString(R.string.photo_selected)
                view.company_image.setImageURI(selectedFilename)
                view.company_image.isVisible = true
            }
        }
    }
    private val REQUEST_EXTERNAL_STORAGE = 3
    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )


    fun checkStoragePermissions(activity: Activity, intent: Intent?) {
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