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

class ColorPickerView : GridLayout {

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        // По какой оси "нумеровать" столбцы
        orientation = HORIZONTAL

        // Массив цветов палитры
        val colors = context.resources.obtainTypedArray(R.array.palette_resources)
        val count = colors.length()

        columnCount = COLUMNS_PREF

        (0 until count).forEach { index ->

            val view = ColorView(context).apply {
                fillColorInt = colors.getColor(index, 0)
                tag = fillColorInt
                setOnClickListener { view ->
                    animate(view)
                    Log.e("APP_TAG", "ColorView clicked")
                }
            }

            addView(view)
        }
        colors.recycle()
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        val pickerW = MeasureSpec.getSize(widthSpec)
        val pickerH = MeasureSpec.getSize(heightSpec)

        // Debug
//        System.out.println("[View name] onMeasure w: ${MeasureSpec.toString(widthSpec)}")
//        System.out.println("[View name] onMeasure h: ${MeasureSpec.toString(heightSpec)}")

        // Предпочтительные размеры для ребенка
        val childPrefW = pickerW / columnCount
        val childPrefH = pickerH / (childCount / columnCount)

        (0 until childCount).forEach { i ->
            val v = getChildAt(i)
            childMeasure(v, childPrefW, childPrefH, MeasureSpec.getMode(widthSpec), MeasureSpec.getMode(heightSpec))
        }
        setMeasuredDimension(MeasureSpec.getSize(widthSpec), MeasureSpec.getSize(heightSpec))

        /**
         * Ключевой момент ! Без этого вызова позиционирование данного кастомного вью
         * во время анимации выполняется неверно - он увеличивается не во все стороны от центра,
         * а от правого нижнего угла в направлении верхнего левого угла, то есть вправо и вниз
         * не растет. Поэтому после каждого изменения размера принудительно запрашиваем
         * перепозиционирование.
         */
        requestLayout()
    }

    // Анимация масштабированием (уменьшение размера и восстановление)
    private fun animate(view: View) {
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
    private fun childMeasure(child: View, w: Int, h: Int, modeW: Int, modeH : Int) {
        val childSpecWidth = MeasureSpec.makeMeasureSpec(w, modeW)
        val childSpecHeight = MeasureSpec.makeMeasureSpec(h, modeH)
        child.measure(childSpecWidth, childSpecHeight)
    }
}