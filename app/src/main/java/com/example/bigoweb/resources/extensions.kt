package com.example.bigoweb.resources

import android.app.Activity
import android.widget.Toast




    fun Activity.toast(str: String, long: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, str, long).show()
    }


