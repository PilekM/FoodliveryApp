package com.example.foodliveryapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_orders.*

class OrdersActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        val session = Session(applicationContext)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)

        order_textview.text = "Hi my login is: " + session.username + " my password: " + session.password + " my uuid: " + session.uuid

        logout_button.setOnClickListener {
            session.restartSession()
            finish()
        }
    }
}
