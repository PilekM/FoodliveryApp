package com.example.foodliveryapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.foodliveryapp.data.order.DetailedOrder
import com.example.foodliveryapp.log.DetailedOrderHandler
import com.example.foodliveryapp.ui.DrawerLayoutBuilder


//TODO na horizontal zrobić split screen lista zamówien|szczegóły zamowienia
class OrderDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        val drawerLayoutBuilder = DrawerLayoutBuilder(this)
        drawerLayoutBuilder.build(R.id.detail_drawer_layout, R.id.detail_nav_view, 0)

        var orderId = intent.getStringExtra("orderId")
        //TODO przekazać view i przypisać textview w klasie
        var detailedOrder = DetailedOrder(
            applicationContext,
            findViewById(R.id.od_os_value),
            findViewById(R.id.od_oc_value),
            findViewById(R.id.od_ca_value),
            findViewById(R.id.od_cn_value),
            findViewById(R.id.od_cp_value),
            findViewById(R.id.od_cnote_value),
            findViewById(R.id.od_oi_value),
            findViewById(R.id.od_price_value),
            findViewById(R.id.od_price_type),
            findViewById(R.id.od_ra_value),
            findViewById(R.id.od_rp_value),
            findViewById(R.id.od_op_value)
        )
        val detailedOrderHandler =
            DetailedOrderHandler(
                applicationContext,
                orderId,
                detailedOrder
            )
        val officePhoneLayout = findViewById<LinearLayout>(R.id.od_office_phone)
        val restPhoneLayout = findViewById<LinearLayout>(R.id.od_rest_phone)
        val custPhoneLayout = findViewById<LinearLayout>(R.id.od_cust_phone)
        val custAddressLayout = findViewById<LinearLayout>(R.id.od_cust_address)

        val officePhoneValue = findViewById<TextView>(R.id.od_op_value)
        val restPhoneValue = findViewById<TextView>(R.id.od_rp_value)
        val custPhoneValue = findViewById<TextView>(R.id.od_cp_value)
        val custAddressValue = findViewById<TextView>(R.id.od_ca_value)


        custAddressLayout.setOnClickListener {
            val mapUri =
                Uri.parse("geo:0,0?q=" + Uri.encode(custAddressValue.text.toString()))
            val mapIntent = Intent(Intent.ACTION_VIEW, mapUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        officePhoneLayout.setOnClickListener {
            val diallIntent = Intent(Intent.ACTION_DIAL)
            diallIntent.setData(Uri.parse("tel:" + officePhoneValue.text.toString()))
            startActivity(diallIntent)
        }

        restPhoneLayout.setOnClickListener {
            val diallIntent = Intent(Intent.ACTION_DIAL)
            diallIntent.setData(Uri.parse("tel:" + restPhoneValue.text.toString()))
            startActivity(diallIntent)
        }

        custPhoneLayout.setOnClickListener {
            val diallIntent = Intent(Intent.ACTION_DIAL)
            diallIntent.setData(Uri.parse("tel:" + custPhoneValue.text.toString()))
            startActivity(diallIntent)
        }



        fun adjustToOrderStatus(orderStatus: String) {
            var leftButton = findViewById<Button>(R.id.od_left_button)
            var rightButton = findViewById<Button>(R.id.od_right_button)

            //TODO ZMNIEJSZYC REDUNDANCJE KODU!!!!!!!!!!!
            when (orderStatus) {
                "0" -> {
                    leftButton.setText("Zaakceptuj")
                    rightButton.setText("Odrzuć")

                    rightButton.setOnClickListener {
                        detailedOrderHandler.denyOrder { response ->
                            run {
                                if (response.getInt("success") == 1) {
                                    this.finish()
                                    val intent = Intent(this, OrdersActivity::class.java)
                                    startActivity(intent)
                                }
                            }
                        }
                    }

                    leftButton.setOnClickListener {
                        detailedOrderHandler.acceptOrder { response ->
                            run {
                                if (response.getInt("success") == 1) {
                                    detailedOrderHandler.getOrder { response ->
                                        run {
                                            var orderStatus =
                                                response.getJSONArray("body").getJSONObject(0)
                                                    .getString("order_status")
                                            adjustToOrderStatus(orderStatus)
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
                "1" -> {
                    leftButton.visibility = View.INVISIBLE
                    rightButton.setText("Czekam w restauracji")

                    rightButton.setOnClickListener {
                        detailedOrderHandler.waitRestOrder { response ->
                            run {
                                if (response.getInt("success") == 1) {
                                    detailedOrderHandler.getOrder { response ->
                                        run {
                                            var orderStatus =
                                                response.getJSONArray("body").getJSONObject(0)
                                                    .getString("order_status")
                                            adjustToOrderStatus(orderStatus)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                "2" -> {
                    leftButton.visibility = View.INVISIBLE

                    rightButton.visibility = View.VISIBLE
                    rightButton.setText("Dostarczam")

                    rightButton.setOnClickListener {
                        detailedOrderHandler.deliverOrder {
                            detailedOrderHandler.getOrder { response ->
                                run {
                                    var orderStatus =
                                        response.getJSONArray("body").getJSONObject(0)
                                            .getString("order_status")
                                    adjustToOrderStatus(orderStatus)
                                }
                            }
                        }
                    }
                }

                "3" -> {
                    leftButton.visibility = View.VISIBLE
                    leftButton.setText("Czekam w restauracji")

                    rightButton.visibility = View.VISIBLE
                    rightButton.setText("U klienta")

                    leftButton.setOnClickListener {
                        detailedOrderHandler.waitRestOrder { response ->
                            run {
                                if (response.getInt("success") == 1) {
                                    detailedOrderHandler.getOrder { response ->
                                        run {
                                            var orderStatus =
                                                response.getJSONArray("body").getJSONObject(0)
                                                    .getString("order_status")
                                            adjustToOrderStatus(orderStatus)
                                        }
                                    }
                                }
                            }
                        }
                    }

                    rightButton.setOnClickListener {
                        detailedOrderHandler.driveCompletedOrder { response ->
                            run {
                                if (response.getInt("success") == 1) {
                                    detailedOrderHandler.getOrder { response ->
                                        run {
                                            var orderStatus =
                                                response.getJSONArray("body").getJSONObject(0)
                                                    .getString("order_status")
                                            adjustToOrderStatus(orderStatus)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                "4" -> {
                    leftButton.visibility = View.VISIBLE
                    leftButton.setText("Dostarczone")

                    rightButton.visibility = View.VISIBLE
                    rightButton.setText("Niedostarczone")

                    leftButton.setOnClickListener {
                        detailedOrderHandler.deliveredOrder { response ->
                            run {
                                if (response.getInt("success") == 1) {
                                    detailedOrderHandler.getOrder { response ->
                                        run {
                                            var orderStatus =
                                                response.getJSONArray("body").getJSONObject(0)
                                                    .getString("order_status")
                                            adjustToOrderStatus(orderStatus)
                                        }
                                    }
                                }
                            }
                        }
                    }

                    rightButton.setOnClickListener {
                        detailedOrderHandler.notDeliveredOrder { response ->
                            run {
                                if (response.getInt("success") == 1) {
                                    detailedOrderHandler.getOrder { response ->
                                        run {
                                            var orderStatus =
                                                response.getJSONArray("body").getJSONObject(0)
                                                    .getString("order_status")
                                            adjustToOrderStatus(orderStatus)
                                        }
                                    }
                                }
                            }
                        }
                    }

                }

                "5" -> {

                    leftButton.visibility = View.VISIBLE
                    leftButton.setText("Niedostarczone")

                    rightButton.visibility = View.VISIBLE
                    rightButton.setText("Zamknij zamówienie")

                    leftButton.setOnClickListener {

                        detailedOrderHandler.notDeliveredOrder { response ->
                            run {
                                if (response.getInt("success") == 1) {
                                    detailedOrderHandler.getOrder { response ->
                                        run {
                                            var orderStatus =
                                                response.getJSONArray("body").getJSONObject(0)
                                                    .getString("order_status")
                                            adjustToOrderStatus(orderStatus)
                                        }
                                    }
                                }
                            }
                        }

                    }

                    rightButton.setOnClickListener {

                        detailedOrderHandler.completedOrder { response ->
                            run {
                                if (response.getInt("success") == 1) {
                                    detailedOrderHandler.getOrder { response ->
                                        run {
                                            var orderStatus =
                                                response.getJSONArray("body").getJSONObject(0)
                                                    .getString("order_status")
                                            adjustToOrderStatus(orderStatus)
                                        }
                                    }
                                }
                            }
                        }


                    }

                }

                "6" -> {
                    leftButton.visibility = View.VISIBLE
                    leftButton.setText("Dostarczone")

                    rightButton.visibility = View.VISIBLE
                    rightButton.setText("Zamknij zamówienie")

                    leftButton.setOnClickListener {

                        detailedOrderHandler.deliveredOrder { response ->
                            run {
                                if (response.getInt("success") == 1) {
                                    detailedOrderHandler.getOrder { response ->
                                        run {
                                            var orderStatus =
                                                response.getJSONArray("body").getJSONObject(0)
                                                    .getString("order_status")
                                            adjustToOrderStatus(orderStatus)
                                        }
                                    }
                                }
                            }
                        }

                    }

                    rightButton.setOnClickListener {

                        detailedOrderHandler.completedOrder { response ->
                            run {
                                if (response.getInt("success") == 1) {
                                    detailedOrderHandler.getOrder { response ->
                                        run {
                                            var orderStatus =
                                                response.getJSONArray("body").getJSONObject(0)
                                                    .getString("order_status")
                                            adjustToOrderStatus(orderStatus)
                                        }
                                    }
                                }
                            }
                        }

                    }
                }

                "7" -> {
                    leftButton.visibility = View.INVISIBLE
                    rightButton.visibility = View.INVISIBLE
                }



            }
        }


        fun updateOrderData() {
            detailedOrderHandler.getOrder { response ->
                run {
                    var orderStatus =
                        response.getJSONArray("body").getJSONObject(0).getString("order_status")
                    adjustToOrderStatus(orderStatus)
                }
            }
        }


        updateOrderData()

    }
}
