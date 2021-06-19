package com.example.myshopping

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myshopping.constants.ActivityPurchaseStatus
import com.example.myshopping.model.Purchase
import com.example.myshopping.model.PurchaseStatus
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class CreatePurchaseActivity : AppCompatActivity() {

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.activity_create_purchase)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun savePurchase(view:View){
        var findViewById = findViewById<TextView>(R.id.textNameEdit)
        if(!findViewById.text.isEmpty()){
            var purchase =
                Purchase(null, findViewById.text.toString(), PurchaseStatus.NOT_PURCHASED)

            db.collection(Purchase.PurchaseConstants.COLLECTION)
                .add(purchase.toHashMap())
                .addOnSuccessListener { documentReference ->
                    setResult(ActivityPurchaseStatus.CREATE_PURCHASE)
                    finish();
                }
                .addOnFailureListener { e ->
                    setResult(Activity.RESULT_CANCELED)
                    finish();
                }
        }else{
            Toast.makeText(this,"Campo de nome não pode ser nulo",Toast.LENGTH_LONG).show()
        }

    }

    fun shared(view: View) {}
    fun delete(view: View) {}
    fun update(view: View) {}


}