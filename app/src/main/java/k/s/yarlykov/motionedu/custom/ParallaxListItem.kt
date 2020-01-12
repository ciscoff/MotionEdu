package k.s.yarlykov.motionedu.custom

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.motion.widget.MotionLayout

class ParallaxListItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : MotionLayout(context, attrs, defStyleAttr), VerticalMotionListener {

    private var maxOffset = 0f

    override fun onOffsetChanged(offset: Float) {
        if(maxOffset != 0f) {
            progress = Math.abs((maxOffset - offset)/maxOffset)
        }
    }

    override fun onUpdateMaxOffset(maxOffset: Float) {
        this.maxOffset = maxOffset
    }
}

interface VerticalMotionListener {
    fun onOffsetChanged(offset : Float)
    fun onUpdateMaxOffset(maxOffset : Float)
}