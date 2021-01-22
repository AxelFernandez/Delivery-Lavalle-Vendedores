package com.axelfernandez.deliverylavallevendedores.ui.sells

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.axelfernandez.deliverylavallevendedores.R
import com.github.mikephil.charting.data.Entry

class SellsFragment : Fragment() {

    companion object {
        fun newInstance() = SellsFragment()
    }
    // TODO: This View Must show Graphs, Like the most product selled a Chard (WIP)

    private lateinit var viewModel: SellsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.sells_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SellsViewModel::class.java)

        val entries : List<Entry> = ArrayList()
    }

}