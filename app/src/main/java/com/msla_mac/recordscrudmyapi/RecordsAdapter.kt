package com.msla_mac.recordscrudmyapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


internal class RecordsAdapter(
    private var recordsList : List<RecordsItem>,
    private val listener: (position: Int) -> Unit
    ): RecyclerView.Adapter<RecordsAdapter.MyViewHolder>() {

    // TODO: finish adapter
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        var recordID : TextView = view.findViewById(R.id.recordRecordIDText)
        var item: TextView = view.findViewById(R.id.recordNameText)
        var description: TextView = view.findViewById(R.id.recordDescriptionText)
        var price: TextView = view.findViewById(R.id.recordPriceText)
        var rating: TextView = view.findViewById(R.id.recordRatingText)
        var dateModified: TextView = view.findViewById(R.id.recordDateModified)
        var dateCreated: TextView = view.findViewById(R.id.recordDateCreated)


        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View){
            listener(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.record_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        //get the data from the list and fill in the various CELL from fields
        val recordItem = recordsList[position]

        holder.recordID.text = recordItem.recordID.toString()
        holder.item.text = recordItem.name
        holder.description.text = recordItem.description
        holder.price.text = recordItem.price.toString().format("%2f", this)
        holder.rating.text = recordItem.rating.toString()
        holder.dateModified.text = recordItem.dateModified
        holder.dateCreated.text = recordItem.dateCreated
    }

    override fun getItemCount(): Int {
        return recordsList.size
    }
}
