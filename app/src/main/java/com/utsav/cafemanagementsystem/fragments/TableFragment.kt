package com.utsav.cafemanagementsystem.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.utsav.cafemanagementsystem.R
import com.utsav.cafemanagementsystem.entity.Table
import com.utsav.cafemanagementsystem.repository.TableRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class TableFragment : Fragment() {
        private lateinit var user_email : EditText
        private lateinit var people : EditText
        private lateinit var btn_date : Button
        private lateinit var time : Spinner
        private lateinit var spinnertxt : TextView
        private lateinit var datetxt : TextView
        private lateinit var btn_table : Button



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_table = view.findViewById(R.id.btn_table)
        user_email = view.findViewById(R.id.user_email)
        time = view.findViewById(R.id.time)
        people = view.findViewById(R.id.people)
        spinnertxt = view.findViewById(R.id.spinnertxt)
        datetxt = view.findViewById(R.id.datetxt)
        btn_date = view.findViewById(R.id.btn_date)
        //calender
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
     val day = c.get(Calendar.DAY_OF_MONTH)
        btn_date.setOnClickListener(){
            val dpd = context?.let {
                DatePickerDialog(
                    it, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                        //set to textview
                        datetxt.setText("" + mDay + "/" + mMonth + "/" + mYear)
                    },
                    year, month, day
                )
            }
            //shoe dialog
            if (dpd != null) {
                dpd.show()
            }



        }
        time?.onItemSelectedListener = object :AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                println("erreur")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val type = parent?.getItemAtPosition(position).toString()
                spinnertxt.setText(type)
                println(type)
            }
        }

        btn_table.setOnClickListener() {
            tablebooking()

        }
    }
    private  fun tablebooking() {
        val user_email = user_email.text.toString()
        val people = people.text.toString()
        val date = datetxt.text.toString()
        val time = spinnertxt.text.toString()

        if(user_email == "" || people == "" || date == "" || time == ""){
            validationData()
            return
        }

        val table = Table(user_email = user_email, people = people, date = date, time = time)
        CoroutineScope(Dispatchers.IO).launch {
            val repository = TableRepository()
            val response = repository.registerTable(table)
            if (response.success == true) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Table Booked", Toast.LENGTH_SHORT).show()
                    clear()
                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "error Booked", Toast.LENGTH_SHORT).show()
                }
            }

        }



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_table, container, false)

        return view;
    }
    private fun clear() {
        user_email.setText("")
        people.setText("")

    }
    //    validation for Table
    private fun validationData(){
        if (user_email.text.isEmpty()) {
            user_email.error = "Please enter Email"
            return
        }
        if (people.text.isEmpty()) {
            people.error = "Please enter Number of People"
            return
        }



    }




}