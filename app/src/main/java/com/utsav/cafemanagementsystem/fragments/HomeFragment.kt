package com.utsav.cafemanagementsystem.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aayush.resturant_management_system.RMS.database.FoodItemDatabase
import com.utsav.cafemanagementsystem.`object`.LoginActivity
import com.utsav.cafemanagementsystem.adapter.FoodItemAdapter
//import com.utsav.cafemanagementsystem.adapter.HomeAdapter
import com.utsav.cafemanagementsystem.database.Db
import com.utsav.cafemanagementsystem.entity.FoodItem
import com.utsav.cafemanagementsystem.repository.FoodItemRepository
import com.synnapps.carouselview.CarouselView
import com.utsav.cafemanagementsystem.R
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main


class HomeFragment : Fragment() {
//    private lateinit var menurecycler: RecyclerView
    private lateinit var fooditemrecycler: RecyclerView
//     private lateinit var favRecycle : RecyclerView
    private  lateinit var  carouselView : CarouselView
    private  val  fragment= AddToCartFragment()
    private lateinit var  cartbtn : ImageView

    //Carousel
    var sampleImage = intArrayOf(
            R.drawable.food,
            R.drawable.food1,
            R.drawable.food2
    )
    var title = arrayOf(
            "Food",
            "Food",
            "Food"
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        carouselView = view.findViewById(R.id.carouselView)
        fooditemrecycler=view.findViewById(R.id.fooditemrecycler)
            cartbtn=view.findViewById(R.id.cartbtn)
//        favRecycle=view.findViewById(R.id.favRecycle)


        //carouselView
        carouselView.pageCount = title.size
        carouselView.setImageClickListener { position ->

            Toast.makeText(context, title[position] , Toast.LENGTH_SHORT).show()
        }

        carouselView.setImageListener { position, imageView ->
            imageView.setImageResource(sampleImage[position])

        }

        cartbtn.setOnClickListener {
//            loadFragment(fragment)

            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage("DO you Want to logout")
            builder.setIcon(android.R.drawable.ic_dialog_alert)
            builder.setPositiveButton("yes"){dialogInterface, which->
                val sharePref = requireActivity().getSharedPreferences("MyPref",AppCompatActivity.MODE_PRIVATE )
                val editor = sharePref.edit()
                editor.remove("email")
                editor.remove("password")
                editor.remove("_id")
                editor.remove("name")
                    .apply()

                CoroutineScope(Dispatchers.IO).launch{
                    Db.getInstance(requireContext()).getUserDao().logout()
                    withContext(Main){
                        startActivity(Intent(context, LoginActivity::class.java))
                    }
                }
            }
            builder.setNegativeButton("No"){
                dialogInterface, which ->
            }
            builder.show()
        }


        getFoodData()

        return view;
    }

    fun getFoodData(){
        try{
            CoroutineScope(Dispatchers.IO).launch {
                val foodItemRepository = FoodItemRepository()
                val response = foodItemRepository.getFoodItemApiData()
                println(response)
                if(response.success==true){
                    withContext(Dispatchers.Main){
                        println(response)
                        val fooditemlist = response.data
                        FoodItemDatabase.getInstance(requireContext()).getFoodItemDAO().deleteFoodItem()
                        FoodItemDatabase.getInstance(requireContext()).getFoodItemDAO().insertfoodItem(response.data)
                        val adapter = FoodItemAdapter(
                                fooditemlist as ArrayList<FoodItem>,
                                requireContext()
                        )

                        fooditemrecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                        fooditemrecycler.adapter = adapter
                    }
                }
            }
        }catch (ex: Exception){
            Toast.makeText(
                    context,
                    "Error : ${ex.toString()}", Toast.LENGTH_SHORT
            ).show()
        }

    }

    private fun loadFragment(fragment: Fragment){
        val trans=fragmentManager?.beginTransaction()
        trans?.replace(R.id.containerViewPager,fragment)
        trans?.disallowAddToBackStack()
        trans?.commit()
    }




}
