package com.example.appveterinaria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistroUsuarioActivity : AppCompatActivity() {

    private lateinit var lblInicioSesion : TextView
    private lateinit var EditTextNombre : EditText
    private lateinit var EditTextApellido : EditText
    private lateinit var EditTextEmail : EditText
    private lateinit var EditTextPassword : EditText
    private lateinit var BtnRegistrar : Button

    private lateinit var auth : FirebaseAuth
    private lateinit var reference : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_usuario)
        //supportActionBar!!.title = "Registrar"
        InicializarVariables()

        lblInicioSesion = findViewById<TextView>(R.id.signinTextView)
        lblInicioSesion.setOnClickListener{
            val intent = Intent(this@RegistroUsuarioActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        BtnRegistrar.setOnClickListener{
            ValidarDatos()
        }
    }

    private fun InicializarVariables() {
        EditTextNombre = findViewById(R.id.NombresEditText)
        EditTextApellido = findViewById(R.id.ApellidosdEditText)
        EditTextEmail = findViewById(R.id.CorreoEditText)
        EditTextPassword = findViewById(R.id.Password2EditText)
        BtnRegistrar = findViewById(R.id.RegistrarButton)
        auth = FirebaseAuth.getInstance()
    }

    private fun ValidarDatos() {
        val nombre : String = EditTextNombre.text.toString()
        val apellido : String = EditTextApellido.text.toString()
        val email : String = EditTextEmail.text.toString()
        val password : String = EditTextPassword.text.toString()

        if(nombre.isEmpty()){
            Toast.makeText(applicationContext, "Ingrese nombre", Toast.LENGTH_SHORT).show()
        }
        else if(apellido.isEmpty()){
            Toast.makeText(applicationContext, "Ingrese apellido", Toast.LENGTH_SHORT).show()
        }
        else if(email.isEmpty()){
            Toast.makeText(applicationContext, "Ingrese un correo", Toast.LENGTH_SHORT).show()
        }
        else if(password.isEmpty()){
            Toast.makeText(applicationContext, "Ingrese contraseña", Toast.LENGTH_SHORT).show()
        }
        else{
            RegistrarUsuario(email, password)
        }
    }

    private fun RegistrarUsuario(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{task ->
                if (task.isSuccessful){
                     var uid : String = ""
                     uid = auth.currentUser!!.uid
                     reference = FirebaseDatabase.getInstance().reference.child("Usuarios").child(uid)

                     val hasmap = HashMap<String, Any>()
                     val nombre : String = EditTextNombre.text.toString()
                     val apellido : String = EditTextApellido.text.toString()
                     val email : String = EditTextEmail.text.toString()

                     hasmap["uid"] = uid
                     hasmap["nombre"] = nombre
                     hasmap["apellido"] = apellido
                     hasmap["email"] = email
                     hasmap["rol"] = "paciente"
                     hasmap["imagen"] = ""

                    reference.updateChildren(hasmap).addOnCompleteListener{ task2 ->
                        if (task2.isSuccessful){
                            val intent = Intent(this@RegistroUsuarioActivity, MainActivity::class.java)
                            Toast.makeText(applicationContext, "Se guardo la cuenta correctamente", Toast.LENGTH_SHORT).show()
                            startActivity(intent)
                            finish()
                        }
                    }.addOnFailureListener{e ->
                        Toast.makeText(applicationContext,"${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(applicationContext, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener{e ->
                Toast.makeText(applicationContext,"${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

}