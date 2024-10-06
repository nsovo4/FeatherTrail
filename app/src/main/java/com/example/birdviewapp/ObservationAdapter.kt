package com.example.birdviewapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ObservationAdapter(private var observations: MutableList<String>) :
    RecyclerView.Adapter<ObservationAdapter.ObservationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObservationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_observation, parent, false)
        return ObservationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ObservationViewHolder, position: Int) {
        holder.bind(observations[position])
    }

    override fun getItemCount(): Int = observations.size

    fun updateData(newObservations: List<String>) {
        observations.clear()
        observations.addAll(newObservations)
        notifyDataSetChanged()
    }

    class ObservationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val observationText: TextView = itemView.findViewById(R.id.observationText)

        fun bind(observation: String) {
            observationText.text = observation
        }
    }
}