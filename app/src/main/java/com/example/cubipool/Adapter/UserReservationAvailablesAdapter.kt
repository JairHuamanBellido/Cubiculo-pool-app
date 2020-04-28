package com.example.cubipool.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cubipool.Interfaces.OnReservationAvailableListener
import com.example.cubipool.R
import com.example.cubipool.service.user.UserReservationsAvailables
import kotlinx.android.synthetic.main.activity_reservations_availables_prototype.view.*

class UserReservationAvailablesAdapter(
    val arrUserReservationAvailables: ArrayList<UserReservationsAvailables>,
    val itemClickListener:OnReservationAvailableListener


):RecyclerView.Adapter<UserReservationAvailablesAdapter.ViewHolder>(){


    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        private val nameCubicle =  itemView.findViewById<TextView>(R.id.tv_reservationAvailableCubicleName)
        private val dayReservation  = itemView.findViewById<TextView>(R.id.tv_reservationAvailableDay)
        private val startTime =  itemView.findViewById<TextView>(R.id.tv_reservationAvailableStartTime)
        private val endTime =  itemView.findViewById<TextView>(R.id.tv_reservationAvailableEndTime)

        val linearLayout = itemView.findViewById<LinearLayout>(R.id.prototypeReservationAvailable)

        fun bind(userReservationsAvailables: UserReservationsAvailables, onReservationAvailableListener: OnReservationAvailableListener){
            nameCubicle.text =  userReservationsAvailables.name
            dayReservation.text =  "(${userReservationsAvailables.day})"
            startTime.text =  userReservationsAvailables.startTime
            endTime.text =  userReservationsAvailables.endTime
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater =  LayoutInflater.from(parent.context)
        val view =  layoutInflater.inflate(R.layout.activity_reservations_availables_prototype,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arrUserReservationAvailables.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.linearLayout.setOnClickListener {itemClickListener.onItemSelected(arrUserReservationAvailables[position]) }
    holder.bind(arrUserReservationAvailables[position],itemClickListener)
    }
}