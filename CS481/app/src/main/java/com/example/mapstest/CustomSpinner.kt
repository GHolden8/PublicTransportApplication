package com.example.mapstest

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatSpinner

class CustomSpinner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.spinnerStyle
) : AppCompatSpinner(context, attrs, defStyleAttr) {

    var onSpinnerEventsListener: OnSpinnerEventsListener? = null

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        onSpinnerEventsListener?.onSpinnerClosed()
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        if (hasWindowFocus) {
            post {
                onSpinnerEventsListener?.onSpinnerClosed()
            }
        }
    }

    override fun setSelection(position: Int, animate: Boolean) {
        val sameSelected = position == selectedItemPosition
        super.setSelection(position, animate)
        if (sameSelected) {
            onItemSelectedListener?.onItemSelected(this, selectedView, position, selectedItemId)
        }
    }

    override fun setSelection(position: Int) {
        val sameSelected = position == selectedItemPosition
        super.setSelection(position)
        if (sameSelected) {
            onItemSelectedListener?.onItemSelected(this, selectedView, position, selectedItemId)
        }
    }

    interface OnSpinnerEventsListener {
        fun onSpinnerClosed()
    }
}
