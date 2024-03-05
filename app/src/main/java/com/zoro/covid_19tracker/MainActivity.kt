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


    private lateinit var worldCases: TextView
    private lateinit var worldRecover: TextView
    private lateinit var worldDeaths: TextView
    private lateinit var countryCases: TextView
    private lateinit var countryRecover: TextView
    private lateinit var countryDeaths: TextView
    private lateinit var state: RecyclerView
    private lateinit var stateAdapter: StateAdapter
    private lateinit var stateList: List<model>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        worldCases = findViewById(R.id.idTVWorldCases)
        worldRecover = findViewById(R.id.idTVWorldRecovered)
        worldDeaths = findViewById(R.id.idTVWorldDeaths)
        countryCases = findViewById(R.id.idTVIndiaCases)
        countryRecover = findViewById(R.id.idTVIndianRecovered)
        countryDeaths = findViewById(R.id.idTVIndianDeaths)
        state = findViewById(R.id.idRVStates)
        stateList = ArrayList<model>()
        getWorldInfo()
        getStateInfo()

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
                countryRecover.text = recovered.toString()
                countryDeaths.text = deaths.toString()

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
    private fun getWorldInfo()
    {




    }
}