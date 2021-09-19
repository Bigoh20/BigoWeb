package com.example.bigoweb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.bigoweb.databinding.ItemProfileBinding
import com.example.bigoweb.resources.InitSP
import com.example.bigoweb.resources.InitSP.Companion.spITEM

class ActivityProfile : AppCompatActivity() {

    private lateinit var binding: ItemProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ItemProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Crear el bundle para recuperar los datos.
        val bundle = intent.extras

        //Renderizar el nombre

        binding.tvDevName.text = bundle?.getString(getString(R.string.dev_name_key))

        //Renderizar la compañía
        binding.tvDevComapny.text = bundle?.getString(getString(R.string.dev_company_key))

        //Renderizar la url:
        Glide.with(this)
            .load(bundle?.getString(getString(R.string.dev_url_key)))
            .centerCrop()
            .circleCrop()
            .into(binding.imageDev)

        //Renderizar las redes.
        binding.tvFacebook.text = bundle?.getString(getString(R.string.dev_facebook_key))

        binding.tvTwitter.text = bundle?.getString(getString(R.string.dev_twitter_key))

        binding.tvGitHub.text = bundle?.getString(getString(R.string.dev_github_key))

        //Renderizar la descripción.
        binding.tvDescription.text = bundle?.getString(getString(R.string.dev_description_key))



    }
}