package k.s.yarlykov.motionedu

import android.os.Bundle
import android.view.LayoutInflater
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.nav_header_navigation_drawer.*


/**
 * Materials:
 * https://www.uplabs.com/posts/menu-slide-animated
 *
 * https://stackoverflow.com/questions/29191837/navigation-drawer-animation-android
 *
 *
 */

class NavigationDrawerActivity : AppCompatActivity() {

    companion object {
        private val ITEMS = 5
    }

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_drawer)
//        initRecyclerView()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.navigation_drawer, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun initRecyclerView() {
//        with(recyclerMenu) {
//            layoutManager = LinearLayoutManager(this@NavigationDrawerActivity)
//            adapter = Adapter()
//        }
    }


    inner class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            return ViewHolder(
                LayoutInflater.from(this@NavigationDrawerActivity)
                    .inflate(
                        R.layout.layout_item_menu,
                        parent,
                        false
                    )
            )
        }

        override fun getItemCount(): Int {
            return ITEMS
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//            setAnimation(holder.itemView, position)

//            holder.itemView.setOnClickListener { view ->
//            }
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    }

    private fun setAnimation(view : View, position : Int) {
        val context = this@NavigationDrawerActivity

        val animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)

        animation.duration = position.toLong() * 50 + 200
        view.startAnimation(animation)
    }

}
