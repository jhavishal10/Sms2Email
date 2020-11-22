package com.android.smstoemail

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.telephony.SmsMessage
import android.util.Log


class SmsListener : BroadcastReceiver() {
    private val preferences: SharedPreferences? = null
    override fun onReceive(context: Context?, intent: Intent) {
        // TODO Auto-generated method stub
        if (intent.action == "android.provider.Telephony.SMS_RECEIVED") {
            val bundle = intent.extras //---get the SMS message passed in---
            var msgs: Array<SmsMessage?>? = null
            var msg_from: String
            if (bundle != null) {
                //---retrieve the SMS message received---
                try {
                    val pdus = bundle["pdus"] as Array<*>?
                    msgs = arrayOfNulls<SmsMessage>(pdus!!.size)
                    for (i in msgs.indices) {
                        msgs[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray)
                        msg_from = msgs[i]?.originatingAddress ?: "na"
                        val msgBody: String = msgs[i]?.messageBody ?: "NA"
                        try {
                            val sender = GMailSender("otponemail@gmail.com", "SomeRandomTool")
                            sender.sendMail(
                                msg_from,
                                msgBody,
                                "otponemail@gmail.com",
                                App.prefs.email
                            )
                        } catch (e: java.lang.Exception) {
                            Log.e("SendMail", e.message, e)
                        }
                        Log.d("yyy", msgBody)
                    }

                } catch (e: Exception) {
                    Log.d("Exception caught", "")
                }
            }
        }
    }
}