package com.example.appveterinaria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var cerrarSesionBtn : Button
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()).commit()
            navigationView.setCheckedItem(R.id.nav_home)
        }

        firebaseAuth = FirebaseAuth.getInstance()
        ExisteSession()

        //cerrarSesionBtn = findViewById(R.id.nav_logout)

       /* cerrarSesionBtn.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this@MainActivity,LoginActivity::class.java))
            finishAffinity()
        }*/

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()).commit()
            R.id.nav_doctores -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DoctoresFragment()).commit()
            R.id.nav_pacientes -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PacientesFragment()).commit()
            R.id.nav_citas -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CitasFragment()).commit()
            R.id.nav_logout -> {
                firebaseAuth.signOut()
                startActivity(Intent(this@MainActivity,LoginActivity::class.java))
                finishAffinity()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun ExisteSession(){
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser == null){
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finishAffinity()

        }
        else{
            Toast.makeText(applicationContext,"Bienvenido(a) ${firebaseUser.email}", Toast.LENGTH_SHORT).show()
        }
    }
}