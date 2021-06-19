package com.example.myshopping

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import com.example.myshopping.constants.ActivityPurchaseStatus
import com.example.myshopping.model.Purchase
import com.example.myshopping.model.PurchaseStatus
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DetailPurchaseActivity : AppCompatActivity() {
    lateinit var idDocument:String;
    lateinit var editTextName:EditText;
    var db = Firebase.firestore


    var  status : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_purchase)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        val purchase:Purchase = intent.getSerializableExtra("purchase") as Purchase
        editTextName =  findViewById<EditText>(R.id.textNameEdit).apply {
            setText(purchase.name)
        }
        val toggle: Switch = findViewById<Switch>(R.id.switch1)
        toggle.isChecked = purchase.status == PurchaseStatus.PURCHASED
        toggle.setOnCheckedChangeListener { _, isChecked ->
            status = isChecked
        }
        idDocument = purchase.id.toString()
    }


    fun shared(view:View){
        if (!editTextName.text.isEmpty()){
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Amigo pode comprar isso ${editTextName.text} para completar a nossa lista de comprar.")
                type = "text/plain"
            }

            if (sendIntent.resolveActivity(packageManager) != null) {
                startActivity(sendIntent)
            }
        }else{
            Toast.makeText(this,"De um nome a sua compra para compartilhar",Toast.LENGTH_LONG).show()
        }

    }

    fun delete(view:View){
        db.collection(Purchase.PurchaseConstants.COLLECTION).document(idDocument)
            .delete()
            .addOnSuccessListener {
                setResult(ActivityPurchaseStatus.DELETE_PURCHASE)
                finish() }
            .addOnFailureListener {     Toast.makeText(this,"Erro ao deletar",Toast.LENGTH_LONG).show() }
    }

    fun update(view: View){
        if (!editTextName.text.isEmpty()){
            var purchaseStatus :PurchaseStatus = if(status==true) PurchaseStatus.PURCHASED else PurchaseStatus.NOT_PURCHASED;
            var purchase = Purchase(null, editTextName.text.toString(), purchaseStatus)

            db.collection(Purchase.PurchaseConstants.COLLECTION).document(idDocument)
                .set(purchase.toHashMap())
                .addOnSuccessListener {
                    setResult(ActivityPurchaseStatus.UPDATE_PURCHASE)
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this,"Erro",Toast.LENGTH_LONG).show()
                }

        }else{
            Toast.makeText(this,"De um nome a sua compra para salvar",Toast.LENGTH_LONG).show()
        }
    }
}