package com.example.chat.projectPackeges.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.databinding.RoomItemBinding
import com.example.chat.projectPackeges.model.Room

class RoomsAdapter(var roomList:List<Room?>?=null):RecyclerView.Adapter<RoomsAdapter.RoomsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomsViewHolder {
        val itemBinding=RoomItemBinding
            .inflate(LayoutInflater.from(parent.context),parent,false)
        return RoomsViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return roomList?.size?:0
    }

    override fun onBindViewHolder(holder: RoomsViewHolder, position: Int) {
        holder.bind(roomList?.get(position))
        holder.itemView.setOnClickListener{
            onItemClickListener?.onItemClick(position,roomList!![position])
        }

    }

    fun bindRooms(rooms: List<Room>?) {
        roomList=rooms
        notifyDataSetChanged()

    }

    class RoomsViewHolder(private val itemBinding:RoomItemBinding):RecyclerView.ViewHolder(itemBinding.root){
       fun bind(room:Room?){
           itemBinding.roomName.text=room?.tittle
           itemBinding.roomImg.setImageResource(room?.getRoomImageById()?:1)
       }
    }

     var onItemClickListener:OnItemClickListener?=null
    fun interface OnItemClickListener{
        fun onItemClick(position:Int, room:Room?)
    }
}