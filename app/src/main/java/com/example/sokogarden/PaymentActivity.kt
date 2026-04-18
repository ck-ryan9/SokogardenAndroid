package com.example.sokogarden

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.loopj.android.http.RequestParams

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        Find the views by use of their IDs
        val txtname = findViewById<TextView>(R.id.txtProductName)
        val textcost = findViewById<TextView>(R.id.txtProductCost)
        val imgProduct = findViewById<ImageView>(R.id.imgProduct)

//        Retreive the data first from the previous activity
        val name = intent.getStringExtra("product_name")
        val cost = intent.getIntExtra("product_cost", 0)
        val product_photo = intent.getStringExtra("product_photo")

//        Update the textViews with the data passed from the previous Activity
        txtname.text = name
        textcost.text = "Ksh $cost"

//        Specify the image URL
        val imageUrl = "https://mutukuryan.alwaysdata.net/static/images/$product_photo"

        Glide.with(this)
            .load(imageUrl )
            .placeholder(R.drawable.ic_launcher_background) // Make sure you have a placeholder image
            .into(imgProduct)

//        Find the EditText and the poy now button by use of their IDs
        val phone = findViewById<EditText>(R.id.phone)
        val btnPay = findViewById<Button>(R.id.pay)

//        Set click listener on the button
        btnPay.setOnClickListener {
//            Specify the API endpoint for making the payment
            val api = "https://mutukuryan.alwaysdata.net/api/mpesa_payment"

//            Create a request params
            val data = RequestParams()

//            Insert data into the RequestParams
            data.put("amount",cost )
            data.put("phone", phone.text.toString().trim())

//            Import the helper class
            val helper = ApiHelper(applicationContext)

//            Access the post function inside of the helper class
            helper.post(api, data)

//            Clear the phone number from the edittext
            phone.text.clear()
        }



    }
}