package com.example.foodliveryapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {


    //private val session = Session(applicationContext)

    //TODO connection error handling!
    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tryRestoreSession()
        login_button.setOnClickListener{
            checkCredentials(login_edittext.text.toString(), password_edittext.text.toString(), true)
        }
        gotoregister_button.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    private fun tryRestoreSession(){
        val session = Session(applicationContext)

        val username = session.username
        val password = session.password

        if(!(username.equals("@null") && password.equals("@null"))){

            checkCredentials(username, password, false)
        }
    }

    private fun checkCredentials(username: String, password:String, plainPassword: Boolean){
        val lh = LoginHandler(applicationContext)
        val session = Session(applicationContext)
        lh.storeLogin(username)

        if(plainPassword) {
            lh.storePassword(password)
        }else{
            lh.storeHashedPassword(password)
        }

        lh.checkCredentials { response ->
            run {
                val success = response.getInt("success")

                if(success == 1){

                    val jsonObject = response.getJSONArray("body").get(0)
                    val uuid = (jsonObject as JSONObject).getString("unique_id")

                    session.username = lh.login
                    session.password = lh.password
                    session.uuid = uuid

                    val intent = Intent(this, OrdersActivity::class.java)
                    startActivity(intent)
                }else{
                    session.username = "@null"
                    session.password = "@null"

                    Toast.makeText(applicationContext, "Nazwa użytkownika lub hasło jest niepoprawne", Toast.LENGTH_SHORT).show()
                }

            }}
    }


}
