package com.example.motiontest

import android.animation.Animator
import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.Animation
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import kotlinx.android.synthetic.main.view_ian.view.*
import java.util.logging.Handler

class IanView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_ian, this, false)
        val set = ConstraintSet()
        addView(view)

        set.clone(this)
        set.applyTo(this)

        btnShine.visibility = View.GONE

        btnFab.setOnClickListener {
//            tvTitle.text = "Upload"
            tvTitle.animateText("Upload")
            btnFab.showProgress(true)
            postDelayed(Runnable {
                btnFab.showProgress(false)
                rippleAnimation() },2000)
        }
    }

    fun initShineButton(activity: Activity){
        btnShine.init(activity)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    fun rippleAnimation(){
        val anim: Animator = ViewAnimationUtils.createCircularReveal(this,this.width,this.height/2,0f,width*1.5f)
        anim.duration = 1000L
        anim.addListener(object:Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                btnFab.visibility = View.GONE
                btnShine.visibility = View.VISIBLE
//                tvTitle.text = "Finish"
                tvTitle.animateText("Finish")
                btnShine.callOnClick()


            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
                btnFab.visibility = View.INVISIBLE
//                tvTitle.text = ""
                tvTitle.animateText("")
            }

        })
        anim.start()
    }
}