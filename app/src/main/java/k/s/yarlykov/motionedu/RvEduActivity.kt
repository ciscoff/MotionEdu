package k.s.yarlykov.motionedu

/**
 * Materials:
 * https://stackoverflow.com/questions/33454609/detect-start-scroll-and-end-scroll-in-recyclerview
 *
 * Видимо это и есть способ двигать картинку внутри области просмотра
 * https://stackoverflow.com/questions/18073588/androidhow-can-i-show-a-part-of-image
 */

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import k.s.yarlykov.motionedu.custom.ParallaxListItem
import k.s.yarlykov.motionedu.custom.VerticalMotionListener
import kotlinx.android.synthetic.main.activity_rv_edu.*

class RvEduActivity : AppCompatActivity() {

    val imagesID = listOf(
        R.drawable.bkg_01_jan,
        R.drawable.bkg_02_feb,
        R.drawable.bkg_03_mar,
        R.drawable.bkg_04_apr,
        R.drawable.bkg_05_may
    )

    val subject: BehaviorSubject<Int> = BehaviorSubject.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rv_edu)

        initRecyclerView(this)
    }

    private fun initRecyclerView(context: Context) {

        with(motionRecyclerView) {
            addItemDecoration(LineItemDecoration())
            layoutManager = LinearLayoutManager(context)
            adapter = Adapter(context, imagesID, subject.hide())

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                    if (dy != 0) {
                        subject.onNext(dy)
                    }
//                    if (dy > 0) {
//                        System.out.println("APP_TAG: Scrolled Downwards y=${dy}")
//                    } else if (dy < 0) {
//                        System.out.println("APP_TAG: Scrolled Upwards y=${dy}")
//                    } else {
//                        System.out.println("APP_TAG: No Vertical Scrolled y=${dy}")
//                    }
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                }
            })
        }
    }

    inner class Adapter(
        val context: Context,
        val images: List<Int>,
        val scrollObservable: Observable<Int>
    ) : RecyclerView.Adapter<Adapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            return ViewHolder(
                LayoutInflater.from(context)
                    .inflate(
                        R.layout.layout_item_motion,
                        parent,
                        false
                    )
            )
        }

        override fun getItemCount(): Int {
            return images.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(imagesID[position])
//            holder.itemView.setOnClickListener { view ->
//                explodeOnClick(view)
//            }
        }

        inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            private var maxY : Float = 0f
            private val ivMotion = view.findViewById<ImageView>(R.id.ivMotion)
            private val tvMotion = view.findViewById<TextView>(R.id.tvMotion)
            private val motionView : VerticalMotionListener
                    = view.findViewById<ParallaxListItem>(R.id.motionLayout)

            init {
                val d = scrollObservable.subscribe {

                    if(view.y > maxY) {
                        maxY = view.y
                        motionView.onUpdateMaxOffset(maxY)
                    }

                    motionView.onOffsetChanged(view.y)

                    if(adapterPosition == 1) {
                        System.out.println("APP_TAG: ItemYPosition y=${view.y}")
                        System.out.println("APP_TAG: ItemHeight y=${view.height}")

                    }
                }
            }

            fun bind(imageId: Int) {
                ivMotion.setImageResource(imageId)
                tvMotion.text = context.getString(R.string.motion)
            }
        }
    }
}
