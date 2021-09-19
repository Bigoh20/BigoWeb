package com.example.bigoweb

import android.content.DialogInterface
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.URLUtil
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.bigoweb.databinding.ActivityUserProfileBinding
import com.example.bigoweb.resources.InitSP.Companion.spITEM
import com.example.bigoweb.resources.toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class UserProfile : AppCompatActivity() {
    private lateinit var binding: ActivityUserProfileBinding

    //Crear el sonido y usarlo cuando se seleccione algo.
    private var pressButton : MediaPlayer? = null
    private var wrongButton : MediaPlayer? = null
    private var doneButton : MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Conseguir el bundle
        val bundle = intent.extras

        //Inicializar sonidos
        startSounds()
        //Conseguir la info si es VIP.
        val isVIP = spITEM.getBoolean(getString(R.string.key_is_vip))


        //Renderizar el nombre (ESTE NOMBRE PROVIENE DE LA MAIN ACTIVITY, POR ESO VA ASÍ)


        if(isVIP) {
            binding.tvUserName.text = "[VIP] " + bundle?.getString(getString(R.string.dev_name_key))
            binding.tvUserName.setTextColor(ContextCompat.getColor(this, R.color.purple_200))
            binding.tvUserName.textSize = 26.0F

        }else{
            binding.tvUserName.text = bundle?.getString(getString(R.string.dev_name_key))
        }
        //Conseguir la company y la url.
        val company = spITEM.getStringSP(getString(R.string.key_company_user))
        val url = spITEM.getStringSP(getString(R.string.key_url_user))

            //Renderizar la company (pero comprobando que tenga algo, en caso contrario ubicar unknown).
            binding.tvUserComapny.text =
            if(company.isEmpty()) "Unknown company"
            else company

        //Renderizar la url. (Pero comprobando que tenga algo, en caso contrario no hacer nada).
        if(!url.isEmpty()){
            Glide.with(this)
                .load(url)
                .centerCrop()
                .circleCrop()
                .into(binding.imageUser)
        }

        //Renderizar las redes.
        val facebook = spITEM.getStringSP(getString(R.string.key_facebook_user))
        val twitter = spITEM.getStringSP(getString(R.string.key_twitter_user))
        val github = spITEM.getStringSP(getString(R.string.key_github_user))
        val description = spITEM.getStringSP(getString(R.string.key_description_user))

        if(facebook.isEmpty())  binding.tvFacebook.text = getString(R.string.unknown_text)
        else binding.tvFacebook.text = "@$facebook"

        if(twitter.isEmpty())  binding.tvTwitter.text = getString(R.string.unknown_text)
        else binding.tvTwitter.text = "@$twitter"

        if(github.isEmpty())  binding.tvGitHub.text = getString(R.string.unknown_text)
        else binding.tvGitHub.text = "@$github"

        if(description.isEmpty())  binding.tvDescription.text = getString(R.string.empty_description_text)
        else binding.tvDescription.text = description


        //Evento de agregar datos (información avanzada solo para VIP)
        binding.FabMoreInfo.setOnClickListener {
            //Comprobar que sea VIP para acceder a la función.
            if(!isVIP){
                toast("Add advanced info is only for VIP users.")
                wrongButton?.start()
            }else{
                showAdvancedDialog()
                pressButton?.start()
            }

        }
        //Agregar info para todos los usuarios.
        binding.FabInfo.setOnClickListener {
            pressButton?.start()
            showDialog()
        }
    }

    private fun startSounds() {
        pressButton = MediaPlayer.create(this, R.raw.sounds_button_press)
        wrongButton = MediaPlayer.create(this, R.raw.wrong_selection)
        doneButton = MediaPlayer.create(this, R.raw.sound_click_three)
    }


    private fun showDialog() {
        //Sacar la vista del dialog.
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_info, null)

                //Crear el dialog con sus respectivos botones.
                val dialog = MaterialAlertDialogBuilder(this)
            .setTitle("Add information.")
            .setView(dialogView)
            .setCancelable(false)
            .setPositiveButton("Save changes") { _, _ -> } //  Dejamos el event vacío para abajo modificarlo
            .setNegativeButton("Cancel") {_, _ ->}  //No hacer nada, para que al dar click se cierre.
            .show()

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener{
//Conseguir la información de los campos en cada variable


            //Conseguimos la información de ambos campos.
            val companyUser =  dialogView.findViewById<TextInputEditText>(R.id.TIET_company).text.toString()
            val urlUser =  dialogView.findViewById<TextInputEditText>(R.id.TIET_URL).text.toString()


            if (companyUser.isEmpty()){
                dialogView.findViewById<TextInputLayout>(R.id.TIL_company)
                    .error = getString(R.string.error_empty_text)
                wrongButton?.start()
            }
            else if (urlUser.isEmpty()){
                dialogView.findViewById<TextInputLayout>(R.id.TIL_URL)
                    .error = getString(R.string.error_empty_text)
                wrongButton?.start()
            }
            else if (!URLUtil.isValidUrl(urlUser)){
                dialogView.findViewById<TextInputLayout>(R.id.TIL_URL)
                    .error = getString(R.string.error_invalid_url)
                wrongButton?.start()
            } else {



                //Si todos tienen información correcta, se guardarán en un sharedPreferences..
                spITEM.putString(getString(R.string.key_company_user),
                    companyUser)

                spITEM.putString(getString(R.string.key_url_user),
                    urlUser)

                toast(getString(R.string.text_success_save))

                //Reproducir el sonido.
                doneButton?.start()

                dialog.dismiss()
                finish()
            }
        }
    }
    private fun showAdvancedDialog() {


        val dialogView = layoutInflater.inflate(R.layout.dialog_add_social_info, null)
        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle("Add advanced information.")
            .setView(dialogView)
            .setPositiveButton("Next", null) //  Dejamos el event vacío para abajo modificarlo
            .setNegativeButton("Cancel", null) //No hacer nada, para que al dar click se cierre.
            .show()

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            //Recuperar los datos.
            val facebookUser =  dialogView.findViewById<TextInputEditText>(R.id.TIET_facebook).text.toString()
            val twitterUser =  dialogView.findViewById<TextInputEditText>(R.id.TIET_twitter).text.toString()
            val gitHubUser =  dialogView.findViewById<TextInputEditText>(R.id.TIET_github).text.toString()
            val descriptionUser =  dialogView.findViewById<TextInputEditText>(R.id.TIET_description).text.toString()
            //Validar que al menos uno esté añadido.

            if(facebookUser.isEmpty() && twitterUser.isEmpty() && gitHubUser.isEmpty() && descriptionUser.isEmpty()){
                Snackbar.make(dialogView.findViewById(R.id.parentLayout), "You need give at least one info", Snackbar.LENGTH_SHORT)
                    .setAnchorView(dialogView.findViewById(R.id.TIL_facebook))
                    .show()
                wrongButton?.start()
            }else {

                if (facebookUser.isNotEmpty()) spITEM.putString(
                    getString(R.string.key_facebook_user),
                    facebookUser
                )
                if (twitterUser.isNotEmpty()) spITEM.putString(
                    getString(R.string.key_twitter_user),
                    twitterUser
                )
                if (gitHubUser.isNotEmpty()) spITEM.putString(
                    getString(R.string.key_github_user),
                    gitHubUser
                )
                if (descriptionUser.isNotEmpty()) spITEM.putString(
                    getString(R.string.key_description_user),
                    descriptionUser
                )

                //Reproducir el sonido.
                doneButton?.start()

                dialog.dismiss()
                toast(getString(R.string.text_success_save))
                finish()
            }


        }

    }




}