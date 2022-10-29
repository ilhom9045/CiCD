package com.magic.alladin.core.view.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.magic.alladin.R
import com.magic.alladin.core.view.model.InfoDialogModel
import com.magic.alladin.core.view.model.InputDialogAmountModel
import com.magic.alladin.modules.records.view.adapter.RecordRecyclerView
import com.magic.alladin.modules.main.model.RecordsModel

class Dialog(private val context: Context) {

    private var dialog: Dialog? = null

    fun infoDialog(infoModel: InfoDialogModel) {
        val builder = AlertDialog.Builder(context)

        val v = LayoutInflater.from(context).inflate(R.layout.dialog_information, null)
        val imageView: ImageView = v.findViewById(R.id.imageView)
        val textViewTitle: TextView = v.findViewById(R.id.textViewTitle)
        val textView: TextView = v.findViewById(R.id.textView)
        val button: MaterialButton = v.findViewById(R.id.button)
        val buttonCancel: MaterialButton = v.findViewById(R.id.buttonCancel)

        imageView.setImageDrawable(infoModel.drawable)
        infoModel.title?.let {
            if (it.isEmpty()) return@let
            textViewTitle.visibility = View.VISIBLE
            textViewTitle.text = it
        }
        textView.text = infoModel.message
        button.text = infoModel.buttonText
        infoModel.cancelButtonText?.let {
            buttonCancel.isVisible = true
            buttonCancel.text = it
        }

        button.setOnClickListener {
            dialog?.dismiss()
            infoModel.action?.let {
                it()
            }
        }

        buttonCancel.setOnClickListener {
            dialog?.dismiss()
            infoModel.cancelAction?.let {
                it()
            }
        }

        builder.setView(v)
        builder.setCancelable(false)
        dialog = builder.create()
        dialogConig()
    }

    fun inputTextAmountDialog(model: InputDialogAmountModel) {
        val builder = AlertDialog.Builder(context)
        val v =
            LayoutInflater.from(context).inflate(R.layout.dialog_default_input_text_amount, null)
        val nameEditText: EditText = v.findViewById(R.id.editText)
        val button: MaterialButton = v.findViewById(R.id.button)
        val buttonCancel: MaterialButton = v.findViewById(R.id.buttonChangeUser)

        model.nameStr?.let {
            nameEditText.setText(it)
        }

        button.text = model.buttonText
        model.cancelButtonText?.let {
            buttonCancel.text = it
        }
        button.setOnClickListener {
            model.action?.let {
                val nameEdit: String = nameEditText.text.toString()
                model.action.invoke(nameEdit)
                dialog?.dismiss()
            }
        }
        buttonCancel.setOnClickListener {
            dialog?.dismiss()
            model.cancelAction?.let { it1 ->
                it1()
            }
        }
        builder.setView(v)
        builder.setCancelable(false)
        dialog = builder.create()
        dialogConig()
    }

    fun showNewRecordDialog(viewGroup: ViewGroup,onEnd:()->Unit) {
        val view =
            LayoutInflater.from(context).inflate(R.layout.heppy_dialog_view, viewGroup, false)
        val imageView:ImageView = view.findViewById(R.id.imageView)
        val anim: Animation = AnimationUtils.loadAnimation(context, R.anim.zoom_in)
        imageView.animation = anim
        anim.setAnimationListener(object :Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {}

            override fun onAnimationEnd(p0: Animation?) {
                onEnd.invoke()
                dialog?.dismiss()
            }

            override fun onAnimationRepeat(p0: Animation?) {}

        })
        imageView.startAnimation(anim)
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setView(view)

        alertDialog.setCancelable(false)
        dialog = alertDialog.create()
        dialogConig()
    }

    private fun dialogConig() {
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.show()
    }
}