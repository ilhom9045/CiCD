package com.magic.alladin.modules.main.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.magic.alladin.R
import com.magic.alladin.core.view.BaseFragment
import com.magic.alladin.modules.info.view.InfoFragment
import com.magic.alladin.modules.play.view.PlayFragment
import com.magic.alladin.modules.records.view.RecordsFragment

class MainFragment : BaseFragment(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findViewById<Button>(R.id.start).setOnClickListener {
            transaction(fragment = PlayFragment())
        }

        findViewById<Button>(R.id.record).setOnClickListener {
            transaction(fragment = RecordsFragment())
        }

        findViewById<Button>(R.id.info).setOnClickListener {
            transaction(fragment = InfoFragment())
        }
    }
}