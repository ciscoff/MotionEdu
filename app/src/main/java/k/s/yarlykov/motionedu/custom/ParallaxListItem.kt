package k.s.yarlykov.motionedu.custom

import android.content.Context
import android.graphics.drawable.ClipDrawable
import android.util.AttributeSet
import android.widget.ImageView
import androidx.constraintlayout.motion.widget.MotionLayout
import k.s.yarlykov.motionedu.R

class ParallaxListItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : MotionLayout(context, attrs, defStyleAttr), VerticalMotionListener {

    private var maxOffset = 0f
    lateinit var clipDrawable : ClipDrawable

    override fun onOffsetChanged(offset: Float) {


        if(maxOffset != 0f && offset >= 0) {
            System.out.println("APP_TAG: ${hashCode().toString(16)}. maxOffset=$maxOffset, offset=$offset")

            progress = Math.abs((maxOffset - offset)/maxOffset)
            clipDrawable.level = (Math.abs((maxOffset - offset)/maxOffset) * 10000f).toInt()

            System.out.println("APP_TAG: ${hashCode().toString(16)}. level=${clipDrawable.level}")
//            ((maxOffset - offset)/maxOffset).toInt()




//            System.out.println("APP_TAG: clipDrawable.level y=${Math.abs((maxOffset - offset)/maxOffset).toInt() * 10000}")

        }
    }

    override fun onUpdateMaxOffset(maxOffset: Float) {
        clipDrawable = this.findViewById<ImageView>(R.id.ivMotion).drawable as ClipDrawable
        this.maxOffset = maxOffset
    }
}

interface VerticalMotionListener {
    fun onOffsetChanged(offset : Float)
    fun onUpdateMaxOffset(maxOffset : Float)
}