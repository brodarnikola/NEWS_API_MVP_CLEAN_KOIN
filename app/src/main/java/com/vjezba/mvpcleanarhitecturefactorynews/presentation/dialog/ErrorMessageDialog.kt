package com.vjezba.mvpcleanarhitecturefactorynews.presentation.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.vjezba.mvpcleanarhitecturefactorynews.R
import kotlinx.android.synthetic.main.dialog_error_message.*

class ErrorMessageDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.dialog_error_message, container, false)
        if (dialog != null && dialog?.window != null) {
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
            dialog?.setCanceledOnTouchOutside(false)
        }
        return view
    }

    override fun onStart() {
        super.onStart()

        tvOk.setOnClickListener {
            dialog?.dismiss()
        }
    }

}