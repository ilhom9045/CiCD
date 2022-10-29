package com.magic.alladin.modules.main.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.magic.alladin.R
import com.magic.alladin.core.view.BaseActivity
import com.magic.alladin.modules.main.vm.MainViewModel

class MainActivity : BaseActivity(R.layout.activity_main) {

    val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, MainViewModel.viewModelFactory(this))[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}