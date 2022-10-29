package com.magic.alladin.modules.records.view

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.magic.alladin.R
import com.magic.alladin.core.view.BaseFragmentWithVM
import com.magic.alladin.modules.main.view.MainActivity
import com.magic.alladin.modules.main.vm.MainViewModel
import com.magic.alladin.modules.records.view.adapter.RecordRecyclerView
import com.magic.alladin.modules.records.vm.RecordsFragmentViewModel

class RecordsFragment : BaseFragmentWithVM<RecordsFragmentViewModel>(
    RecordsFragmentViewModel::class.java,
    R.layout.fragment_records
) {
    private val sharedViewModel: MainViewModel by lazy {
        (requireActivity() as MainActivity).viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.init()
        toolbar.setTitle(getString(R.string.record))
        toolbar.setDisplayHomeEnable(true)
        toolbar.setTitleTextAppearance()
        toolbar.changeControlColor()

        val yourRecordsTextview: TextView = findViewById(R.id.yourRecordsTextview)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val notRecordsTextview: TextView = findViewById(R.id.notRecordsTextview)
        recyclerView.layoutManager = LinearLayoutManager(context)

        sharedViewModel.records.observe(viewLifecycleOwner) { records ->
            yourRecordsTextview.isVisible = records.isNotEmpty()
            notRecordsTextview.isVisible = records.isNullOrEmpty()
            recyclerView.adapter = RecordRecyclerView(records)
        }
    }
}