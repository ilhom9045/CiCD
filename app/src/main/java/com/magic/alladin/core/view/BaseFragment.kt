package com.magic.alladin.core.view

import android.view.MenuItem
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.magic.alladin.R
import com.magic.alladin.core.view.toolbar.ToolbarUtil

abstract class BaseFragment(@LayoutRes private val layout: Int) : Fragment(layout) {

    protected val toolbar by lazy {
        setHasOptionsMenu(true)
        ToolbarUtil(requireView(), requireActivity() as AppCompatActivity)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                requireActivity().onBackPressed()
                true
            }
            else -> {
                false
            }
        }
    }

    protected fun transaction(
        container_id: Int = R.id.fragment_container,
        fragment: Fragment,
        addToBackStack: Boolean = true,
        tag: String? = null
    ) {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(container_id, fragment, tag)
            if (addToBackStack) {
                addToBackStack(tag)
            }
            commit()
        }
    }

    protected fun <T : View> findViewById(@IdRes id: Int): T {
        return requireView().findViewById(id)
    }
}