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

    companion object {
        val INVALID_MAX_OFFSET = Float.NEGATIVE_INFINITY
    }

    private var maxOffset = INVALID_MAX_OFFSET
    private var interpolationLength = 0f
    private var isShowProgress = false

    override fun onOffsetChanged(offset: Float) {

        if (isShowProgress) {
            System.out.println("APP_TAG: ${hashCode().toString(16)}: interpolationLength=$interpolationLength, maxOffset=$maxOffset, offset=$offset, height=$height, progress=$progress")
        }

        if (maxOffset != INVALID_MAX_OFFSET && interpolationLength != 0f && offset >= -height) {
            progress = (interpolationLength - (height.toFloat() + offset)) / interpolationLength

//            if (isShowProgress) {
//                System.out.println("APP_TAG: ${hashCode().toString(16)}: interpolationLength=$interpolationLength, maxOffset=$maxOffset, offset=$offset, height=$height, progress=$progress")
//            }


//            progress = Math.abs((maxOffset - offset)/maxOffset)
//            clipDrawable.level = (Math.abs((maxOffset - offset)/maxOffset) * 10000f).toInt()
//
//            System.out.println("APP_TAG: ${hashCode().toString(16)}. level=${clipDrawable.level}")
//            ((maxOffset - offset)/maxOffset).toInt()
//            System.out.println("APP_TAG: clipDrawable.level y=${Math.abs((maxOffset - offset)/maxOffset).toInt() * 10000}")
        }
    }

    override fun setDebugFlag(flag: Boolean) {
        isShowProgress = flag
    }

    override fun onUpdateMaxOffset(maxOffset: Float) {
        this.maxOffset = maxOffset
        this.interpolationLength = maxOffset + height
    }
}

interface VerticalMotionListener {
    fun onOffsetChanged(offset: Float)
    fun onUpdateMaxOffset(maxOffset: Float)
    fun setDebugFlag(flag: Boolean)
}