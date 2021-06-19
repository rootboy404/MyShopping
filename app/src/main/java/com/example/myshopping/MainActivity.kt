package com.example.myshopping

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myshopping.adapter.ItemAdapter
import com.example.myshopping.constants.ActivityPurchaseStatus
import com.example.myshopping.model.Purchase
import com.example.myshopping.model.PurchaseStatus
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(),ItemAdapter.AdapterClick {

    var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getAllPurchase()

    }

    fun getAllPurchase(){
        db.collection(Purchase.PurchaseConstants.COLLECTION)
            .get()
            .addOnSuccessListener { result ->
                createList(result.documents.map { it-> Purchase(it.id,it.get(Purchase.PurchaseConstants.NAME).toString(),
                    PurchaseStatus.valueOf(it.get(Purchase.PurchaseConstants.STATUS).toString())) })

            }
            .addOnFailureListener { exception ->
                Toast.makeText(this,"erro ao carregar lista", Toast.LENGTH_LONG).show()
            }
    }

    private fun createList(list: List<Purchase>) {
        val viewManager = LinearLayoutManager(this)
        val viewAdapter = ItemAdapter(list)
        viewAdapter.adapterClick = this
        var recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

    }


    fun createProduct(view:View){
        val intent = Intent(this,CreatePurchaseActivity::class.java)
        resultLauncherCreate.launch(intent)
    }



    override fun onClick(view: View, purchase: Purchase) {
        intent = Intent(this, DetailPurchaseActivity::class.java)
        intent.putExtra("purchase",purchase)
        resultLauncherCreate.launch(intent)
    }


    var resultLauncherCreate =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode==ActivityPurchaseStatus.CREATE_PURCHASE){
                Toast.makeText(this,"Adicionado nova compra",Toast.LENGTH_LONG).show()
                getAllPurchase()
            }
            if(result.resultCode==ActivityPurchaseStatus.UPDATE_PURCHASE){
                Toast.makeText(this,"Atualizado uma compra",Toast.LENGTH_LONG).show()
                getAllPurchase()
            }
            if(result.resultCode==ActivityPurchaseStatus.DELETE_PURCHASE){
                Toast.makeText(this,"deletado uma compra",Toast.LENGTH_LONG).show()
                getAllPurchase()
            }
        }
}