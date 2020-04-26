package com.example.cubipool.Adapter

import android.content.SharedPreferences
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cubipool.Interfaces.OnCubicleClickListener
import com.example.cubipool.R

import com.example.cubipool.service.cubicle.CubicleResponse
import kotlinx.android.synthetic.main.activity_cubicle_item.view.*

class CubicleListAdapter(val arrCubicles:ArrayList<CubicleResponse>, val itemClickListener:OnCubicleClickListener): RecyclerView.Adapter<CubicleListAdapter.ViewHolder>() {

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

            private val name:TextView =  itemView.findViewById(R.id.cubicleName)
            private val date:TextView =  itemView.findViewById(R.id.dateCubicle)
            private val startTime:TextView = itemView.findViewById(R.id.startTimeCubicle)
            private val endTime:TextView =  itemView.findViewById(R.id.endTimeCubicle)
            val linearLayout = itemView.findViewById<LinearLayout>(R.id.cardCubicleLayout)

            fun bind(cubicleResponse:CubicleResponse, clickListener: OnCubicleClickListener){
                name.text =  cubicleResponse.name;
                date.text =  "(${cubicleResponse.day})"
                startTime.text =  cubicleResponse.startTime
                endTime.text =  cubicleResponse.endTime;
            }


        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.activity_cubicle_item, parent,false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return arrCubicles.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                if(arrCubicles[position].status){
                    paintCubicleCard(holder)
                }
                else{
                    removePaint(holder)
                }

            holder.linearLayout.setOnClickListener{selectCubicle(holder,position)}
            holder.bind(arrCubicles[position],itemClickListener)
        }

        private fun selectCubicle(holder:ViewHolder, position: Int){


            arrCubicles[position].status = !arrCubicles[position].status

            for (e in 0..arrCubicles.size-1){
                arrCubicles[e].status = position == e
                println(arrCubicles[e].status);
            }

            // Enviando datos al activity padre
            itemClickListener.onCubicleSelected(arrCubicles[position].id);

            notifyDataSetChanged()

        }

    private fun paintCubicleCard(holder: ViewHolder){
        holder.linearLayout.setBackgroundResource(R.drawable.cubicle_item_selected)
        holder.linearLayout.cubicleName.setTextColor(Color.parseColor("#ffffff"))
        holder.linearLayout.dateCubicle.setTextColor(Color.parseColor("#ffffff"))
        holder.linearLayout.startTimeCubicle.setTextColor(Color.parseColor("#ffffff"))
        holder.linearLayout.endTimeCubicle.setTextColor(Color.parseColor("#ffffff"))
    }
    private fun removePaint(holder: ViewHolder){
        holder.linearLayout.setBackgroundResource(R.drawable.card_cubicle)!!
        holder.linearLayout.cubicleName.setTextColor(Color.parseColor("#000000"))
        holder.linearLayout.dateCubicle.setTextColor(Color.parseColor("#000000"))
        holder.linearLayout.startTimeCubicle.setTextColor(Color.parseColor("#000000"))
        holder.linearLayout.endTimeCubicle.setTextColor(Color.parseColor("#000000"))
    }

}