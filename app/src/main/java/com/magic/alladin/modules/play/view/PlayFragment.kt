package com.magic.alladin.modules.play.view

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.magic.alladin.R
import com.magic.alladin.core.view.BaseFragment
import com.magic.alladin.core.view.dialog.Dialog
import com.magic.alladin.core.view.model.InfoDialogModel
import com.magic.alladin.core.view.model.InputDialogAmountModel
import com.magic.alladin.modules.main.model.RecordsModel
import com.magic.alladin.modules.main.view.MainActivity
import com.magic.alladin.modules.main.vm.MainViewModel
import com.magic.alladin.modules.play.model.PlayModel
import com.magic.alladin.modules.play.view.adapter.PlayAdapter
import com.magic.alladin.modules.play.vm.PlayViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlin.properties.Delegates.notNull

class PlayFragment : BaseFragment(R.layout.play), PlayAdapter.Listener {

    private var recyclerView: RecyclerView by notNull()
    private var countText: TextView by notNull()

    protected val shredViewModel: MainViewModel by lazy {
        (requireActivity() as MainActivity).viewModel
    }

    private var adapter: PlayAdapter? = null

    private val viewModel: PlayViewModel by lazy {
        ViewModelProvider(
            this,
            PlayViewModel.viewModelFactory(requireContext())
        )[PlayViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListener()
        initObservers(savedInstanceState)
    }

    private fun initObservers(savedInstanceState: Bundle?) {
        viewModel.items.observe(viewLifecycleOwner) {
            adapter = PlayAdapter(it, this)
            recyclerView.adapter = adapter
        }
        lifecycleScope.launchWhenStarted {
            viewModel.allSuccess.collectLatest {
                Dialog(requireContext()).showNewRecordDialog(requireActivity().window.decorView.rootView as ViewGroup) {
                    createDialog(shredViewModel.isRecord(it), it)
                }
            }
        }
        viewModel.counter.observe(viewLifecycleOwner) {
            countText.text = it.toString()
        }
        viewModel.loadCards(savedInstanceState == null)
    }

    private fun initListener() {
        recyclerView.layoutManager =
            GridLayoutManager(requireContext(), 4, GridLayoutManager.VERTICAL, false)
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.playRecyclerView)
        countText = findViewById(R.id.countText)
        toolbar.init()
        toolbar.setTitle(null)
        toolbar.setDisplayHomeEnable(true)
        toolbar.setTitleTextAppearance()
        toolbar.changeControlColor()
    }

    override fun onItemClick(item: PlayModel) {
        viewModel.check(item)
    }

    private fun createDialog(isRecord: Boolean, count: Int) {
        infoDialog(count)
        if (isRecord) {
            Dialog(requireContext()).inputTextAmountDialog(InputDialogAmountModel(
                buttonText = getString(R.string.saveName),
                action = {
                    it?.let {
                        shredViewModel.saveNewRecord(RecordsModel(it, count))
                    }
                }
            ))
        }
    }

    fun infoDialog(count: Int) {
        Dialog(requireContext()).infoDialog(InfoDialogModel(
            message = count.toString(),
            buttonText = getString(R.string.restart),
            cancelButtonText = getString(R.string.exit),
            cancelAction = {
                requireActivity().onBackPressed()
            },
            action = {
                viewModel.cleanAll()
            }
        ))
    }

}