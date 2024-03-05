package com.zoro.covid_19tracker

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StateAdapter (private val stateList: List<model>) :
    RecyclerView.Adapter<StateAdapter.StateViewHolder>() {


    class StateViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView){

        val stateName:TextView = itemView.findViewById(R.id.idTVState)
        val casesTV:TextView= itemView.findViewById(R.id.idTVCases)
        val recoverTV:TextView= itemView.findViewById(R.id.idTVRecovered)
        val deathsTV:TextView= itemView.findViewById(R.id.idTVDeaths)

    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateViewHolder {
      val itemView = LayoutInflater.from(parent.context).inflate(R.layout.state_rv_item,parent,false)
        return StateViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: StateViewHolder, position: Int) {
       val stateData = stateList[position]
        holder.stateName.text = stateData.state.toString()
        holder.casesTV.text = stateData.cases.toString()
        holder.recoverTV.text = stateData.recovered.toString()
        holder.deathsTV.text = stateData.deaths.toString()
    }
    override fun getItemCount(): Int {
        return stateList.size
    }
}