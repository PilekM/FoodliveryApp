package com.example.foodliveryapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val lh = LoginHandler()


    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        login_button.setOnClickListener{
            lh.storeLogin(login_edittext)
            lh.storePassword(password_edittext)

            lh.checkCredentials(applicationContext)

        }

    }

    fun loginButtonHandler(){
    }


}
