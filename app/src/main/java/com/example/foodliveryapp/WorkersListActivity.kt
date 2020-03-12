package com.example.foodliveryapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodliveryapp.log.WorkersListHandler
import com.example.foodliveryapp.recycler.workers.WorkersListAdapter
import com.example.foodliveryapp.ui.DrawerLayoutBuilder

class WorkersListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workers_list)

        val drawerLayoutBuilder = DrawerLayoutBuilder(this)
        drawerLayoutBuilder.build(
            R.id.worker_list_drawer_layout,
            R.id.worker_list_nav_view,
            R.id.workers_drawer_item
        )

        val workersListHandler =
            WorkersListHandler(applicationContext)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_workers_list)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        workersListHandler.getWorkers { response ->
            run {
                if (response.getInt("success") == 1) {
                    val workersList =
                        workersListHandler.convertJSONToWorkers(response.getJSONArray("body"))

                    val adapter =
                        WorkersListAdapter(
                            workersList,
                            this
                        )

                    recyclerView.swapAdapter(adapter, false)
                }
            }
        }
    }
}
