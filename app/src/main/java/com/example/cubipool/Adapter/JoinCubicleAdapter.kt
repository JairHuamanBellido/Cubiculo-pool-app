package com.example.cubipool.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cubipool.Interfaces.OnCubicleClickListener
import com.example.cubipool.R
import com.example.cubipool.service.offers.CreateOfferResponse
import kotlinx.android.synthetic.main.prototype_join.view.*

class JoinCubicleAdapter(val arroffers:ArrayList<CreateOfferResponse>, val itemClickListener: OnCubicleClickListener) :
    RecyclerView.Adapter<JoinCubiclePrototype>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JoinCubiclePrototype {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.prototype_join,parent,false)

        return JoinCubiclePrototype(view)
    }

    override fun getItemCount(): Int {
        return  arroffers.size
    }

    override fun onBindViewHolder(holder: JoinCubiclePrototype, position: Int) {
        holder.bind(arroffers[position],itemClickListener)
    }

}

class JoinCubiclePrototype(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tv_idCubicleJoin = itemView.tv_idCubicleJoin
    val ivAppleJoin = itemView.ivAppleJoin
    val ivBoardJoin = itemView.ivBoardJoin
    val tvTemaJoin = itemView.tvTemaJoin
    val tvStartTime = itemView.tvHoraInicioJoin
    val tvEndTime = itemView.tvHorafinJoin

    fun bind(offerResponse: CreateOfferResponse ,itemClickListener: OnCubicleClickListener){

        val apple = offerResponse.apple
        val board = offerResponse.pizarra
        tv_idCubicleJoin.text = offerResponse.cubiculoNombre
        tvTemaJoin.text = offerResponse.tema
        tvStartTime.text = offerResponse.horaInicio
        tvEndTime.text = offerResponse.horaFin

    }

}