package k.s.yarlykov.motionedu.custom

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.GridLayout
import k.s.yarlykov.motionedu.R

private const val COLUMNS_PREF = 2


class AnimatedColorPickerView : GridLayout {

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        // По какой оси "нумеровать" столбцы
        orientation = GridLayout.HORIZONTAL

        // Массив цветов палитры
        val colors = context.resources.obtainTypedArray(R.array.palette_resources)
        val count = colors.length()

        columnCount = COLUMNS_PREF

        (0 until count).forEach { index ->

            val view = CircleColorView(context).apply {
                fillColorInt = colors.getColor(index, 0)
                tag = fillColorInt
                setOnClickListener { view ->
                    animateOnClick(view)
                    Log.e("APP_TAG", "ColorView clicked")
                }
            }

            addView(view)

//            setCrazyAnimation(view, index)
        }
        colors.recycle()
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        val pickerW = View.MeasureSpec.getSize(widthSpec)
        val pickerH = View.MeasureSpec.getSize(heightSpec)

        // Предпочтительные размеры для ребенка
        val childPrefW = pickerW / columnCount
        val childPrefH = pickerH / (childCount / columnCount)

        (0 until childCount).forEach { i ->
            val v = getChildAt(i)
            childMeasure(
                v,
                childPrefW,
                childPrefH,
                MeasureSpec.getMode(widthSpec),
                MeasureSpec.getMode(heightSpec)
            )
        }
        setMeasuredDimension(
            MeasureSpec.getSize(widthSpec),
            MeasureSpec.getSize(heightSpec)
        )

        requestLayout()
    }

    private fun setCrazyAnimation(view: View, i : Int) {
        // Alpha
//        val animator = ObjectAnimator.ofFloat(view, View.ALPHA, 0f)
//        animator.repeatCount = 1
//        animator.repeatMode = ObjectAnimator.REVERSE
//        animator.start()


        // Moving
//        val animator = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 200f)
//        animator.duration = 4000
//        animator.repeatCount = 1
//        animator.repeatMode = ObjectAnimator.REVERSE
//        animator.start()

        // Rotate
        val animator = ObjectAnimator.ofFloat(view, View.ROTATION, -360f, 0f)
        animator.duration = 2000
        animator.repeatCount = 3
        animator.start()
    }

    // Анимация масштабированием (уменьшение размера и восстановление)
    private fun animateOnClick(view: View) {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.8f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.8f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(
            view, scaleX, scaleY
        )
        animator.repeatCount = 1
        animator.duration = 100
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.start()
    }

    // Вызвать measure у дочернего элемента
    private fun childMeasure(child: View, w: Int, h: Int, modeW: Int, modeH: Int) {
        val childSpecWidth = View.MeasureSpec.makeMeasureSpec(w, modeW)
        val childSpecHeight = View.MeasureSpec.makeMeasureSpec(h, modeH)
        child.measure(childSpecWidth, childSpecHeight)
    }
}