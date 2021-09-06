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
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
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

class FoodItemAdapter(
    val listitem : ArrayList<FoodItem>,
    val context: Context) : RecyclerView.Adapter<FoodItemAdapter.ItemviewHolder>()
{
    class ItemviewHolder (view: View) : RecyclerView.ViewHolder(view){
        val fooditemname :TextView;
        val fooditemprice : TextView
        val fooditemdesc : TextView
        val fooditemimage : ImageView
        val addfav : ImageView



        init {
             fooditemname = view.findViewById(R.id.fooditemname)
            fooditemprice = view.findViewById(R.id.fooditemprice)
             fooditemdesc = view.findViewById(R.id.fooditemdesc)
             fooditemimage = view.findViewById(R.id.fooditemimage)
            addfav = view.findViewById(R.id.addfav)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemviewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fooditemlayout, parent, false)
        return ItemviewHolder(view)

    }

    override fun onBindViewHolder(holder: ItemviewHolder, position: Int) {

        val fooditem = listitem[position]

        holder.fooditemname.text = fooditem.food_name
        holder.fooditemprice.text = fooditem.food_price
        holder.fooditemdesc.text = fooditem.food_desc
        val id = fooditem._id
        if (!fooditem.food_image.equals("noimg")) {
            Glide.with(context)
                .load(fooditem.food_image)
                .into(holder.fooditemimage)
        }


        holder.addfav.setOnClickListener {
            val builder = AlertDialog.Builder(context);
            builder.setMessage("Do you want add this Food to Fav.")
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setPositiveButton("Yes") { dialogInterface, which ->
                CoroutineScope(Dispatchers.IO).launch {
                    val repository = AddToCartRepository()
                    val response = repository.AddFav(
                        AddCart(
                            userId = ServiceBuilder.id!!,
                            productId = fooditem._id
                        )
                    )
                    if (response.success == true) {
                        showfavnotification()
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

        val imagePath = ServiceBuilder.loadImagepath() + fooditem.food_image
        if (!fooditem.food_image.equals("no-photo.png")) {
            Glide.with(context)
                .load(imagePath)
                .fitCenter()
                .into(holder.fooditemimage)
        }


    }

    private fun showfavnotification() {



        val notificationManager= NotificationManagerCompat.from(context)
        val notificationChannels= NotificationChannels(context)
        notificationChannels.createNotificationChannels()
        val notification= NotificationCompat.Builder(context, notificationChannels.CHANNEL_1)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle("Favorite Added")
            .setContentText("Food Item added to favorites successfully.")
            .setColor(Color.YELLOW)
            .build()
        notificationManager.notify(1, notification)


    }


    override fun getItemCount(): Int {
        return listitem.size
    }

}