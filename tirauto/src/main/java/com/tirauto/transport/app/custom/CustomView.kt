package com.tirauto.transport.app.custom

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import com.tirauto.transport.app.R
import com.walhalla.landing.databinding.IncChildVwBinding

class CustomView : RelativeLayout {
    private var binding: IncChildVwBinding? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    private fun init(context: Context) {
        // Inflate layout with ViewBinding
        binding = IncChildVwBinding.inflate(LayoutInflater.from(context),
            this, true
        )

        // Access the views using binding
        binding!!.mainCloseButton.setOnClickListener { v: View? ->
            closeChild()
        }

        //binding.mainTitleText.setText("Your Title Here");
        // You can further manipulate the views as needed
        //binding.mainBrowserLayout.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
    }

    fun setTitle(title: String?) {
        binding!!.mainTitleText.text = title
    }


    fun closeChild() {
        Log.v("@@@", "Closing Child WebView")
        val slideDown = AnimationUtils.loadAnimation(
            context, R.anim.slide_down
        )
        binding!!.mainAdChildLayout.startAnimation(slideDown)
        slideDown.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
            }

            override fun onAnimationEnd(animation: Animation) {
                binding!!.mainTitleText.text = ""
                binding!!.mainAdChildLayout.visibility = INVISIBLE
            }

            override fun onAnimationRepeat(animation: Animation) {
            }
        })
    }

    fun mainAdChildLayout(): RelativeLayout {
        return binding!!.mainAdChildLayout
    }

    fun mainBrowserLayout(): RelativeLayout {
        return binding!!.mainBrowserLayout
    }
}
