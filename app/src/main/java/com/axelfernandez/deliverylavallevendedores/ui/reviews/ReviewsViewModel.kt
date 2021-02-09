package com.axelfernandez.deliverylavallevendedores.ui.reviews

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.axelfernandez.deliverylavallevendedores.api.Api
import com.axelfernandez.deliverylavallevendedores.api.RetrofitFactory
import com.axelfernandez.deliverylavallevendedores.models.Company
import com.axelfernandez.deliverylavallevendedores.models.Review
import com.axelfernandez.deliverylavallevendedores.repository.OrderRepository
import kotlinx.android.synthetic.main.reviews_fragment.view.*

class ReviewsViewModel : ViewModel() {

	private lateinit var orderRepository :OrderRepository

	fun getRepository(context: Context) {
		orderRepository = OrderRepository(RetrofitFactory.buildService(Api::class.java, context))
	}

	fun bind(view : View, company: Company){
		view.company_rating.text = company.rating.toString()
		view.company_rating.isEnabled = false

	}

	fun getReviews( idCompany:String){
		orderRepository.getReviews(idCompany)
	}

	fun returnReviews(): LiveData<List<Review>> {
		return orderRepository.returnReviews()
	}
}