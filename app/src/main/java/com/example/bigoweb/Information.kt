package com.example.bigoweb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bigoweb.databinding.ActivityInformationBinding
import android.text.method.LinkMovementMethod

class Information : AppCompatActivity() {
    private lateinit var binding: ActivityInformationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Renderizar los hipervinculos.
  binding.tvYoutube.movementMethod = LinkMovementMethod.getInstance()
        binding.tvDiscord.movementMethod = LinkMovementMethod.getInstance()

    }
}