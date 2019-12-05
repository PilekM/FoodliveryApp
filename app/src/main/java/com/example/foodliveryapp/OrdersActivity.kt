package com.example.foodliveryapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.example.foodliveryapp.database.RequestHandler
import com.example.foodliveryapp.database.Services
import com.example.foodliveryapp.orders_recycler.Order
import com.example.foodliveryapp.orders_recycler.OrdersListAdapter
import kotlinx.android.synthetic.main.activity_orders.*

class OrdersActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {

        val session = Session(applicationContext)
        val orderHandler = OrderHandler(applicationContext)

        var orderList = ArrayList<Order>()
        val order = Order("kaszubska 1", "9:15", "KFC Pasaż Grunwaldzki", "pl. Grunwaldzki 22", "44.99", "Gotówka");
        val order1 = Order("kaszubska 1", "9:15", "KFC Pasaż Grunwaldzki", "pl. Grunwaldzki 22", "44.99", "Gotówka");
        val order2 = Order("kaszubska 1", "9:15", "KFC Pasaż Grunwaldzki", "pl. Grunwaldzki 22", "44.99", "Gotówka");
        orderList.add(order)
        orderList.add(order1)
        orderList.add(order2)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_orderlist)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter2 = OrdersListAdapter(orderList, this)
        recyclerView.adapter = adapter2


        logout_button.setOnClickListener {
            session.restartSession()
            finish()
        }

        orderHandler.getOrders{response ->
        run {

            if(response.getInt("success") == 1) {
                orderList = orderHandler.convertJSONtoOrders(response.getJSONArray("body"))
                println(response)
                println("this is order list" + orderList)
                val adapter = OrdersListAdapter(orderList, this)

                recyclerView.swapAdapter(adapter, false)
            }

        }}
    }
}
