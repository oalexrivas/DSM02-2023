package com.example.appveterinaria

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginActivity : AppCompatActivity() {

    private lateinit var lblCrearCuenta : TextView
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog : ProgressDialog
    private lateinit var BtnIniciar : Button
    private  lateinit var EditTextEmail : EditText
    private lateinit var EditTextPassword : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor...")
        progressDialog.setCanceledOnTouchOutside(false)
        lblCrearCuenta = findViewById<TextView>(R.id.signupTextView)

        InicializarVariables()

        lblCrearCuenta.setOnClickListener{
            val intent = Intent(this@LoginActivity, RegistroUsuarioActivity::class.java)
            startActivity(intent)
            finish()
        }

        BtnIniciar.setOnClickListener{
            ValidarInfomacion()

        }
    }

    private fun InicializarVariables() {
        EditTextEmail = findViewById(R.id.emailEditText)
        EditTextPassword = findViewById(R.id.passwordEditText)
        BtnIniciar = findViewById(R.id.loginButton)
    }

    private var email = ""
    private var password = ""

    private fun ValidarInfomacion() {
        email = EditTextEmail.text.toString().trim()
        password = EditTextPassword.text.toString().trim()

        if(email.isEmpty()){
            Toast.makeText(applicationContext, "Ingrese un correo", Toast.LENGTH_SHORT).show()
            EditTextEmail.requestFocus()
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(applicationContext, "¡Correo no valido!", Toast.LENGTH_SHORT).show()
            EditTextEmail.requestFocus()
        }
        else if(password.isEmpty()){
            Toast.makeText(applicationContext, "Ingrese contraseña", Toast.LENGTH_SHORT).show()
            EditTextPassword.requestFocus()
        }
        else{
            Login()
        }

    }

    private fun Login() {
        progressDialog.setMessage("Iniciando sesión...")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this@LoginActivity, MainActivity:: class.java))
                finishAffinity()
            }.addOnFailureListener{e ->
                progressDialog.dismiss()
                Toast.makeText(applicationContext,"No se pudo iniciar sesión debido a ${e.message}", Toast.LENGTH_SHORT).show()
            }


    }


}