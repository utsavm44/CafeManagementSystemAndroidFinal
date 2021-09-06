package com.utsav.cafemanagementsystem.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aayush.resturant_management_system.RMS.database.FoodItemDatabase
import com.utsav.cafemanagementsystem.adapter.AddToCartAdapter
import com.utsav.cafemanagementsystem.api.ServiceBuilder
import com.utsav.cafemanagementsystem.R
import com.utsav.cafemanagementsystem.entity.ForAddItem
import com.utsav.cafemanagementsystem.repository.AddToCartRepository
import com.utsav.cafemanagementsystem.repository.FoodItemRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private lateinit var favRecycle: RecyclerView;

/**
 * A simple [Fragment] subclass.
 * Use the [AddToCartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddToCartFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_to_cart, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favRecycle = view.findViewById(R.id.favRecycle);
        loadvlaue()
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddToCartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddToCartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    private fun loadvlaue() {
        CoroutineScope(Dispatchers.IO).launch {
            val repository = AddToCartRepository()
            val response = repository.getallFavFoodItem(ServiceBuilder.id!!)
            if (response.success == true) {
                val data = response.data
                var allfoodid: String? = null
                FoodItemDatabase.getInstance(requireContext()).getAddToCartDAO().dropTable()
                for (i in data!!.indices) {
                    allfoodid = data[i].productId
                    val foodRepository = FoodItemRepository()
                    val foodResponse = foodRepository.getallAddtoCart(allfoodid!!)
                    if (foodResponse.success == true) {
                        val res=foodResponse.data
                        FoodItemDatabase.getInstance(requireContext()).getAddToCartDAO().AddCart(foodResponse.data)
                    }
                }
                val fooditemList = FoodItemDatabase.getInstance(requireContext()).getAddToCartDAO().getCart()
                withContext(Dispatchers.Main) {
                    val adpater = context?.let { AddToCartAdapter(fooditemList as ArrayList<ForAddItem>, it) }
                    favRecycle.setHasFixedSize(true);
                    favRecycle.layoutManager = LinearLayoutManager(activity)
                    favRecycle.adapter = adpater;
                }
            }
        }
    }
}