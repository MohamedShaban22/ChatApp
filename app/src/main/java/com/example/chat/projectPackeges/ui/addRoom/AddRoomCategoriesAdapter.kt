package com.example.chat.projectPackeges.ui.addRoom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.chat.databinding.RoomCategoieItemBinding

class AddRoomCategoriesAdapter(val items:List<CategoriesItem>): BaseAdapter() {
    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return items[position].id.toLong()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View? {
        val viewHolder: ViewHolder
            if(view==null){
                // create view holder
                val itemBinding=RoomCategoieItemBinding.inflate(LayoutInflater.from(parent?.context),
                    parent,false)
                viewHolder=ViewHolder(itemBinding)
                itemBinding.root.tag=viewHolder
            }
        else{
            viewHolder=view.tag as ViewHolder
        }
        viewHolder.itemBinding.categoryImg.setImageResource(items[position].categoryImage)
        viewHolder.itemBinding.categoryName.text=items[position].categoryName
        return viewHolder.itemBinding.root
    }
    class ViewHolder(val itemBinding:RoomCategoieItemBinding)
}