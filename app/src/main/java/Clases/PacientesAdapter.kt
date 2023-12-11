package Clases

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appveterinaria.EditarPacienteFragment
import com.example.appveterinaria.R

class PacientesAdapter (private val pacientesList: List<Paciente>,
                      private val onEditPacienteListener: OnEditPacienteListener?)
    : RecyclerView.Adapter<PacientesAdapter.PacienteViewHolder>() {


    interface OnEditPacienteListener {
        fun onEditPacienteClicked(pacienteId: String)
    }

    class PacienteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nombrePaciente: TextView = itemView.findViewById(R.id.pacienteNameTextView)
        var carnetPaciente: TextView = itemView.findViewById(R.id.pacienteCodeTextView)
        var btnEditPaciente: Button = itemView.findViewById(R.id.btnEditPaciente)
        // var pacienteImageView: ImageView = itemView.findViewById(R.id.pacienteImageView) // para obtener una imagen dinámica
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PacienteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_paciente, parent, false)
        return PacienteViewHolder(view)
    }

    override fun onBindViewHolder(holder: PacienteViewHolder, position: Int) {
        val paciente = pacientesList[position]
        holder.nombrePaciente.text = paciente.nombre + " " + paciente.apellidos
        holder.carnetPaciente.text = paciente.carnet
        // holder.pacienteImageView.setImageResource(R.drawable.fixed_image) // aqui para cargar una imagen dinámica

        holder.btnEditPaciente.setOnClickListener {
            onEditPacienteListener?.onEditPacienteClicked(paciente.uid)
        }
    }

    override fun getItemCount() = pacientesList.size
}