package com.utsav.cafemanagementsystem.`object`

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.utsav.cafemanagementsystem.entity.FoodItem
import com.bumptech.glide.Glide
import com.utsav.cafemanagementsystem.R

class FoodDetailsActivity : AppCompatActivity() {

    private lateinit var fname : TextView
    private lateinit var fprice : TextView
    private lateinit var fdesc : TextView

    private lateinit var fid : TextView
    private lateinit var fimage : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_details)

        fname = findViewById(R.id.fname)
        fprice = findViewById(R.id.fprice)
        fdesc = findViewById(R.id.fdesc)
        fid = findViewById(R.id.fid)
        fimage = findViewById(R.id.fimage)

        val intent = intent.getParcelableExtra<FoodItem>("foodItem")
        if (intent != null){
            val id = intent._id
            val fName = intent.food_name
            val fPrice = intent.food_price
            val fDesc = intent.food_desc
            val fImage = intent.food_image

            fname.text = fName
            fname.text = fName
            fprice.text = fPrice
            fdesc.text = fDesc

            Glide.with(this)
                    .load(fImage)
                    .into(fimage)
        }
    }
}