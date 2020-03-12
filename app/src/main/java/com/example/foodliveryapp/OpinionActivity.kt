package com.example.foodliveryapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.foodliveryapp.log.OpinionHandler
import com.example.foodliveryapp.ui.DrawerLayoutBuilder
import kotlinx.android.synthetic.main.activity_opinion.*
import java.text.SimpleDateFormat


class OpinionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opinion)

        val drawerLayoutBuilder = DrawerLayoutBuilder(this)
        drawerLayoutBuilder.build(R.id.opinion_drawer_layout, R.id.opinion_nav_view, R.id.opinion_drawer_item)

        val opinionHandler = OpinionHandler(this)

        opinionHandler.getOpinionForm {
            response ->
            run {
                if (response.getInt("success") == 1) {
                    System.out.println(response)
                    val body = response.getJSONArray("body")
                    if(body.length() != 0){
                        opinionHandler.fillFormFromJSON(body.getJSONObject(0))
                        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")
                        val date = formatter.format(parser.parse(body.getJSONObject(0).getString("updated_at")))
                        Toast.makeText(this,
                            "Ostatnia ankieta została wypełniona: $date. Wróć za miesiąc", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        opinion_update_button.setOnClickListener {
            run {
                opinionHandler.fillFormFromUI()
                opinionHandler.updateOpinion { response ->
                    run {
                        if(response.getInt("success") == 1){
                            Toast.makeText(this, "Pomyślnie przesłano ankietę.", Toast.LENGTH_SHORT).show()
                            opinionHandler.fillUIForm()
                        }else{
                            Toast.makeText(this, "Nie udało się przesłać ankiety. Spróbuj ponownie.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}
