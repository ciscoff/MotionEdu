package k.s.yarlykov.motionedu

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LineItemDecoration() : RecyclerView.ItemDecoration() {

    /**
     * @outRect в своих полях left, top, bottom, right возвращает значения padding'ов
     * для данного view. То есть это не прямоугольник с координатами или длинами сторон.
     * Это просто массив из 4-х чисел значений отступов.
     */

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        System.out.println("APP_TAG: getItemOffsets")

    }


}