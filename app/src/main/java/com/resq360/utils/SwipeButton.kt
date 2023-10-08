package com.resq360.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.resq360.R


class SwipeButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private val slidingButton: ImageView
    private var initialX = 0f
    private var active = false
    private var initialButtonWidth = 0
    private val centerText: TextView

    private val disabledDrawable: Drawable?
    private val enabledDrawable: Drawable?

    var rightSwipeListener: (() -> Unit)? = null
    var leftSwipeListener: (() -> Unit)? = null

    init {
        val background = RelativeLayout(context)
        val layoutParamsView = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParamsView.addRule(CENTER_IN_PARENT, TRUE)

        background.background = ContextCompat.getDrawable(context, R.drawable.shape_slider_inner_background)
        addView(background, layoutParamsView)

        val layoutParamsText = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParamsText.addRule(CENTER_IN_PARENT, TRUE)

        centerText = TextView(context)
        centerText.text = context.getString(R.string.lbl_swipe_to_call)
        centerText.setTextColor(Color.WHITE)
        centerText.gravity = Gravity.CENTER
        centerText.setPadding(35, 35, 35, 35)
        background.addView(centerText, layoutParamsText)

        disabledDrawable = ContextCompat.getDrawable(context, R.drawable.iv_sos_slider_icon)
        enabledDrawable = ContextCompat.getDrawable(context, R.drawable.iv_sos_slider_icon)

        val layoutParamsButton = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParamsButton.addRule(ALIGN_PARENT_LEFT, TRUE)

        slidingButton = ImageView(context)
        layoutParamsButton.addRule(CENTER_VERTICAL, TRUE)
        slidingButton.setImageDrawable(disabledDrawable)
        slidingButton.setPadding(10, 40, 10, 40)
        addView(slidingButton, layoutParamsButton)

        setOnTouchListener(getButtonTouchListener())
    }

    private fun getButtonTouchListener(): OnTouchListener {
        return OnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> true
                MotionEvent.ACTION_MOVE -> {
                    if (initialX == 0f) {
                        initialX = slidingButton.x
                    }
                    if (event.x - slidingButton.width / 2 > 0 &&
                        event.x + slidingButton.width / 2 < width
                    ) {
                        slidingButton.x = event.x - slidingButton.width / 2
                        centerText.alpha =
                            1 - 1.3f * (slidingButton.x + slidingButton.width) / width
                    }
                    if (event.x + slidingButton.width / 2 > width) {
                        slidingButton.x = (width - slidingButton.width).toFloat()
                    }
                    if (event.x - slidingButton.width / 2 < 0) {
                        slidingButton.x = 0f
                    }

                    true
                }
                MotionEvent.ACTION_UP -> {
                    if (active) {
                        collapseButton()
                    } else {
                        initialButtonWidth = slidingButton.width
                        if (slidingButton.x + slidingButton.width > width * 0.85) {
                            expandButton()
                            rightSwipeListener?.invoke()
                        } else {
                            moveButtonBack()
                            leftSwipeListener?.invoke()
                        }
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun moveButtonBack() {
        val positionAnimator = ValueAnimator.ofFloat(slidingButton.x, 0f)
        positionAnimator.interpolator = AccelerateDecelerateInterpolator()
        positionAnimator.addUpdateListener {
            val x = positionAnimator.animatedValue as Float
            slidingButton.x = x
        }

        val objectAnimator = ObjectAnimator.ofFloat(
            centerText, "alpha", 1f
        )

        positionAnimator.duration = 200

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(objectAnimator, positionAnimator)
        animatorSet.start()
    }

    private fun expandButton() {
        val positionAnimator = ValueAnimator.ofFloat(slidingButton.x, 0f)
        positionAnimator.addUpdateListener {
            val x = positionAnimator.animatedValue as Float
            slidingButton.x = x
        }

        val widthAnimator = ValueAnimator.ofInt(
            slidingButton.width,
            width
        )

        widthAnimator.addUpdateListener {
            val params = slidingButton.layoutParams
            params.width = (widthAnimator.animatedValue as Int)
            slidingButton.layoutParams = params
        }

        val animatorSet = AnimatorSet()
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                active = true
                slidingButton.setImageDrawable(enabledDrawable)
            }
        })

        animatorSet.playTogether(positionAnimator, widthAnimator)
        animatorSet.start()
    }

    private fun collapseButton() {
        val widthAnimator = ValueAnimator.ofInt(
            slidingButton.width,
            initialButtonWidth
        )

        widthAnimator.addUpdateListener {
            val params = slidingButton.layoutParams
            params.width = (widthAnimator.animatedValue as Int)
            slidingButton.layoutParams = params
        }

        widthAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                active = false
                slidingButton.setImageDrawable(disabledDrawable)
            }
        })

        val objectAnimator = ObjectAnimator.ofFloat(
            centerText, "alpha", 1f
        )

        val animatorSet = AnimatorSet()

        animatorSet.playTogether(objectAnimator, widthAnimator)
        animatorSet.start()
    }
}