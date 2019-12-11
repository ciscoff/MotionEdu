package k.s.yarlykov.motionedu.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.res.ResourcesCompat
import k.s.yarlykov.motionedu.R
import kotlin.math.min

class CircleColorView @kotlin.jvm.JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var radius : Float = 0f

    private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    @ColorInt
    var fillColorInt: Int = ResourcesCompat.getColor(resources, R.color.white, null)
        set(value) {
            field = value
            fillPaint.color = value
        }

    init {
        isClickable = true
    }


    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        radius = (min(width, height) / 2.0 * 0.8).toFloat()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        System.out.println("[View name] onMeasure w: ${MeasureSpec.toString(widthMeasureSpec)}")
//        System.out.println("[View name] onMeasure h: ${MeasureSpec.toString(heightMeasureSpec)}")

        val w = MeasureSpec.getSize(widthMeasureSpec)
        val h = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, fillPaint)
    }



}