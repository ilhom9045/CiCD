package com.magic.alladin.core.view

import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.magic.alladin.core.vm.BaseViewModel

abstract class BaseFragmentWithVM<T : BaseViewModel>(
    clazz: Class<T>,
    @LayoutRes private val layout: Int
) :
    BaseFragment(layout) {

    protected val viewModel by lazy {
        ViewModelProvider(this)[clazz]
    }

}
