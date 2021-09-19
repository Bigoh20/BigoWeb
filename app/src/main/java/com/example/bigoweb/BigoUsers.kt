package com.example.bigoweb

import android.content.DialogInterface
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.URLUtil
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bigoweb.databinding.BigoUsersBinding
import com.example.bigoweb.databinding.DialogAdduserItemBinding
import com.example.bigoweb.resources.*
import com.example.bigoweb.resources.InitSP.Companion.spITEM
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class BigoUsers : AppCompatActivity(), ClickListener {
    private lateinit var adapter : UserAdapter
    private lateinit var manager : RecyclerView.LayoutManager

    //Crear los sonidos.
    private var pressButtonSound : MediaPlayer? = null
    private var pressButtonSound2 : MediaPlayer? = null
    private var pressButtonSound3 : MediaPlayer? = null
    private var pressButtonWrong : MediaPlayer? = null

    //DO NOT TOUCH. IMPORTANT INFORMATION.
    private var users : ArrayList<UserData> = (arrayListOf(
        UserData("Bigooh", "https://i.pinimg.com/736x/e3/f1/cd/e3f1cdc0f0cd90b956b61834fef5e554.jpg",
            "Collectick", "@BigoDevOne", "@Carlos_Sánchez", "@Bigooh20",
            "Soy un desarrollador android de 16 años apasionado por el código libre. \n Trabajo actualmente con las tecnologías del FrontEnd para el desarrollo web, kotlin y java para android y javax swing para aplicaciones de escritorio. Cuando sepa hacer algo por lo que pueda cobrar, lo escribiré aquí, por mientras me limito a llenar este parámetro string llamado description... "),

        UserData("Nicolas_Dev", "https://static.vecteezy.com/system/resources/previews/000/380/173/non_2x/programming-vector-icon.jpg",
            "New Zealand O.C", "@Nikola016", "@Nicolás_PvP", "@Nico20",
            "Hola. Me llamo Nicolás y soy de Chile. Actualmente vivo en Nueva Zelanda y soy desarrollador web full stack. Llevo más de 10 años en la industria y he trabajdo en más de 10 países, entre ellos, Ecuador."),

        UserData("Carlos", "https://pbs.twimg.com/profile_images/1372222218734862346/J745eUuQ_400x400.jpg",
            "Microsoft", "@Carlos Jimenez", "@CarlosDev", "@Carl07",
        "Soy un desarrollador que trabaja en Microsoft como fullstack. Llevo 25 años en el mercado y puedo decir que he encontrado hacer algo que me apasiona. Hablo 5 idiomas y este texto es de relleno."),

        UserData("Juan", "https://upload.wikimedia.org/wikipedia/commons/b/b7/Juan_Darth%C3%A9s_.png",
            "Facebook", "@Juan Aguilar", "@Juanx", "@juanGamer",
            "Soy un desarrollador con 4 años de experiencia, y a pesar de que pueda parecer poco tiempo, tengo mucho conocimiento y espero poder servir a la comunidad Boliviana en el ámbito de la tecnología por mucho más tiempo. "),

        UserData("Sarc_20", "https://cdn.alfabetajuega.com/wp-content/uploads/2021/01/cod-mobile-unirse-a-partida-privada-780x405.jpg",
            "Don Pepe's shop.", "@SarcCOD", "@COD_gamer", "@SebasDev",
        "Soy sebastián, tengo 14 años y llevo programando desde los 9. A pesar de mi corta edad esto no me ha sido un limitante para estudiar lo que me gusta y puedo decir que no he sido más feliz. Esto es texto de relleno a."),

        UserData("Bigooh", "https://i.pinimg.com/736x/e3/f1/cd/e3f1cdc0f0cd90b956b61834fef5e554.jpg",
            "Collectick", "@BigoDevOne", "@Carlos_Sánchez", "@Bigooh20",
            "Soy un desarrollador android de 16 años apasionado por el código libre. \n Trabajo actualmente con las tecnologías del FrontEnd para el desarrollo web, kotlin y java para android y javax swing para aplicaciones de escritorio. Cuando sepa hacer algo por lo que pueda cobrar, lo escribiré aquí, por mientras me limito a llenar este parámetro string llamado description... "),

        UserData("Nicolas_Dev", "https://static.vecteezy.com/system/resources/previews/000/380/173/non_2x/programming-vector-icon.jpg",
            "New Zealand O.C", "@Nikola016", "@Nicolás_PvP", "@Nico20",
            "Hola. Me llamo Nicolás y soy de Chile. Actualmente vivo en Nueva Zelanda y soy desarrollador web full stack. Llevo más de 10 años en la industria y he trabajdo en más de 10 países, entre ellos, Ecuador."),

        UserData("Carlos", "https://pbs.twimg.com/profile_images/1372222218734862346/J745eUuQ_400x400.jpg",
            "Microsoft", "@Carlos Jimenez", "@CarlosDev", "@Carl07",
            "Soy un desarrollador que trabaja en Microsoft como fullstack. Llevo 25 años en el mercado y puedo decir que he encontrado hacer algo que me apasiona. Hablo 5 idiomas y este texto es de relleno."),

        UserData("Juan", "https://upload.wikimedia.org/wikipedia/commons/b/b7/Juan_Darth%C3%A9s_.png",
            "Facebook", "@Juan Aguilar", "@Juanx", "@juanGamer",
            "Soy un desarrollador con 4 años de experiencia, y a pesar de que pueda parecer poco tiempo, tengo mucho conocimiento y espero poder servir a la comunidad Boliviana en el ámbito de la tecnología por mucho más tiempo. "),

        UserData("Sarc_20", "https://cdn.alfabetajuega.com/wp-content/uploads/2021/01/cod-mobile-unirse-a-partida-privada-780x405.jpg",
            "Don Pepe's shop.", "@SarcCOD", "@COD_gamer", "@SebasDev",
            "Soy sebastián, tengo 14 años y llevo programando desde los 9. A pesar de mi corta edad esto no me ha sido un limitante para estudiar lo que me gusta y puedo decir que no he sido más feliz. Esto es texto de relleno a."),

    ))
    //DO NOT TOUCH. IMPORTANT INFORMATION.

    private lateinit var binding: BigoUsersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  BigoUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)


        adapter = UserAdapter(users, this)  //Enviar la lista de usuarios y el listener del click al adapter
        manager = LinearLayoutManager(this)




        binding.RecyclerViewUsers.adapter = adapter
        binding.RecyclerViewUsers.layoutManager = manager
        binding.RecyclerViewUsers.setHasFixedSize(true)


        //Inicializar sonidos.
         startSounds()



        binding.fab1.setOnClickListener {
            //Comprobar que tenga una cuenta.
            if(spITEM.getBoolean(getString(R.string.key_boolean_firsttime))){
                createUser()
            }else{
                toast("You need an account to add a developer")
                pressButtonWrong?.start()
            }

        }

    }



    //Crea el usuario validando los campos que tiene el dialog. Leer detenidamente:
    private fun createUser(){

        //Reproducir el sonido.
        pressButtonSound?.start()
    //Sacar la vista del dialog.
        val dialogView = layoutInflater.inflate(R.layout.dialog_adduser_item, null)

    //Crear el dialog con sus respectivos botones.
        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle("Add developer.")
            .setView(dialogView)
            .setCancelable(false)
            .setPositiveButton("Save changes") { _, _ -> } //  Dejamos el event vacío para abajo modificarlo
            .setNegativeButton("Cancel") {_, _ ->}  //No hacer nada, para que al dar click se cierre.
            .show()

    //Hasta este punto al usuario se le muestra el dialog con las opciones, luego si le da al boton de confirmar pasa esto:


    //Recuperar el evento del botón para darle otra funcionalidad. (Al de aceptar)
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            //Conseguir la información de los campos en cada variable

            val nameDev = dialogView.findViewById<TextInputEditText>(R.id.TIET_name).text.toString()
            val companyDev =  dialogView.findViewById<TextInputEditText>(R.id.TIET_company).text.toString()
            val urlDev =  dialogView.findViewById<TextInputEditText>(R.id.TIET_URL).text.toString()

            //Comprobar que ninguno esté vacío:
            if (nameDev.isEmpty()){
                dialogView.findViewById<TextInputLayout>(R.id.TIL_name)
                    .error = getString(R.string.error_empty_text)
            }
            else if (companyDev.isEmpty()){
                dialogView.findViewById<TextInputLayout>(R.id.TIL_company)
                    .error = getString(R.string.error_empty_text)
            }
            else if (urlDev.isEmpty()){
                dialogView.findViewById<TextInputLayout>(R.id.TIL_URL)
                    .error = getString(R.string.error_empty_text)
            }
            else if (!URLUtil.isValidUrl(urlDev)){
                dialogView.findViewById<TextInputLayout>(R.id.TIL_URL)
                    .error = getString(R.string.error_invalid_url)
            } else {

                //Reproducir el sonido.
                    pressButtonSound3?.start()
                //Si todos tienen información correcta, se añadirá un dato al array, notificando al adapter y cerrado el dialog.
                users.add(UserData(nameDev, urlDev, companyDev, "unknown", "unkown", "unkown", "Invited user."))
                adapter.notifyItemInserted(users.size)
                toast("The user was created successfully")
                dialog.dismiss()
            }
        }
    }

    //El evento del click para el recyclerview, ver Udemy 102 si no entiendes bien.
    //ESTA LLEVA AL PERFIL PERSONAL DEL DEVELOPER.
    override fun onClick(user: UserData) {
        pressButtonSound2?.start()

        //Crear un Intent que lleve a la activity del perfil con todos sus datos.
         val intent = Intent(this, ActivityProfile::class.java)
        intent.putExtra(getString(R.string.dev_name_key), user.name)
        intent.putExtra(getString(R.string.dev_company_key), user.company)
        intent.putExtra(getString(R.string.dev_url_key), user.photo)
        intent.putExtra(getString(R.string.dev_facebook_key), user.facebook)
        intent.putExtra(getString(R.string.dev_twitter_key), user.twitter)
        intent.putExtra(getString(R.string.dev_github_key), user.github)
        intent.putExtra(getString(R.string.dev_description_key), user.description)

        startActivity(intent)

    }
    private fun startSounds() {
        pressButtonSound = MediaPlayer.create(this, R.raw.sounds_button_press)
        pressButtonSound2 = MediaPlayer.create(this, R.raw.sound_click_two)
        pressButtonSound3 = MediaPlayer.create(this, R.raw.sound_click_three)
        pressButtonWrong = MediaPlayer.create(this, R.raw.wrong_selection)
    }



}