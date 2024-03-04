package com.zoro.covid_19tracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StateAdapter (private val stateList: List<model>) :
    RecyclerView.Adapter<StateAdapter.StateViewHolder>() {


    class StateViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView){

        val stateName:TextView = itemView.findViewById(R.id.stateRecord)
        val stateCases:TextView= itemView.findViewById(R.id.state_cases)
        val stateRecovered:TextView= itemView.findViewById(R.id.state_recovered)
        val stateDeaths:TextView= itemView.findViewById(R.id.state_deaths)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateViewHolder {
      val itemView = LayoutInflater.from(parent.context).inflate(R.layout.state_rv_item,parent,false)
        return StateViewHolder(itemView)
    }

    override fun getItemCount(): Int {
       return stateList.size
    }

    override fun onBindViewHolder(holder: StateViewHolder, position: Int) {
       val stateData = stateList[position]
        holder.stateCases.text = stateData.cases.toString()
        holder.stateRecovered.text = stateData.recovered.toString()
        holder.stateName.text = stateData.state.toString()
        holder.stateDeaths.text = stateData.deaths.toString()

    }

}