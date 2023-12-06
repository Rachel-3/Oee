package com.example.oee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oee.databinding.ActivityHistoryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    private var binding: ActivityHistoryBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryBinding.inflate(layoutInflater)

        setContentView(binding?.root)


        setSupportActionBar(binding?.toolbarHistoryActivity)

        val actionbar = supportActionBar
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.title = "HISTORY"
        }

        binding?.toolbarHistoryActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        val dao = (application as WorkOutApp).db.historyDao()
        getAllCompletedDates(dao)
    }

    private fun getAllCompletedDates(historyDao: HistoryDao) {
        lifecycleScope.launch {
          historyDao.fetchALlDates().collect { allCompletedDatesList->

              if (allCompletedDatesList.isNotEmpty()) {

                  binding?.tvHistory?.visibility = View.VISIBLE
                  binding?.rvHistory?.visibility = View.VISIBLE
                  binding?.tvNoDataAvailable?.visibility = View.GONE


                  binding?.rvHistory?.layoutManager = LinearLayoutManager(this@HistoryActivity)


                  val dates = ArrayList<String>()
                  for (date in allCompletedDatesList){
                      dates.add(date.date)
                  }
                  val historyAdapter = HistoryAdapter(ArrayList(dates))


                  binding?.rvHistory?.adapter = historyAdapter
              } else {
                  binding?.tvHistory?.visibility = View.GONE
                  binding?.rvHistory?.visibility = View.GONE
                  binding?.tvNoDataAvailable?.visibility = View.VISIBLE
              }

          }
       }

    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }
}