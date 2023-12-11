package Clases

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appveterinaria.EditarDoctorFragment
import com.example.appveterinaria.R

class DoctorsAdapter (private val doctorsList: List<Doctor>,
                      private val onEditDoctorListener: OnEditDoctorListener?)
    : RecyclerView.Adapter<DoctorsAdapter.DoctorViewHolder>() {


    interface OnEditDoctorListener {
        fun onEditDoctorClicked(doctorId: String)
    }

    class DoctorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nombreDoctor: TextView = itemView.findViewById(R.id.doctorNameTextView)
        var carnetDoctor: TextView = itemView.findViewById(R.id.doctorCodeTextView)
        var btnEditDoctor: Button = itemView.findViewById(R.id.btnEditDoctor)
        // var doctorImageView: ImageView = itemView.findViewById(R.id.doctorImageView) // para obtener una imagen dinámica
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_doctor, parent, false)
        return DoctorViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val doctor = doctorsList[position]
        holder.nombreDoctor.text = doctor.nombre + " " + doctor.apellidos
        holder.carnetDoctor.text = doctor.carnet
        // holder.doctorImageView.setImageResource(R.drawable.fixed_image) // aqui para cargar una imagen dinámica

        holder.btnEditDoctor.setOnClickListener {
            onEditDoctorListener?.onEditDoctorClicked(doctor.uid)
        }
    }

    override fun getItemCount() = doctorsList.size
}