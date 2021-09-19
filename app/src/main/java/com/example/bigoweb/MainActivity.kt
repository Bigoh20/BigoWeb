package com.example.bigoweb

import android.content.DialogInterface
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import com.example.bigoweb.databinding.ActivityMainBinding
import com.example.bigoweb.resources.InitSP.Companion.spITEM
import com.example.bigoweb.resources.toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    

    private lateinit var binding: ActivityMainBinding

    //Declarar una variable para saber si es la primera vez que el usuario entra.
    var isNotfirstTime : Boolean? =  null

    //Crear el sonido y usarlo cuando se seleccione algo.
    private var pressButton : MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inicializar el sonido.
        pressButton = MediaPlayer.create(this, R.raw.selection_section)

        //Colocar el name de bienvenida.
        setName()

        //Rellenar la variable para saber si se tiene que registrar o no.
        isNotfirstTime = spITEM.getBoolean(getString(R.string.key_boolean_firsttime))
      //Sistema de registro
         registerUser()

        binding.cardivewGo.setOnClickListener {
            startActivity(Intent(this, BigoUsers::class.java))
            pressButton?.start()
        }

          binding.cardivewProfile.setOnClickListener {
              if(spITEM.getStringSP(getString(R.string.key_username)).isEmpty()){
                   toast(getString(R.string.invalid_profile_text))
              }else{
                  //Si el perfil existe enviar los datos e iniciar el activity.
                  val i = Intent(this, UserProfile::class.java)
                  i.putExtra(getString(R.string.dev_name_key), spITEM.getStringSP(getString(R.string.key_username)))
                  startActivity(i)
                  pressButton?.start()

              }
          }
        //Configuración activity:
        binding.cardivewConfig.setOnClickListener {
            val i = Intent(this, ConfigActivity::class.java)
            startActivity(i)
            pressButton?.start()

        }
        //About us activity:
        binding.cardivewAbout.setOnClickListener {
            val i = Intent(this, Information::class.java)
            startActivity(i)
            pressButton?.start()
        }
    }

    //Renderizar el nombre.
    private fun setName(){
        binding.textViewName.text = "Welcome, ${spITEM.getStringSP(getString(R.string.key_username))}."

    }

   private fun registerUser(){
       //Realizar una validación: (Si es la primera vez:)
       //Sistema de registro.
       if(!isNotfirstTime!!){

           //Crear la vista para dialog_item:
           val view = layoutInflater.inflate(R.layout.dialog_item, null)

           //Crear el dialog y almacenarlo en una variable. con su vista:
           val dialog = MaterialAlertDialogBuilder(this)
               .setView(view)
               .setTitle(getString(R.string.title_dialog))
               .setCancelable(false)
               .setPositiveButton("Register")  { _, _ ->}
               .setNeutralButton("Join as invited") { _, _ -> //Hacer esto para crear usuario invitado.
                   binding.textViewName.text =  "Welcome, " + getString(R.string.invited_user)
               }
               .show()

           //Crear el evento del click.
           dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
               //Guardar el username del TextInputEditText
               val username = view.findViewById<TextInputEditText>(R.id.TIET_dialog).text.toString().trim()

               //Comprobar que no esté vacío.
               if(username.isEmpty()){
                   view.findViewById<TextInputLayout>(R.id.TIL_dialog).error = getString(R.string.error_empty_text)
               }else{
                   //Guardar el dato del nombre y de isNotFirstTime.
                   spITEM.putString(getString(R.string.key_username), username)
                   spITEM.putBoolean(getString(R.string.key_boolean_firsttime), true)
                   toast(getString(R.string.success_register_text))

                   //Guardar en un dato si compró VIP en un SP.
                   val vip = view.findViewById<CheckBox>(R.id.cbVIP).isChecked

                   spITEM.putBoolean(getString(R.string.key_is_vip), vip)

                   //Cerrar el dialog.
                   dialog.dismiss()
                   //Colocar el nombre
                   setName()
               }
           }
       }
       //Fin del sistema de registro.
    }
}