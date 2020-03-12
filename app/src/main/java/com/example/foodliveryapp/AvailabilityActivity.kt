package com.example.foodliveryapp

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.foodliveryapp.log.AvailabilityHandler
import com.example.foodliveryapp.ui.DrawerLayoutBuilder

class AvailabilityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_availability)

        val drawerBuilder = DrawerLayoutBuilder(this)
        drawerBuilder.build(R.id.availability_drawer_layout, R.id.availability_nav_view, R.id.availability_drawer_item)

        val availabilityHandler =
            AvailabilityHandler(this)

        availabilityHandler.getForm{response ->
            run{
                if(response.getInt("success") == 1){
                    availabilityHandler.fillFormFromJSON(response.getJSONArray("body").getJSONObject(0))
                    System.out.println("jestem w response")
                }


            }
        }

        findViewById<Button>(R.id.availability_update_button).setOnClickListener {

            run {
                availabilityHandler.fillFormFromUI();
                availabilityHandler.sendForm { response ->
                    run {
                        if(response.getInt("success") == 1){
                            Toast.makeText(this, "Poprawnie zaktualizowano dyspozycyjność", Toast.LENGTH_SHORT).show()
                            availabilityHandler.clearFormText()
                            availabilityHandler.fillUIForm()
                        }else{
                            Toast.makeText(this, "Nie udało się zaktualizować dyspozycyjności. Spróbuj ponownie", Toast.LENGTH_SHORT).show()

                        }
                    }
                }
            }
        }
    }
}
