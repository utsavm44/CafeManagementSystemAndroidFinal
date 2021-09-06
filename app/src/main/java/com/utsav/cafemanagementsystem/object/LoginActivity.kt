package com.utsav.cafemanagementsystem.`object`

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import com.utsav.cafemanagementsystem.Utils.saveSharedPref
import com.utsav.cafemanagementsystem.api.ServiceBuilder
import com.utsav.cafemanagementsystem.entity.User
import com.utsav.cafemanagementsystem.repository.UserRepository
import com.google.android.material.snackbar.Snackbar
import com.utsav.cafemanagementsystem.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginActivity : AppCompatActivity()  {
//, SensorEventListener
    private lateinit var login_email: EditText
    private lateinit var login_password: EditText
    private lateinit var btn_login: Button
    private lateinit var btnsignup: Button
    private lateinit var linearlayout: ConstraintLayout

//    private lateinit var sensorManager: SensorManager
//    private var sensor:Sensor?=null

    private val permissions = arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnsignup = findViewById(R.id.btn_signup)
        login_email = findViewById(R.id.login_email)
        login_password = findViewById(R.id.login_password)
        btn_login = findViewById(R.id.btn_login)
        linearlayout = findViewById(R.id.linearlayout)



//        sensorManager= getSystemService(SENSOR_SERVICE) as SensorManager
//
//        if(!checkSensor())
//            return
//        else{
//            sensor= sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
//            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL  )
//        }

        checkRunTimePermission()
        btn_login.setOnClickListener {
            login()
        }
        btnsignup.setOnClickListener() {
            startActivity(Intent(this, RegisterActivity::class.java))

        }


    }

//    private fun checkSensor(): Boolean {
//        var flag=true
//        if(sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)==null){
//            flag= false
//        }
//        return flag
//    }

//    override fun onSensorChanged(event: SensorEvent?){
//        val values=event!!.values[1]
//
//        if (values<0) {
//            Toast.makeText(this, " Right", Toast.LENGTH_SHORT).show()
//
//            startActivity(Intent(this, RegisterActivity::class.java))
//            finish()
//
//        }
//
//
//        else if (values>0) {
//            Toast.makeText(this, " left", Toast.LENGTH_SHORT).show()
//            startActivity(Intent(this, LoginActivity::class.java))
//            finish()
//
//        }
//
//
//    }


    private fun checkRunTimePermission() {
        if (!hasPermission()) {
            requestPermission()
        }
    }

    private fun hasPermission(): Boolean {
        var hasPermission = true
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                            this,
                            permission
                    ) != PackageManager.PERMISSION_GRANTED
            ) {
                hasPermission = false
                break
            }
        }
        return hasPermission
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this@LoginActivity, permissions, 1)
    }

    private fun login() {

        val email = login_email.text.toString()
        val password = login_password.text.toString()
        val user= User(email=email,password = password)

        if(email == "" || password == "" ){
            validationData()
            return
        }
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = UserRepository()
                val response = repository.checkUser(user = user)
                if (response.success == true) {
                    saveSharedPref(_id = response.id!!, email = email, password =  password)
                    ServiceBuilder.token = "Bearer " + response.token
                    ServiceBuilder.id=response.id!!
                    startActivity(
                            Intent(
                                    this@LoginActivity,
                                    MainActivity::class.java
                            )

                    )

                    withContext(Dispatchers.Main) {
                        val snack =
                            Snackbar.make(
                                linearlayout,
                                "{${user.name} is successfully logged In}",
                                Snackbar.LENGTH_LONG
                            )
                        snack.setAction("OK", View.OnClickListener {
                            snack.dismiss()
                        })
                        snack.show()
                    }

                    finish()

                } else {
                    withContext(Dispatchers.Main) {
                        val snack =
                                Snackbar.make(
                                        linearlayout,
                                        "Invalid credentials",
                                        Snackbar.LENGTH_LONG
                                )
                        snack.setAction("OK", View.OnClickListener {
                            snack.dismiss()
                        })
                        snack.show()
                    }
                }

            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                            this@LoginActivity,
                            "Login error", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

//    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
//
//    }

//    Validation
private fun validationData() {
    if (login_email.text.isEmpty()) {
        login_email.error = "Please enter Email"
        return
    }

    if (login_password.text.isEmpty()) {
        login_password.error = "Please enter Password"
        return
    }


}





}
