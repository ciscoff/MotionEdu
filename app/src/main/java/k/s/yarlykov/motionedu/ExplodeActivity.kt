package k.s.yarlykov.motionedu

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.*
import kotlinx.android.synthetic.main.activity_explode.*


private const val COLUMNS = 6
private const val ROWS = 8

/**
 * В этом примере вся активити появляется на экране как пустой элемент
 * с голубым фоном. При клике на любое место голубой фон разлетается в разные стороны
 * в виде квадратиков.
 */
class ExplodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explode)
        initRecyclerView(ROWS, COLUMNS)
    }

    private fun initRecyclerView(rows: Int, columns : Int) {

        with(recyclerView) {
            addItemDecoration(GridItemDecoration(rows, columns))
            layoutManager = GridLayoutManager(this@ExplodeActivity, columns)
            adapter = Adapter()
        }
    }

    fun explodeOnClick(view: View) {

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
//            addListener()
        }

        TransitionManager.beginDelayedTransition(recyclerView, set)

        // Триггер начала анимации. Удаляем контент и все начинается
        recyclerView.adapter = null
    }

    inner class tla : TransitionListenerAdapter() {
        override fun onTransitionEnd(transition: Transition) {
            transition.removeListener(this)
            this@ExplodeActivity.onBackPressed()
        }
    }

    inner class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            return ViewHolder(
                LayoutInflater.from(this@ExplodeActivity)
                    .inflate(
                        R.layout.layout_list_item,
                        parent,
                        false
                    )
            )
        }

        override fun getItemCount(): Int {
            return COLUMNS * ROWS
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.setOnClickListener { view ->
                explodeOnClick(view)
            }
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    }
}


