package com.example.foodliveryapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.foodliveryapp.log.ProfileHandler
import com.example.foodliveryapp.ui.DrawerLayoutBuilder
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val drawerLayoutBuilder = DrawerLayoutBuilder(this)
        drawerLayoutBuilder.build(
            R.id.profile_drawer_layout,
            R.id.profile_nav_view,
            R.id.profile_drawer_item
        )

        val profileHandler = ProfileHandler(this)

        profile_pass_update_button.setOnClickListener {
            run {
                if (profileHandler.checkPasswords()) {
                    profileHandler.sendPassword { response ->
                        run {
                            if (response.getInt("success") == 1) {
                                Toast.makeText(
                                    this,
                                    "Hasło zmienione poprawnie.",
                                    Toast.LENGTH_LONG
                                ).show()
                                profileHandler.resetPasswords()
                            } else {
                                if (response.getString("error").equals("password")) {
                                    Toast.makeText(
                                        this,
                                        "Podane aktualne hasło jest nieprawidłowe.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    profileHandler.resetPasswords()
                                }
                            }

                        }
                    }
                }
            }
        }

        profile_phone_update_button.setOnClickListener {
            run {
                if (profileHandler.checkPhones()) {
                    profileHandler.sendPhone { response ->
                        run {
                            if (response.getInt("success") == 1) {
                                Toast.makeText(
                                    this,
                                    "Numer telefonu zmieniony poprawnie.",
                                    Toast.LENGTH_LONG
                                ).show()
                                profileHandler.resetPhones()
                            } else {
                                if (response.getString("error").equals("phone")) {
                                    Toast.makeText(
                                        this,
                                        "Podany aktualny numer telefonu jest niepoprawny.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    profileHandler.resetPhones()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
