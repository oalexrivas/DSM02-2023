package com.example.appveterinaria

import Clases.Doctor
import Clases.DoctorsAdapter
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// TODO: Rename parameter arguments, choose names that match

class DoctoresFragment : Fragment(), DoctorsAdapter.OnEditDoctorListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DoctorsAdapter


    //funcion para pasar al fragmento editarDoctor el doctorId (uid)
    override fun onEditDoctorClicked(doctorId: String) {
        val editDoctorFragment = EditarDoctorFragment.newInstance(doctorId)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, editDoctorFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_doctores, container, false)
        recyclerView = view.findViewById(R.id.doctorsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        loadDoctors()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Accion del boton de agregar doctor
        val fab: FloatingActionButton = view.findViewById(R.id.addDoctor)
        fab.setOnClickListener {
            showAddDoctorView()
        }
    }

    //funcion para mostrar fragmento de agregar doctor
    private fun showAddDoctorView() {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, AgregarDoctorFragment())
        transaction.addToBackStack(null) // permite que el usuario regrese al fragmento anterior
        transaction.commit()
    }

    //Funcion para cargar el listado de doctores desde firebase y asignarlos al recyclerView
    private fun loadDoctors() {
        val doctorsList = mutableListOf<Doctor>()
        val databaseReference = FirebaseDatabase.getInstance().getReference("Doctores")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                doctorsList.clear()
                for (postSnapshot in dataSnapshot.children) {
                    val doctor = postSnapshot.getValue(Doctor::class.java)
                    doctor?.let { doctorsList.add(it) }
                }
                adapter = DoctorsAdapter(doctorsList,this@DoctoresFragment)
                recyclerView.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // handle error
            }
        })
    }
}
