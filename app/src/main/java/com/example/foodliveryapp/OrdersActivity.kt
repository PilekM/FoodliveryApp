package com.example.foodliveryapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.foodliveryapp.log.location.LocationIntent
import com.example.foodliveryapp.log.OrderHandler
import com.example.foodliveryapp.log.PermissionHandler
import com.example.foodliveryapp.log.permission.GPSandInternetChecker
import com.example.foodliveryapp.recycler.orders.Order
import com.example.foodliveryapp.recycler.orders.OrdersListAdapter
import com.example.foodliveryapp.ui.DrawerLayoutBuilder
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability


class OrdersActivity : AppCompatActivity(){

    lateinit var gpsInternetChecker: GPSandInternetChecker
    lateinit var orderHandler: OrderHandler
    lateinit var orderList: ArrayList<Order>
    lateinit var adapter: OrdersListAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var refreshLayout: SwipeRefreshLayout
    lateinit var permissionHandler: PermissionHandler

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)

        gpsInternetChecker =
            GPSandInternetChecker(this)
        orderList = ArrayList<Order>()
        adapter = OrdersListAdapter(
            orderList,
            this
        )

        orderHandler =
            OrderHandler(applicationContext)
        permissionHandler =
            PermissionHandler(this)
        permissionHandler.handlePermissions()

        val drawerLayoutBuilder = DrawerLayoutBuilder(this)
        drawerLayoutBuilder.build(R.id.order_drawer_layout, R.id.order_nav_view, R.id.orders_drawer_item)

        refreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_orders)
        refreshLayout.isRefreshing = true

        recyclerView = findViewById<RecyclerView>(R.id.recycler_orderlist)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.swapAdapter(adapter, false)

        refreshLayout.setOnRefreshListener {

            updateData()

        }

        updateData()

    }

    private fun updateData() {
        orderHandler.getOrders { response ->
            run {

                if (response.getInt("success") == 1) {
                    orderList =
                        orderHandler.convertJSONtoOrders(response.getJSONArray("body"))
                    adapter =
                        OrdersListAdapter(
                            orderList,
                            this
                        )

                    recyclerView.swapAdapter(adapter, false)

                    refreshLayout.isRefreshing = false
                }else {
                    if(response.getString("error").equals("empty")){
                        orderList = ArrayList<Order>()
                        adapter =
                            OrdersListAdapter(
                                orderList,
                                this
                            )
                        recyclerView.swapAdapter(adapter, false)
                        Toast.makeText(this, "Aktualnie brak przypiętych zamówień.", Toast.LENGTH_SHORT).show();
                    }
                    refreshLayout.isRefreshing = false
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        gpsInternetChecker.checkRequiredConnections();

        val serviceIntent = LocationIntent.getInstance(applicationContext).locationService
        startService(serviceIntent)

        updateData()

    }

    override fun onPause() {
        super.onPause()


    }

    override fun onResume() {
        super.onResume()

        if(!permissionHandler.checkPlayServices()){
            AlertDialog.Builder(this)
                .setMessage("Potrzebujesz Google Play Services żeby poprawnie używać aplikacji.")
                .show()
            finishAndRemoveTask()
        }
    }

}
