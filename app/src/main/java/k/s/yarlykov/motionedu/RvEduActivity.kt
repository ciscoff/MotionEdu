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
import k.s.yarlykov.motionedu.custom.ParallaxListItem.Companion.INVALID_MAX_OFFSET
import k.s.yarlykov.motionedu.custom.VerticalMotionListener
import kotlinx.android.synthetic.main.activity_rv_edu.*

class RvEduActivity : AppCompatActivity() {

    val imagesID = listOf(
        R.drawable.bkg_00_dec,
        R.drawable.bkg_01_jan,
        R.drawable.bkg_02_feb,
        R.drawable.bkg_03_mar,
        R.drawable.bkg_04_apr,
        R.drawable.bkg_05_may,
        R.drawable.bkg_06_jun,
        R.drawable.bkg_07_jul,
        R.drawable.bkg_08_aug,
        R.drawable.bkg_09_sep,
        R.drawable.bkg_10_oct,
        R.drawable.bkg_11_nov
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

                    /**
                     * Как я понял в dy (dx) получаем "длину" проделанного скрола.
                     * Она может быть отрицательной и положительной в зависимости от направления
                     *
                     * dy > 0 [Scrolled Downwards] Крутим к нижнему элементу списка
                     * dy < 0 [Scrolled Upwards] Крутим в начало, к верхнему элементу
                     */

                    if (dy != 0) {
                        subject.onNext(dy)
                    }
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
        }

        inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            private var maxY : Float = INVALID_MAX_OFFSET
            private val ivMotion = view.findViewById<ImageView>(R.id.ivMotion)
            private val tvMotion = view.findViewById<TextView>(R.id.tvMotion)
            private val motionView : VerticalMotionListener
                    = view.findViewById<ParallaxListItem>(R.id.motionLayout)

            init {
                val d = scrollObservable.subscribe {

                    if(adapterPosition == 0) {
                        motionView.setDebugFlag(true)
                    } else {
                        motionView.setDebugFlag(false)
                    }


                    if(view.y >= maxY) {
                        maxY = view.y
                        motionView.onUpdateMaxOffset(maxY)
                    }

                    if(adapterPosition == 0) {
                        System.out.println("APP_TAG: view.y=${view.y}")
                        System.out.println("APP_TAG: maxY=${maxY}")
                    }

                    motionView.onOffsetChanged(view.y)


//                    if(adapterPosition == 1) {
//                        System.out.println("APP_TAG: ItemYPosition y=${view.y}")
//                        System.out.println("APP_TAG: ItemHeight y=${view.height}")
//                    }
                }
            }

            fun bind(imageId: Int) {
                ivMotion.setImageResource(imageId)
                tvMotion.text = context.getString(R.string.motion)
                maxY = INVALID_MAX_OFFSET
            }
        }
    }
}
