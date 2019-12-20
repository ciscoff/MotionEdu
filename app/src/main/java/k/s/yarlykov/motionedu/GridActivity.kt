package k.s.yarlykov.motionedu

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.children
import androidx.transition.*
import kotlinx.android.synthetic.main.activity_explode.*
import kotlinx.android.synthetic.main.activity_grid.*

class GridActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid)

        with(grid) {
            (0 until childCount).forEach { i ->
                val v = getChildAt(i)

                if (v is Button) {
                    v.setOnClickListener {
                        explodeOnClick(it)
                    }
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
//        (gridMotion as MotionLayout).transitionToEnd()
    }

    /**
     * Короче не работает Explode c GridLayout (или GridView). Причина описана здесь:
     * https://stackoverflow.com/questions/36363761/android-explode-transition-doesnt-explode-gridview
     */

    private fun explodeOnClick(view: View) {

        val viewRect = Rect()
        view.getGlobalVisibleRect(viewRect)

        val explodeTransition = Explode()

        explodeTransition.epicenterCallback = object : Transition.EpicenterCallback() {
            override fun onGetEpicenter(transition: Transition): Rect {
                return viewRect
            }
        }

        explodeTransition.excludeTarget(view, true)

        val set = TransitionSet().apply {
            addTransition(explodeTransition)
            addTransition(Fade().addTarget(view))
        }

        TransitionManager.beginDelayedTransition(grid, set)

        // Триггер начала анимации. Удаляем контент и все начинается (НЕ РАБОТАЕТ !!!!)
        grid.removeAllViews()
    }
}
