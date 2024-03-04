package com.zoro.covid_19tracker

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class MainActivity : AppCompatActivity() {


    lateinit var worldCases: TextView
    lateinit var worldRecover: TextView
    lateinit var worldDeaths: TextView

    lateinit var countryCases: TextView
    lateinit var countryRecover: TextView
    lateinit var countryDeaths: TextView

    lateinit var state: RecyclerView

    lateinit var stateAdapter: StateAdapter
    lateinit var stateList: List<model>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        worldCases = findViewById(R.id.total_cases)
        worldRecover = findViewById(R.id.total_Recovered)
        worldDeaths = findViewById(R.id.total_deaths)

        countryCases = findViewById(R.id.total_indian_cases)
        countryRecover = findViewById(R.id.total_indian_Recovered)
        countryDeaths = findViewById(R.id.total_indian_deaths)

        state = findViewById(R.id.stateRecord)
        stateList = ArrayList<model>()
    }

    private fun getStateInfo() {

        val url = "https://api.rootnet.in/covid19-in/stats/latest"
        val queue = Volley.newRequestQueue(this@MainActivity)
        val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            try {
                val dataObj = response.getJSONObject("data")
                val summaryObj = dataObj.getJSONObject("summary")
                val cases: Int = summaryObj.getInt("total")
                val recovered: Int = summaryObj.getInt("discharged")
                val deaths: Int = summaryObj.getInt("deaths")

                countryCases.text = cases.toString()
                countryRecover.text = cases.toString()
                countryDeaths.text = cases.toString()

                val regionArray = dataObj.getJSONArray("regional")
                for (i in 0 until regionArray.length()) {
                    val regionObj = regionArray.getJSONObject(i)
                    val stateName: String = regionObj.getString("loc")
                    val cases: Int = regionObj.getInt("totalConfirmed")
                    val recovered: Int = regionObj.getInt("discharged")
                    val deaths: Int = regionObj.getInt("deaths")

                    val stateModel = model(stateName, cases, recovered, deaths)
                    stateList = stateList + stateModel
                }
                stateAdapter = StateAdapter(stateList)
                state.layoutManager = LinearLayoutManager(this)
                state.adapter = stateAdapter

            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }, { error ->
            {
                Toast.makeText(this, "Fail to get data", Toast.LENGTH_SHORT).show()
            }
        })
        queue.add(request)
    }
}