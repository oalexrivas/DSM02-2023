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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// TODO: Rename parameter arguments, choose names that match

/**
 * A simple [Fragment] subclass.
 * Use the [EditarDoctorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditarDoctorFragment : Fragment() {

    private lateinit var database: DatabaseReference //creamos intancia a la BD de firebase
    private var doctorId = ""
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
        return inflater.inflate(R.layout.fragment_editar_doctor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = FirebaseDatabase.getInstance().getReference("Doctores") //Obtenemos la instancia de la tabla doctores
        doctorId = arguments?.getString(ARG_DOCTOR_ID).toString() //asignamos el doctorid (uid)
        doctorId?.let {
            cargarDatosDoctor(it)
        }

        view.findViewById<Button>(R.id.btnEditDoctor).setOnClickListener {
            actualizarDoctorEnFirebase()
        }
    }

    private fun cargarDatosDoctor(doctorId: String) {
        database.child(doctorId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val doctor = snapshot.getValue(Doctor::class.java)
                doctor?.let { d ->
                    view?.findViewById<EditText>(R.id.EditNombresEditText)?.setText(d.nombre)
                    view?.findViewById<EditText>(R.id.EditApellidosdEditText)?.setText(d.apellidos)
                    view?.findViewById<EditText>(R.id.EditDuiEditText)?.setText(d.dui)
                    view?.findViewById<EditText>(R.id.EditTelefonoEditText)?.setText(d.telefono)
                    view?.findViewById<EditText>(R.id.EditCarnetEditText)?.setText(d.carnet)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Error al cargar datos: ${error.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
    private fun obtenerDatosActualizados(): Doctor {
        val nombres = view?.findViewById<EditText>(R.id.EditNombresEditText)?.text.toString().trim()
        val apellidos = view?.findViewById<EditText>(R.id.EditApellidosdEditText)?.text.toString().trim()
        val dui = view?.findViewById<EditText>(R.id.EditDuiEditText)?.text.toString().trim()
        val telefono = view?.findViewById<EditText>(R.id.EditTelefonoEditText)?.text.toString().trim()
        val carnet = view?.findViewById<EditText>(R.id.EditCarnetEditText)?.text.toString().trim()
        val rol = "Doctor"
        return Doctor(nombres, apellidos, dui, telefono, carnet,rol, doctorId)
    }

    private fun actualizarDoctorEnFirebase() {
        val doctorActualizado = obtenerDatosActualizados()

        doctorActualizado.uid?.let { uid ->
            database.child(uid).setValue(doctorActualizado)
                .addOnSuccessListener {
                    Toast.makeText(context, "Datos del doctor actualizados con éxito.", Toast.LENGTH_SHORT).show()
                    // retornamos al fragmento anterior
                    requireActivity().supportFragmentManager.popBackStack()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Error al actualizar los datos.", Toast.LENGTH_SHORT).show()
                }
        }
    }


    companion object {
        private const val ARG_DOCTOR_ID = "doctor_id"

        fun newInstance(doctorId: String) = EditarDoctorFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_DOCTOR_ID, doctorId)
            }
        }
    }
}