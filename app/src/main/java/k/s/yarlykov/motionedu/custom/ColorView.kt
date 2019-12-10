package k.s.yarlykov.motionedu.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import k.s.yarlykov.motionedu.R

class ColorView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    /**
     * @ColorRes - это ссылка на ресурс, то есть R.color.black
     * @ColorInt - это непосредственное значение цвета, 32-х битное (включая alpha)
     */

    @ColorRes
    var fillColorRes: Int = R.color.white
        set(value) {
            field = value
            fillPaint.color = ContextCompat.getColor(context, value)
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

    /**
     * Просто принимаем предлженные размеры
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        System.out.println("[View name] onMeasure w: ${MeasureSpec.toString(widthMeasureSpec)}")
//        System.out.println("[View name] onMeasure h: ${MeasureSpec.toString(heightMeasureSpec)}")

        val w = MeasureSpec.getSize(widthMeasureSpec)
        val h = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(fillColorInt)
    }
}