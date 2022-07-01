package com.example.poc_voice.activity

import android.app.AlertDialog
import android.app.KeyguardManager
import android.content.DialogInterface
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.core.app.ActivityCompat
import com.example.poc_voice.R

class AuthClass : AppCompatActivity() {
        // create a CancellationSignal variable and assign a value null to it
        private var cancellationSignal : CancellationSignal? = null

        private val sharedPrefFile = "kotlinsharedpreference"

        private lateinit var  btn_bio: Button
        private lateinit var  start_authentication: Button
        private lateinit var ll_login: LinearLayout

        // create an authenticationCallback
        private val authenticationCallback : BiometricPrompt.AuthenticationCallback
            get() =
                @RequiresApi(Build.VERSION_CODES.P)
                object : BiometricPrompt.AuthenticationCallback(){
                    // here we need to implement two methods
                    // onAuthenticationError and onAuthenticationSucceeded
                    // If the fingerprint is not recognized by the app it will call
                    // onAuthenticationError and show a toast
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                        super.onAuthenticationError(errorCode, errString)
                        notifyUser("Authentication Error : $errString")
                    }
                    // If the fingerprint is recognized by the app then it will call
                    // onAuthenticationSucceeded and show a toast that Authentication has Succeed
                    // Here you can also start a new activity after that
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                        super.onAuthenticationSucceeded(result)
                        notifyUser("Authentication Succeeded")

                        // or start a new Activity
                    }

                }

        @RequiresApi(Build.VERSION_CODES.P)
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_auth)

            val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
                MODE_PRIVATE
            )


            btn_bio = findViewById(R.id.btn_bio)
            start_authentication = findViewById(R.id.start_authentication)
            ll_login = findViewById(R.id.ll_login)

            if(BiometricManager.from(this).canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS) {
                checkBiometricSupport()

                ll_login.visibility = View.GONE

                val sharedIdValue = sharedPreferences.getInt("id_key",0)
                if (sharedIdValue==1)
                {
                    btn_bio.visibility = View.GONE
                    start_authentication.visibility = View.VISIBLE
                }
                else
                {
                    btn_bio.visibility = View.VISIBLE
                    start_authentication.visibility = View.GONE
                }
            }
            else
            {
                ll_login.visibility = View.VISIBLE
                btn_bio.visibility = View.GONE
                start_authentication.visibility = View.GONE
            }



            btn_bio.setOnClickListener{
                val builder = AlertDialog.Builder(this)
                //set title for alert dialog
                builder.setTitle("Permission Box")
                //set message for alert dialog
                builder.setMessage("Are you want to enable bio-authentication ")
                builder.setIcon(android.R.drawable.ic_dialog_alert)

                //performing positive action
                builder.setPositiveButton("Yes"){dialogInterface, which ->
                    Toast.makeText(applicationContext, "clicked yes", Toast.LENGTH_LONG).show()
                    val editor: SharedPreferences.Editor =  sharedPreferences.edit()
                    editor.putInt("id_key",1)
                    editor.apply()
                    editor.commit()

                    btn_bio.visibility = View.GONE
                    start_authentication.visibility = View.VISIBLE

                }
                //performing cancel action
                builder.setNeutralButton("Cancel"){dialogInterface , which ->
                    Toast.makeText(
                        applicationContext,
                        "clicked cancel\n operation cancel",
                        Toast.LENGTH_LONG
                    ).show()
                }

                // Create the AlertDialog
                val alertDialog: AlertDialog = builder.create()
                // Set other dialog properties
                alertDialog.setCancelable(false)
                alertDialog.show()
            }

            // create a biometric dialog on Click of button
            start_authentication.setOnClickListener {
                // This creates a dialog of biometric auth and it requires title , subtitle ,
                // and description
                // In our case there is a cancel button by clicking it, it will cancel the process of
                // fingerprint authentication
                val biometricPrompt = BiometricPrompt.Builder(this)
                    .setTitle("Authentication")
                    .setDescription("Uses FP")
                    .setNegativeButton("Cancel" , this.mainExecutor, DialogInterface.OnClickListener { dialog, which ->
                        notifyUser("Authentication Cancelled")
                    }).build()

                // start the authenticationCallback in mainExecutor
                biometricPrompt.authenticate(getCancellationSignal(),mainExecutor, authenticationCallback)
            }

        }

        // it will be called when authentication is cancelled by the user
        private fun getCancellationSignal() : CancellationSignal {
            cancellationSignal = CancellationSignal()
            cancellationSignal?.setOnCancelListener {
                notifyUser("Authentication was Cancelled by the user")
            }
            return cancellationSignal as CancellationSignal
        }

        // it checks whether the app the app has fingerprint permission
        @RequiresApi(Build.VERSION_CODES.M)
        private fun checkBiometricSupport() : Boolean {
            val keyguardManager = getSystemService(KEYGUARD_SERVICE) as KeyguardManager
            if (!keyguardManager.isDeviceSecure){
                notifyUser("Fingerprint authentication has not been enabled in settings")
                return false
            }
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED){
                notifyUser("Fingerprint Authentication Permission is not enabled")
                return false
            }
            return if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)){
                true
            }else true

        }

        // this is a toast method which is responsible for showing toast
        // it takes a string as parameter
        private fun notifyUser(message : String){
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
}