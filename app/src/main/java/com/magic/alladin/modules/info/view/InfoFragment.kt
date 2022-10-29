package com.magic.alladin.modules.info.view

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import com.magic.alladin.R
import com.magic.alladin.core.extention.margins
import com.magic.alladin.core.view.BaseFragment

class InfoFragment : BaseFragment(R.layout.fragment_info) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.init()
        toolbar.setTitle(getString(R.string.info))
        toolbar.setDisplayHomeEnable(true)
        toolbar.setTitleTextAppearance()
        toolbar.changeControlColor()


        findViewById<LinearLayout>(R.id.linearLayout).margins(bottom = navigationSize())

    }

    fun navigationSize(): Int {
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

}