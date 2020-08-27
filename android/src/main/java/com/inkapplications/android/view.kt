package com.inkapplications.android

import android.os.Handler
import android.os.Looper
import android.view.View

private val MAIN_HANDLER = Handler(Looper.getMainLooper())

/**
 * Add a click listener that debounces multiple clicks posted in the
 * same frame. A click on one button disables all buttons for that frame.
 */
fun View.setDebouncingOnClickListener(clickListner: (View?) -> Unit) {
    var enabled = true
    val enableAgain = Runnable { enabled = true }

    setOnClickListener(object : View.OnClickListener {
        override fun onClick(v: View?) {
            if (!enabled) return
            enabled = false

            // Post to the main looper directly rather than going through the view.
            // Ensure that ENABLE_AGAIN will be executed, avoid static field {@link #enabled}
            // staying in false state.
            MAIN_HANDLER.post(enableAgain)
            clickListner(v)
        }
    })
}
