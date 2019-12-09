package k.s.yarlykov.motionedu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonOk.setOnClickListener {
            (motionLayout as MotionLayout).transitionToStart()
        }
    }

    override fun onResume() {
        super.onResume()
        (motionLayout as MotionLayout).transitionToEnd()
    }
}
