package com.utsav.cafemanagementsystem.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utsav.cafemanagementsystem.R
import com.utsav.cafemanagementsystem.adapter.FoodMenuAdapter
import com.utsav.cafemanagementsystem.database.FoodMenuDatabase
import com.utsav.cafemanagementsystem.entity.FoodMenu
import com.utsav.cafemanagementsystem.repository.FoodMenuRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MenuFragment : Fragment() {
//    sensor
//    private lateinit var sensorManager: SensorManager
//    private var sensor: Sensor?=null

    private lateinit var menurecycler: RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        menurecycler=view.findViewById(R.id.menurecycler)


        getData()

        return view;
    }







    fun getData(){
        try{
            CoroutineScope(Dispatchers.IO).launch {
                val foodMenuRepository = FoodMenuRepository()
                val response = foodMenuRepository.getFoodMenuApiData()
                if(response.success==true){
                    withContext(Dispatchers.Main){
                        println(response)
                        val foodmenuitemlist = response.data
                        FoodMenuDatabase.getInstance(requireContext()).getFoodMenuDAO().deleteFoodMenu()
                        FoodMenuDatabase.getInstance(requireContext()).getFoodMenuDAO().insertfoodmenu(response.data)
//                        Toast.makeText(context, "$foodmenuitemlist", Toast.LENGTH_SHORT).show()
                        val adapter = FoodMenuAdapter(
                                foodmenuitemlist as ArrayList<FoodMenu>,
                                requireContext()
                        )
                        menurecycler.layoutManager = LinearLayoutManager(context)
                        menurecycler.adapter = adapter
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



}