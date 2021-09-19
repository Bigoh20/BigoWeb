package com.example.bigoweb.resources

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bigoweb.R
import com.example.bigoweb.databinding.UserItemBinding
import com.example.bigoweb.resources.InitSP.Companion.spITEM
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class UserAdapter(private val information: List<UserData>,
                  val listener: ClickListener) : RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    //Crear el Context para usarlo.
    private lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false)
        return UserViewHolder(view)
    }

    //Este bind sirve como un each for para cada celda, usamos los métodos que están en el Holder.
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.render(position)
        holder.setOnClickListener(information[position]) //= Esto saca el item del array de acuerdo a la posición
    }

    override fun getItemCount() = information.size



    inner class UserViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val binding = UserItemBinding.bind(view)


        //Renderizar todos los elementos. Necesita el parámetro pos para sacar el item del array.
        fun render(pos : Int){
           val user = information[pos]
            //Cargar la información principal
            binding.tvName.text = user.name
            binding.tvCompany.text = user.company
            binding.tvID.text = (pos+1).toString()



            //Cargar la imagen usando Glide.
            Glide.with(context)
                .load(user.photo)
                .centerCrop()
                .circleCrop()
                .into(binding.imageViewUser)

        }
        //
        fun setOnClickListener(user : UserData){
            binding.CardViewUser.setOnClickListener { listener.onClick(user) }

        }

    }
}

