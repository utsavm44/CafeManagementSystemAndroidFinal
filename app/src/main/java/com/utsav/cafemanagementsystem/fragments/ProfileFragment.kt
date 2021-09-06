package com.utsav.cafemanagementsystem.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.utsav.cafemanagementsystem.`object`.LoginActivity
import com.utsav.cafemanagementsystem.`object`.UserProfileActivity
import com.utsav.cafemanagementsystem.api.ServiceBuilder
import com.utsav.cafemanagementsystem.database.Db
import com.utsav.cafemanagementsystem.repository.UserRepository
import com.bumptech.glide.Glide
import com.utsav.cafemanagementsystem.R
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ProfileFragment : Fragment() {
    //binding
    private lateinit var profilename: TextView
    private lateinit var btn_logout: Button
    private lateinit var editProfile: Button
    private lateinit var email: TextView
    private lateinit var profile_gender: TextView
    private lateinit var profile_phone: TextView
    private lateinit var profile_dob: TextView
    private lateinit var profile_address: TextView
    private lateinit var image1: CircleImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        return view;

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profilename = view.findViewById(R.id.profilename)
        email = view.findViewById(R.id.profile_email)
        image1 = view.findViewById(R.id.circleImageView)
        profile_gender = view.findViewById(R.id.profile_gender)
        profile_phone = view.findViewById(R.id.profile_phone)
        profile_dob = view.findViewById(R.id.profile_dob)
        profile_address = view.findViewById(R.id.profile_address)
        btn_logout = view.findViewById(R.id.btn_logout)
        editProfile = view.findViewById(R.id.btn_editprofile)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repositry = UserRepository()
                val response = repositry.getUser(ServiceBuilder.id!!)

                if (response.success == true) {
                    val data = response.data
                    val dat = data?.get(0)
                    Log.d("data is ", response.data!!.toString())
                    val name = "${dat!!.name}  "
                    val p_email = "${dat!!.email}"
                    val image = "${dat!!.image}"
                    val p_phone = dat.phone
                    val p_gender = dat.gender
                    val p_dob = dat.dob
                    val p_address = dat.address


                    withContext(Dispatchers.Main) {
                        profilename.text = name
                        email.text = p_email
                        profile_phone.text = p_phone
                        profile_gender.text = p_gender
                        profile_dob.text = p_dob
                        profile_address.text = p_address


                        val imagepath = ServiceBuilder.loadImagepath() + image
                        if (image != null) {
                            if (!image.equals("noimg")!!) {
                                Glide.with(requireActivity())
                                    .load(imagepath)
                                    .into((image1))
                            }
                        }

//                        Toast.makeText(context, "check", Toast.LENGTH_SHORT).show()

                    }


                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, response.msg, Toast.LENGTH_SHORT).show()
                    }
                }

            } catch (e: Exception) {
                Log.d("25A:", e.localizedMessage)
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Here", Toast.LENGTH_SHORT).show()
                }
            }

            editProfile.setOnClickListener {
                activity?.let {
                    startActivity(Intent(context, UserProfileActivity::class.java))
                }
            }

        }



        btn_logout.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage("DO you Want to logout")
            builder.setIcon(android.R.drawable.ic_dialog_alert)
            builder.setPositiveButton("yes") { dialogInterface, which ->
                val sharePref =
                    requireActivity().getSharedPreferences("MyPref", AppCompatActivity.MODE_PRIVATE)
                val editor = sharePref.edit()
                editor.remove("email")
                editor.remove("password")
                editor.remove("_id")
                editor.remove("name")
                    .apply()

                CoroutineScope(Dispatchers.IO).launch {
                    Db.getInstance(requireContext()).getUserDao().logout()
                    withContext(Dispatchers.Main) {
                        startActivity(Intent(context, LoginActivity::class.java))
                    }

                }

            }
            builder.setNegativeButton("No") { dialogInterface, which ->
            }
            builder.show()


        }


    }


}



