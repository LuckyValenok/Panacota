package net.panacota.app.ui.dialogs

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import net.panacota.app.R

abstract class FullScreenDialog : DialogFragment() {
    override fun onStart() {
        super.onStart()
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog!!.window!!.setLayout(width, height)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_Panacota_FullScreenDialog)
    }
}