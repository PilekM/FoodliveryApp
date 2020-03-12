package com.example.foodliveryapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodliveryapp.log.RestaurantsListHandler
import com.example.foodliveryapp.recycler.restaurants.RestaurantsListAdapter
import com.example.foodliveryapp.ui.DrawerLayoutBuilder

class RestaurantsListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurants_list)

        val drawerLayoutBuilder = DrawerLayoutBuilder(this)
        drawerLayoutBuilder.build(R.id.rest_list_drawer_layout, R.id.rest_list_nav_view,R.id.restaurants_drawer_item)

        val restaurantsListHandler =
            RestaurantsListHandler(
                applicationContext
            )

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_restaurants_list)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        restaurantsListHandler.getRestaurants { response ->
            run {

                if(response.getInt("success") == 1) {
                    val restaurantsList = restaurantsListHandler.convertJSONToRestaurants(response.getJSONArray("body"))

                    val adapter =
                        RestaurantsListAdapter(
                            restaurantsList,
                            this
                        )

                    recyclerView.swapAdapter(adapter, false)
                }
            }
        }

    }
}
