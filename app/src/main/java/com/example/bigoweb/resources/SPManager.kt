package com.example.bigoweb.resources

import android.content.Context


class SPManager(context : Context){

private val preferences = context.getSharedPreferences("myDTB", 0)


    fun putString(key : String, value : String){
        preferences.edit().putString(key, value).apply()
    }
    fun getStringSP(key : String) : String{
        return preferences.getString(key, "")!!
    }

    fun putBoolean(key : String, value : Boolean){
        preferences.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key : String) : Boolean{
        return preferences.getBoolean(key, false)
    }

    fun wipeData(){
        preferences.edit().clear().apply()
    }



}