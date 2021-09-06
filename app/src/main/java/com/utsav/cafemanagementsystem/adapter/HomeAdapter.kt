package com.utsav.cafemanagementsystem.adapter

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.utsav.cafemanagementsystem.api.ServiceBuilder
import com.utsav.cafemanagementsystem.repository.AddToCartRepository
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.utsav.cafemanagementsystem.R
import com.utsav.cafemanagementsystem.entity.AddCart
import com.utsav.cafemanagementsystem.entity.FoodItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeAdapter(
    val listpost:ArrayList<FoodItem>,
    val context: Context): RecyclerView.Adapter<HomeAdapter.HomwviewHolder>() {
    class HomwviewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fImage: ImageView;
        val f_name: TextView;
        val f_price: TextView;
        val f_desc: TextView;
        val addfav: ImageView;
        init {
            fImage=view.findViewById(R.id.fooditemimage)
            f_name=view.findViewById(R.id.fooditemname)
            f_desc=view.findViewById(R.id.fooditemprice)
            f_price=view.findViewById(R.id.fooditemdesc)
            addfav=view.findViewById(R.id.addfav)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomwviewHolder {
        val view= LayoutInflater.from(parent.context)
            .inflate(R.layout.fooditemlayout,parent,false)
        return HomwviewHolder(view)
    }
    override fun onBindViewHolder(holder: HomwviewHolder, position: Int) {
        val post=listpost[position]
        holder.f_name.text=post.food_name
        holder.f_price.text=post.food_price
        holder.f_desc.text=post.food_desc
        val id=post._id
        val imagePath = ServiceBuilder.loadImagepath() +post.food_image
        if (!post.food_image.equals("noimg")) {
            Glide.with(context)
                .load(imagePath)
                .into(holder.fImage)
        }
        holder.addfav.setOnClickListener() {
            val builder = AlertDialog.Builder(context);
            builder.setMessage("Do you want add this product to Fav.")
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setPositiveButton("Yes") { dialogInterface, which ->
                CoroutineScope(Dispatchers.IO).launch {
                    val repository = AddToCartRepository()
                    val response = repository.AddFav(AddCart(userId = ServiceBuilder.id!!, productId = post._id))
                    if (response.success == true) {
                        withContext(Dispatchers.Main) {
                            val snack = Snackbar.make(it, "${response.msg}", Snackbar.LENGTH_SHORT)
                            snack.setAction("Ok") {
                                snack.dismiss()
                            }
                            snack.show()
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "error occur", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }
            builder.setNegativeButton("No") { dialogInterface, which ->
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
            holder.addfav.setBackgroundColor(Color.RED)
        }
    }
    override fun getItemCount(): Int {
        return listpost.size
    }
}