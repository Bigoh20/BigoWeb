package com.example.bigoweb

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.bigoweb.databinding.ActivityConfigBinding
import com.example.bigoweb.resources.InitSP.Companion.spITEM
import com.example.bigoweb.resources.toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ConfigActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConfigBinding
    //Crear los textos de información sobre la información.
    private var terms = ""
    private var privacy = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Rellenar la info.
        terms = getString(R.string.text_terms)
        privacy = getString(R.string.text_privacy)
        //Crear el boolean para saber si es invitado
        val isNotInvited = spITEM.getBoolean(getString(R.string.key_boolean_firsttime))

        //Crear el evento de eliminar la cuenta
        binding.deleteButton.setOnClickListener{

            //Si no es invitado es porque tiene una cuenta, en ese caso proceder eliminandola, en caso contrario toast.
            if(isNotInvited) {
                deleteAccount()
            }else{
                toast("You have no an account.")
            }
        }

        //Crear el evento de eliminar personal data.
        binding.deleteSocialButton.setOnClickListener {
            if(isNotInvited) {
                deleteSocialData()
            }else{
                toast("You have no an account.")
            }
        }

        binding.termButton.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.dialog_about, null)

            MaterialAlertDialogBuilder(this)
                .setTitle("Terms of use.")
                .setView(view)
                .setPositiveButton("Ok") { _, _ ->}
                .show()
                view.findViewById<TextView>(R.id.tv_info).text = terms

        }

        binding.privacyButton.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.dialog_about, null)

            MaterialAlertDialogBuilder(this)
                .setTitle("Privacy")
                .setView(view)
                .setPositiveButton("Ok") { _, _ ->}
                .show()
            view.findViewById<TextView>(R.id.tv_info).text = privacy
        }

        binding.feedbackButton.setOnClickListener {
            toast("Comment on our youtube channel!")
        }

        binding.rateButton.setOnClickListener {
            toast("Coming soon")
        }
    }

    private fun deleteSocialData() {
        //Crear el dialog para que confirme.
        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle("Confirmation")
            .setCancelable(false)
            .setPositiveButton("Delete social media.") { _, _ ->}
            .setNegativeButton("Cancel") { _, _ ->}
            .show()

        //Recuperar el botón para eliminar los datos de la preferencia.
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
        //(Primero recuperar el nombre y el si es vip y si es la primera vez para no borrar that stuff.)
        val name = spITEM.getStringSP(getString(R.string.key_username))
            val isVIP = spITEM.getBoolean(getString(R.string.key_is_vip))
            val isNotFirstTime = spITEM.getBoolean(getString(R.string.key_boolean_firsttime))

        //Eliminar todo.
        spITEM.wipeData()
        //Colocar de nuevo el nombre, el si es vip y si es la primera vez que entró
        spITEM.putString(getString(R.string.key_username), name)
            spITEM.putBoolean(getString(R.string.key_is_vip), isVIP)
            spITEM.putBoolean(getString(R.string.key_boolean_firsttime), isNotFirstTime)
        //Cerrar la ventana y enviar un toast.
        toast("Your social data was wiped successfully! ")
            dialog.dismiss()
        finish()
        }
    }

    private fun deleteAccount() {
        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle("Confirmation")
            .setCancelable(false)
            .setPositiveButton("Delete account") { _, _ ->}
            .setNegativeButton("Cancel") { _, _ ->}
            .show()

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            //Eliminar todos los datos de la preferencia de usuarios.
            spITEM.wipeData()
            //Cerrar dialog
            dialog.dismiss()
            //Matar al activity.
            finishAffinity()
            //Toast
            toast("The account was deleted successfully, app restart is required.", Toast.LENGTH_LONG)

        }


    }
}