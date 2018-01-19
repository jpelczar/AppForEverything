package io.jpelczar.appforeverything.module.datacollection.cell


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.jpelczar.appforeverything.R
import io.jpelczar.appforeverything.core.BaseFragment

class CellDataCollectionFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cell_data_collection, container, false)
    }
}