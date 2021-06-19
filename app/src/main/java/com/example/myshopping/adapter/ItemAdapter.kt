package com.example.myshopping.adapter

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myshopping.R
import com.example.myshopping.model.Purchase
import com.example.myshopping.model.PurchaseStatus

class ItemAdapter (var list : List<Purchase>):RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(){
        var adapterClick: AdapterClick? = null


    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(purchase: Purchase){
            print(purchase.name)
            itemView.findViewById<TextView>(R.id.productName).apply {
                text = purchase.name
            }
            itemView.findViewById<TextView>(R.id.productStatus).apply {
                text = purchase.status.label
            }

            itemView.setOnClickListener{
                adapterClick?.onClick(it,purchase)
            }
            if (purchase.status.equals(PurchaseStatus.PURCHASED)){
                itemView.setBackgroundColor(Color.parseColor("#97F42B"))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.item_purchases, parent, false)
        return ItemViewHolder(inflate);
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
       holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    interface AdapterClick{
        fun onClick(view:View, purchase: Purchase)
    }
}