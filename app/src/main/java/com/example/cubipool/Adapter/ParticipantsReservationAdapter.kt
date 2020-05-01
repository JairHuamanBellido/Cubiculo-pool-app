package com.example.cubipool.Adapter

import android.app.Activity
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cubipool.R
import com.example.cubipool.ReservationDetailActivity
import com.example.cubipool.service.reservation.ParticipantsReservation
import com.example.cubipool.service.reservation.ReservationDetail
import kotlinx.android.synthetic.main.activity_participant_reservation_prototype.*

class ParticipantsReservationAdapter (
    val arrParticipants:ArrayList<ParticipantsReservation>, val activity: Activity)
    :RecyclerView.Adapter<ParticipantsReservationAdapter.ViewHolder>()
{
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        private val nameParticipant =  itemView.findViewById<TextView>(R.id.tv_participantName)
        private val codeParticipant =  itemView.findViewById<TextView>(R.id.tv_participantCode)
        private val imageParticipant =  itemView.findViewById<ImageView>(R.id.img_participant)
        val linearLayout =  itemView.findViewById<LinearLayout>(R.id.prototypeParticipant)

        fun bind(participantsReservation: ParticipantsReservation,activity: Activity){
            nameParticipant.text = participantsReservation.nombre
            codeParticipant.text = participantsReservation.codigo
            Glide.with(activity).load("https://intranet.upc.edu.pe/programas/Imagen/Fotos/Upc/0540${participantsReservation.codigo.removeRange(0,1)}.jpg").into(imageParticipant)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater =  LayoutInflater.from(parent.context)
        val view=  layoutInflater.inflate(R.layout.activity_participant_reservation_prototype,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arrParticipants.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(arrParticipants[position],activity)
    }
}