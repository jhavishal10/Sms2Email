package com.android.smstoemail

import android.os.Bundle
import android.os.StrictMode
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.android.smstoemail.App.Companion.prefs
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        askMessagePermission()
        if (!prefs.email.isNullOrEmpty()) {
            note.isVisible = true
            email.isVisible = false
            note.text = "Your provided email is: " + prefs.email
            button.text = "Change email"
        }
        button.setOnClickListener {
            if (button.text == "Change email") {
                email.isVisible = true
                note.isVisible = true
                button.isVisible = true
                note.text = "Your provided email was: " + prefs.email
                button.text = "Submit"
            } else if (validateEmail(email)) {
                prefs.email = email.text.toString()
                email.isVisible = false
                note.isVisible = true
                note.text = "Your provided email is: " + prefs.email
                button.text = "Change email"

            }

        }
    }

    fun validateEmail(nameValue: TextView): Boolean {
        val phoneNumber = nameValue.text.toString()
        if (!isEmailValid(phoneNumber)) {
            nameValue.error = "Invalid email."
            return false
        }

        return true
    }

    private fun isEmailValid(sValue: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(sValue).matches()
    }

    fun askMessagePermission() {
        Dexter.withActivity(this)
            .withPermission(android.Manifest.permission.RECEIVE_SMS)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    onPermissionSuccess()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    Toast.makeText(
                        this@MainActivity,
                        "App won't work without permission",
                        Toast.LENGTH_SHORT
                    ).show()
                    askMessagePermission()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).check()
    }

    private fun onPermissionSuccess() {
        email.isVisible = true
        note.isVisible = true
        button.isVisible = true
    }


}

