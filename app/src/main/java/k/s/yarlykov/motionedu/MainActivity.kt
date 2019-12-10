package k.s.yarlykov.motionedu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonCancel.setOnClickListener {
            (motionLayout as MotionLayout).transitionToStart()
        }

        buttonOk.setOnClickListener {
            startActivity(Intent(this, GridActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        (motionLayout as MotionLayout).transitionToEnd()
    }
}
