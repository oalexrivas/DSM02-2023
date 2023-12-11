package com.example.appveterinaria

import Clases.Doctor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AgregarDoctorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AgregarDoctorFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_agregar_doctor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = FirebaseDatabase.getInstance().getReference("Doctores")

        view.findViewById<Button>(R.id.RegistrarButton).setOnClickListener {
            guardarDoctor()
        }
    }
    private fun guardarDoctor() {
        val nombres = view?.findViewById<EditText>(R.id.NombresEditText)?.text.toString().trim()
        val apellidos = view?.findViewById<EditText>(R.id.ApellidosdEditText)?.text.toString().trim()
        val dui = view?.findViewById<EditText>(R.id.DuiEditText)?.text.toString().trim()
        val telefono = view?.findViewById<EditText>(R.id.TelefonoEditText)?.text.toString().trim()
        val carnet = view?.findViewById<EditText>(R.id.CarnetEditText)?.text.toString().trim()

        if (nombres.isEmpty() || apellidos.isEmpty() || dui.isEmpty() || telefono.isEmpty() || carnet.isEmpty()) {
            Toast.makeText(context, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
            return
        }

        val uid = database.push().key
        val doctor = Doctor(nombres, apellidos, dui, telefono, carnet,"Doctor", uid.toString())

        uid?.let {
            database.child(it).setValue(doctor).addOnCompleteListener {
                Toast.makeText(context, "Doctor guardado con éxito", Toast.LENGTH_SHORT).show()
                // retornamos al fragmento anterior
                requireActivity().supportFragmentManager.popBackStack()
            }.addOnFailureListener {
                Toast.makeText(context, "Error al guardar el doctor", Toast.LENGTH_SHORT).show()
            }
        }
    }
}