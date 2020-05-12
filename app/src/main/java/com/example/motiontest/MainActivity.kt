package com.example.motiontest

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import com.lany.picker.DateTimePicker
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var isFullScreen: Boolean = false
    var progres: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        edtName.error = "測試"

        edtName.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

//        btnToStartScene.setOnClickListener { motionLayout.transitionToStart() }
//        btnToEndScene.setOnClickListener { motionLayout.transitionToEnd() }

        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                motionLayout.progress = (progress*0.01).toFloat()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        motionLayout.setTransitionListener(object : MotionLayout.TransitionListener{
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
                Log.e("Ian","[onTransitionTrigger] MotionLayout:${p0?.id}, p1:$p1, p2:$p2, p3:$p3")
            }

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
                Log.e("Ian","[onTransitionStarted] MotionLayout:${p0?.id}, p1:$p1, p2:$p2")
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
                Log.e("Ian","[onTransitionChange] MotionLayout:${p0?.id}, p1:$p1, p2:$p2, p3:$p3")
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                Log.e("Ian","[onTransitionCompleted] MotionLayout:${p0?.id}, p1:$p1")
            }

        })

        dateTimePicker.setOnChangedListener(object : DateTimePicker.OnChangedListener{
            override fun onChanged(
                p0: DateTimePicker?,
                p1: Int,
                p2: Int,
                p3: Int,
                p4: Int,
                p5: Int,
                p6: Int
            ) {
                tvSelectDate.text = "選擇時間 $p1 年 ${p2+1} 月 $p3 日 $p4 點 $p5 分 $p6 秒"
            }
        })

        btnScreenMode.setOnClickListener {
            if(!isFullScreen){
                requestWindowFeature(Window.FEATURE_NO_TITLE)  //全螢幕設定

                window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN)
                isFullScreen = true
            }else {
                requestWindowFeature(Window.FEATURE_ACTION_BAR)  //全螢幕設定

            }
        }

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            view.setBackgroundColor(getColor(R.color.colorPrimary))
        }

        btnProgress.setOnClickListener {
            if (btnProgress.getLoaderType() === ButtonProgressBar.Type.DETERMINATE) {
                callHandler()
            } else {
                btnProgress.startLoader()
                Handler().postDelayed({
                    btnProgress.stopLoader()
                    Toast.makeText(this@MainActivity, "Complete", Toast.LENGTH_SHORT).show()
                }, 5000)
            }
        }

        layoutIan.initShineButton(this)

    }

    fun validateName(input: String = ""): Boolean{
        if (input == "0"){
            return false
        }
        return true
    }

    private fun callHandler() {
        val handler = Handler()
        handler.postDelayed({
            if (progres <= 100) {
                updateUI()
                progres++
                callHandler()
            } else {
                progres = 0
                Toast.makeText(this@MainActivity, "Complete", Toast.LENGTH_SHORT).show()
            }
        }, 20)
    }

    fun updateUI() {
        runOnUiThread { btnProgress.setProgress(progres) }
    }
}
