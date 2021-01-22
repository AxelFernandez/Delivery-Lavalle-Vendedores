package com.axelfernandez.deliverylavallevendedores.ui.reviews

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.Toolbar
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.axelfernandez.deliverylavallevendedores.R
import com.axelfernandez.deliverylavallevendedores.adapter.ReviewAdapter
import com.axelfernandez.deliverylavallevendedores.utils.LoginUtils
import kotlinx.android.synthetic.main.reviews_fragment.view.*

class ReviewsFragment : Fragment() {

	companion object {
		fun newInstance() = ReviewsFragment()
	}

	private lateinit var viewModel: ReviewsViewModel

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.reviews_fragment, container, false)
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		viewModel = ViewModelProvider(this).get(ReviewsViewModel::class.java)
		val arguments = arguments?:return
		val v = view?:return
		val company = LoginUtils.getDefaultCompany(requireContext())
		val toolbar = v.findViewById(R.id.toolbar) as Toolbar
		val user = LoginUtils.getUserFromSharedPreferences(requireContext())
		val reviews = v.findViewById(R.id.reviews_rv) as RecyclerView
		toolbar.setNavigationIcon(R.drawable.ic_back_button)
		toolbar.setNavigationOnClickListener(View.OnClickListener { requireActivity().onBackPressed() })
		viewModel.bind(v,company)
		viewModel.getReviews(user.token, company.id?:return)
		viewModel.returnReviews().observe(viewLifecycleOwner, Observer {
			if (it == null){
				v.no_review_layout.isVisible = true
				return@Observer
			}
			val animation: LayoutAnimationController =
				AnimationUtils.loadLayoutAnimation(context, R.anim.layout_anim_fall_down)
			reviews.layoutAnimation = animation
			reviews.layoutManager =  LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
			reviews.adapter = ReviewAdapter(it)
		})

	}

}