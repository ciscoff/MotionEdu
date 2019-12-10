package k.s.yarlykov.motionedu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.motion.widget.MotionLayout
import kotlinx.android.synthetic.main.activity_grid.*

class GridActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid)
    }

    override fun onResume() {
        super.onResume()
        (gridMotion as MotionLayout).transitionToEnd()
    }
}
