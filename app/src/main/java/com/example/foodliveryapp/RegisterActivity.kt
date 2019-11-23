package com.example.foodliveryapp

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {



    val regHandler = RegisterHandler(this)

    //TODO podkreślić na czerwono editText z błędnymi
    //TODO ikonki(dymki) z wyjasnieniem zasad obowiazujacych w danym polu tekstowym

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        if(actionBar != null) {
            actionBar.setTitle("Rejestracja")
        }

        register_button.setOnClickListener {

            val regForm = RegisterForm()

            regForm.name = register_name.text.toString()
            regForm.surname = register_surname.text.toString()
            regForm.username = register_username.text.toString()
            regForm.password = register_password.text.toString()
            regForm.phoneNumber = register_phone.text.toString()
            regForm.email = register_email.text.toString()
            regForm.isChecked = register_checkbox.isChecked

            regHandler.setRegisterForm(regForm)
            regHandler.setActivity(this)

            if(regHandler.validateRegisterForm()){
                //send request
                regForm.prepareFormToSend()
                regHandler.setRegisterForm(regForm)
                regHandler.sendRegisterForm {response ->
                    run {
                        val success = response.getInt("success")

                        if (success == 1) {
                            System.out.println(response.get("body"))

                            showInfoSnackbar(R.id.register_layout, "REJESTRACJA UDANA", Color.GREEN)
                            clearRegisterFormFields()

                        } else {

                            showInfoSnackbar(R.id.register_layout, "REJESTRACJA NIEUDANA", Color.RED)

                            val error = response.get("error").toString()
                            System.out.println(error)
                            handleRegisterError(error)
                        }
                    }
                }

            }
        }
    }

    fun clearRegisterFormFields(){
        register_username.setText("")
        register_password.setText("")
        register_phone.setText("")
        register_email.setText("")
        register_name.setText("")
        register_surname.setText("")
        register_checkbox.isChecked = false
    }

    fun showInfoSnackbar(itemId: Int, information: String, color: Int){

        val sb = Snackbar.make(findViewById(itemId), information, Snackbar.LENGTH_LONG)

        sb.setBackgroundTint(color)
        sb.show()

    }

    fun handleRegisterError(error: String){
        when(error){
            "user" -> {
                register_username.error = "Nazwa użytkownika jest już używana"
                register_username.setText("")
            }
            "phone" -> {
                register_phone.error = "Numer telefonu jest już przypisany do użytkownika"
                register_phone.setText("")
            }
            "email" -> {
                register_email.error = "Email jest już przypisany do użytkownika"
                register_email.setText("")
            }
        }
    }

}
