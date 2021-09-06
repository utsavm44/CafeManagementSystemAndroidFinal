package com.utsav.cafemanagementsystem.adapter

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.RecyclerView
import com.utsav.cafemanagementsystem.api.ServiceBuilder
import com.utsav.cafemanagementsystem.repository.AddToCartRepository
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.utsav.cafemanagementsystem.R
import com.utsav.cafemanagementsystem.entity.ForAddItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddToCartAdapter (val listpost:ArrayList<ForAddItem>,
                        val context: Context
): RecyclerView.Adapter<AddToCartAdapter.FavviewHolder>(){
    class FavviewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView
        val removeFav: ImageView
        val foodname: TextView
        val foodprice: TextView
        val foodd: TextView

        init {
            img=view.findViewById(R.id.img)
            removeFav=view.findViewById(R.id.removeFav)
            foodname=view.findViewById(R.id.foodname)
            foodprice=view.findViewById(R.id.foodprice)
            foodd=view.findViewById(R.id.foodd)

        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavviewHolder {
        val view= LayoutInflater.from(parent.context)
            .inflate(R.layout.addtocart,parent,false)
        return FavviewHolder(view)
    }

    override fun onBindViewHolder(holder: FavviewHolder, position: Int) {
        val fav = listpost[position]
        holder.foodname.text=fav.food_name
        holder.foodprice.text=fav.food_price
        holder.foodd.text=fav.food_desc

        val imagePath = ServiceBuilder.loadImagepath() + fav.food_image
        if (!fav.food_image.equals("noimg")) {
            Glide.with(context)
                .load(imagePath)
                .into(holder.img)
        }

        holder.removeFav.setOnClickListener(){
            val builder= AlertDialog.Builder(context);
            builder.setMessage("Do you want remove from fav")
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setPositiveButton("Yes"){dialogInterface,which->
                CoroutineScope(Dispatchers.IO).launch {
                    val repository= AddToCartRepository()
                    val response=repository.deleteFavProduct(fav._id!!)
                    if(response.success==true){
                        deletenotification()
                        withContext(Dispatchers.Main){
                            listpost.removeAt(position)
                            notifyDataSetChanged()
                            val snack=  Snackbar.make(it,"${response.msg}.  remove from Fav", Snackbar.LENGTH_SHORT)
                            snack.setAction("Ok") {
                                snack.dismiss()
                            }
                            snack.show()

                        }
                    }
                }
            }
            builder.setNegativeButton("No"){
                    dialogInterface,which->
            }
            builder.show()
        }
    }

    private fun deletenotification() {

        val notificationManager= NotificationManagerCompat.from(context)
        val notificationChannels= NotificationChannels(context)
        notificationChannels.createNotificationChannels()
        val notification= NotificationCompat.Builder(context, notificationChannels.CHANNEL_1)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle("Favorite Deleted")
            .setContentText("Food Item Deleted from favorites successfully.")
            .setColor(Color.YELLOW)
            .build()
        notificationManager.notify(1, notification)
    }

    override fun getItemCount(): Int {
        return listpost.size
    }
}