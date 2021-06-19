package com.example.myshopping.model

import java.io.Serializable

class Purchase(val id: String?, val name:String, val status:PurchaseStatus):Serializable {


    fun toHashMap() :HashMap<String,String>{
        return hashMapOf(
            PurchaseConstants.NAME to name,
            PurchaseConstants.STATUS to status.toString()
        )
    }

    object PurchaseConstants{
        const val COLLECTION = "purchases"
        const val NAME = "name"
        const val STATUS = "status"
    }
}