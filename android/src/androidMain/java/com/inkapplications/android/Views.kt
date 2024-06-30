package com.inkapplications.android

import android.os.Handler
import android.os.Looper
import android.view.animation.AlphaAnimation
import android.view.View

/**
 * Set the visibility of a view.
 *
 * This works like Android's `setVisibility` but takes a boolean instead
 * of a `View` integer constant.
 * Use this to replace unwieldy if statements when binding views.
 *
 * Example:
 *
 *     fun toggleViews(show: Boolean) {
 *         my_view.setVisibility(show)
 *         my_invisible_view.setVisibility(show, View.INVISIBLE)
 *     }
 *
 * @param visible Whether the view should be displayed or not.
 * @param hiddenState Optional parameter to control how the view is hidden when [visible] is false.
 */
fun View.setVisibility(visible: Boolean, hiddenState: Int = View.GONE) {
    visibility = if (visible) View.VISIBLE else hiddenState
}

/**
 * Animate a view visibility to Visible.
 *
 * Triggers a simple alpha animation, transitioning a view from invisible
 * to visible.
 * The view should already be invisible before calling this method.
 *
 * @param duration How long the animation should last.
 * @param offset A delay before starting the animation.
 * @see fadeOut for the opposite.
 */
fun View.fadeIn(duration: Int = 500, offset: Int = 0) {
    val fade = AlphaAnimation(0f, 1f)
    fade.duration = duration.toLong()
    fade.startOffset = offset.toLong()
    startAnimation(fade)
    visibility = View.VISIBLE
}

/**
 * Animate a view visibility to Visible.
 *
 * Triggers a simple alpha animation, transitioning a view from visible
 * to invisible.
 * The view should already be visible before calling this method.
 *
 * @param duration How long the animation should last.
 * @param offset A delay before starting the animation.
 * @see fadeIn for the opposite.
 */
fun View.fadeOut(duration: Int = 500, offset: Int = 0) {
    val fade = AlphaAnimation(1f, 0f)
    fade.duration = duration.toLong()
    fade.startOffset = offset.toLong()
    startAnimation(fade)
    visibility = View.INVISIBLE
}
