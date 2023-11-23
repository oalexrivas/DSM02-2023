package com.example.appveterinaria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var cerrarSesionBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firebaseAuth = FirebaseAuth.getInstance()
        ExisteSession()

        cerrarSesionBtn = findViewById(R.id.CerraSessionButton)

        cerrarSesionBtn.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this@MainActivity,LoginActivity::class.java))
            finishAffinity()
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