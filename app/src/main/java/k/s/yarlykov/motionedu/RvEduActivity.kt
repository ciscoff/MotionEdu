package k.s.yarlykov.motionedu

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_explode.*
import kotlinx.android.synthetic.main.activity_rv_edu.*

class RvEduActivity : AppCompatActivity() {

    val imagesID = listOf(
        R.drawable.bkg_01_jan,
        R.drawable.bkg_02_feb,
        R.drawable.bkg_03_mar,
        R.drawable.bkg_04_apr,
        R.drawable.bkg_05_may
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rv_edu)

        initRecyclerView()
    }

    private fun initRecyclerView() {

        with(motionRecyclerView) {
            layoutManager = LinearLayoutManager(this@RvEduActivity)
            adapter = Adapter(this@RvEduActivity, imagesID)
        }
    }

    inner class Adapter(val context : Context, val images : List<Int>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

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

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val ivMotion = itemView.findViewById<ImageView>(R.id.ivMotion)
            val tvMotion = itemView.findViewById<TextView>(R.id.tvMotion)

            fun bind(imageId : Int) {
                ivMotion.setImageResource(imageId)
                tvMotion.text = context.getString(R.string.motion)
            }
        }
    }
}
