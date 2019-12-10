package k.s.yarlykov.motionedu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_colors.*
import java.util.concurrent.TimeUnit

class ColorsActivity : AppCompatActivity() {

    private val obs =
        Observable.fromIterable(listOf<Int>(1)).delay(3, TimeUnit.SECONDS, Schedulers.newThread())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_colors)
    }

    override fun onResume() {
        super.onResume()

        /**
         * Сделал отложенную анимацию, чтобы проще было ловить глюк
         * с позиционированием.
         */
        obs
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                (palette as MotionLayout).transitionToEnd()
            }

    }
}
