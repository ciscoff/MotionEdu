package k.s.yarlykov.motionedu.custom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.transition.*
import k.s.yarlykov.motionedu.R
import kotlinx.android.synthetic.main.activity_crazy.*

class CrazyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crazy)

//        val scene2 = Scene.getSceneForLayout(sceneRoot, R.layout.scene_crazy_3, this)
//
//        sceneRoot.setOnClickListener {
//            val set = TransitionSet()
//            set.addTransition(Explode())
//            set.duration = 1000
//            set.interpolator = AccelerateInterpolator()
//            TransitionManager.go(scene2, set)
//        }

        layoutContainer.setOnClickListener {
            handleTouch()
        }
    }

    fun handleTouch() {

        val transition = Explode()
        transition.duration = 1000
        transition.interpolator = AccelerateInterpolator()
        TransitionManager.beginDelayedTransition(gridRoot)


        with(gridRoot) {
            (0 until childCount).forEach { i ->
                val v = getChildAt(i)
                v.visibility = View.INVISIBLE
            }
        }
    }

}
