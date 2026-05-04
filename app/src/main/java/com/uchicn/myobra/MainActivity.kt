package com.uchicn.myobra

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uchicn.myobra.ui.drawer.DrawerAdapter
import com.uchicn.myobra.ui.drawer.DrawerItem

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)

        // ☰ BOTÓN HAMBURGUESA (FORMA CORRECTA)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.app_name,
            R.string.app_name
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // NAV CONTROLLER
        val navController = findNavController(R.id.nav_host_fragment)

        val calculoChildren = listOf(
            DrawerItem.Child("Zapata", 0, R.id.nav_calc_zapata),
            DrawerItem.Child("Cimiento", 0, R.id.nav_calc_cimiento),
            DrawerItem.Child("Sobrecimiento", 0, R.id.nav_calc_sobrecimiento),
            DrawerItem.Child("Muro", 0, R.id.nav_calc_muro),
            DrawerItem.Child("Piso", 0, R.id.nav_calc_piso),
            DrawerItem.Child("Columna", 0, R.id.nav_calc_columna),
            DrawerItem.Child("Viga", 0, R.id.nav_calc_viga),
            DrawerItem.Child("Losa", 0, R.id.nav_calc_losa)
        )

        val drawerItems: MutableList<DrawerItem> = mutableListOf(
            DrawerItem.Child("Inicio", R.drawable.ic_home, R.id.nav_home),
            DrawerItem.Child("Topografía", R.drawable.ic_topo, R.id.nav_topo),
            DrawerItem.Group("Cálc. Materiales",R.drawable.ic_calc_mat,calculoChildren),
            DrawerItem.Child("Información", R.drawable.ic_info, R.id.nav_info)
        )

        val drawerAdapter = DrawerAdapter(drawerItems) { destinationId ->
            navController.navigate(destinationId)
            drawerLayout.closeDrawers()
        }

        findViewById<RecyclerView>(R.id.rvDrawer).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = drawerAdapter
        }
    }
}
