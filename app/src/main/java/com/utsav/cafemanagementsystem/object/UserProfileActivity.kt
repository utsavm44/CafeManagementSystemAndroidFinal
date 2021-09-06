package com.utsav.cafemanagementsystem.`object`

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.*
import com.utsav.cafemanagementsystem.api.ServiceBuilder
import com.utsav.cafemanagementsystem.entity.User
import com.utsav.cafemanagementsystem.repository.UserRepository
import com.bumptech.glide.Glide
import com.utsav.cafemanagementsystem.R
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class UserProfileActivity : AppCompatActivity() {
    private val REQUEST_GALLERY_CODE = 0;
    private val REQUEST_CAMERA_CODE = 1;
    private lateinit var imgAdd: CircleImageView;
    private lateinit var edit_name: EditText;
    private lateinit var edit_email: EditText;
    private lateinit var edit_address: EditText;
    private lateinit var edit_gender: Spinner;
    private lateinit var edit_phone: EditText;
    private lateinit var profilename: TextView;
    private lateinit var dob: TextView;
    private lateinit var gender: TextView;
    private lateinit var btn_dobdate: Button;
    var imageUrl: String? = null
    private lateinit var btn_saveprofile: Button;

    //    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        edit_name = findViewById(R.id.edit_name)
        edit_email = findViewById(R.id.edit_email)
        edit_gender = findViewById(R.id.edit_gender)
        edit_address = findViewById(R.id.edit_address)
        edit_phone = findViewById(R.id.edit_phone)
        btn_dobdate = findViewById(R.id.btn_dobdate)
        profilename = findViewById(R.id.profilename)
        dob = findViewById(R.id.dob)
        gender = findViewById(R.id.gender)
        btn_saveprofile = findViewById(R.id.btn_saveprofile)
        imgAdd = findViewById(R.id.imgAdd)
//    calender for dob
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        btn_dobdate.setOnClickListener() {
            val dpd = this?.let {
                DatePickerDialog(
                    it, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                        //set to textview
                        dob.setText("" + mDay + "/" + mMonth + "/" + mYear)
                    },
                    year, month, day
                )
            }
            //shoe dialog
            if (dpd != null) {
                dpd.show()
            }

        }

//    spinner gender
        edit_gender?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
                gender.setText(type)
                println(type)
            }
        }




        CoroutineScope(Dispatchers.IO).launch {
            val repository = UserRepository()
            val response = repository.getUser(ServiceBuilder.id!!)
            if (response.success == true) {
                val data = response.data
                val listdata = data?.get(0)
                withContext(Dispatchers.Main) {
                    val imageUrl = listdata!!.image
                    val imagePath = ServiceBuilder.loadImagepath() + imageUrl
                    if (!imageUrl.equals("noimg")) {
                        Glide.with(applicationContext)
                            .load(imagePath)
                            .into(imgAdd)
                    }
                    edit_name.setText("${listdata.name}")
                    edit_address.setText("${listdata.address}")
                    edit_email.setText("${listdata.email}")
                    edit_phone.setText("${listdata.phone}")
                    profilename.setText("${listdata.name}")
                }
            }
        }
        imgAdd.setOnClickListener() {
            popup()
        }
        btn_saveprofile.setOnClickListener() {
            val user = User(
                _id = ServiceBuilder.id!!,
                name = edit_name.text.toString(),
                address = edit_address.text.toString(),
                email = edit_email.text.toString(),
                phone = edit_phone.text.toString(),
                dob = dob.text.toString(),
                gender = gender.text.toString()
//                gender = etgender.text.toString(), dob = etdob.text.toString()

            )
            CoroutineScope(Dispatchers.IO).launch {
                val repository = UserRepository()
                val response = repository.updateUser(user)
                val suc = response
                if (response.success == true) {
                    val id = ServiceBuilder.id!!
                    if (imageUrl != null) {
                        uploadImage(id!!)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@UserProfileActivity,
                                "User Edited Successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@UserProfileActivity,
                            "error occours",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                finish()
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@UserProfileActivity, "Profile Edited", Toast.LENGTH_SHORT)
                        .show()

                }

            }
        }
    }

    private fun popup() {
        val popupMenu = PopupMenu(this, imgAdd)
        popupMenu.menuInflater.inflate(R.menu.gallery_camera_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menuGallery -> {
                    openGallery()
                    true
                }
                R.id.menuCamera -> {
                    openCamera()
                    true
                }
                else -> false
            }
        })
        popupMenu.show()
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_CODE)
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CAMERA_CODE)
    }

    private fun bitmapToFile(
        bitmap: Bitmap,
        fileNameToSave: String
    ): File? {
        var file: File? = null
        return try {
            file = File(
                getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    .toString() + File.separator + fileNameToSave
            )
            file.createNewFile()
            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
            val bitMapData = bos.toByteArray()
            val fos = FileOutputStream(file)
            fos.write(bitMapData)
            fos.flush()
            fos.close()
            file
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            file
        }
    }

    private fun uploadImage(studentId: String) {
        if (imageUrl != null) {
            val file = File(imageUrl!!)
            val reqFile =
                RequestBody.create(MediaType.parse("image/${file.extension}"), file)
            val body =
                MultipartBody.Part.createFormData("file", file.name, reqFile)
            CoroutineScope(Dispatchers.IO).launch {
                val userRepository = UserRepository()
                val response = userRepository.uploadImage(studentId, body)
                if (response.success == true) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@UserProfileActivity, "Uploaded", Toast.LENGTH_SHORT)
                            .show()
                        finish()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@UserProfileActivity, "error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_GALLERY_CODE && data != null) {
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val contentResolver = contentResolver
                val cursor =
                    contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
                cursor!!.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                imageUrl = cursor.getString(columnIndex)
                imgAdd.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
                cursor.close()
            } else if (requestCode == REQUEST_CAMERA_CODE && data != null) {
                val imageBitmap = data.extras?.get("data") as Bitmap
                val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val file = bitmapToFile(imageBitmap, "$timeStamp.jpg")
                imageUrl = file!!.absolutePath
                imgAdd.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
            }
        }
    }
}