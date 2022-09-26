package com.dicoding.mybroadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log

class SmsReceiver : BroadcastReceiver() {

    //fungsi ini digunakan untuk mengolah metadata dari sms yang masuk
    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val bundle = intent.extras
        try {
            if (bundle != null){
                /*
                  Bundle dengan key "pdus" sudah merupakan standar yang digunakan oleh system
                   */
                val pdusObj = bundle.get("pdus") as Array<*>
                for (aPdusObj in pdusObj){
                    val currentMessage = getIncomeMessage(aPdusObj as Any, bundle)          //mengambil data sms dengan bundle
                    val senderNum = currentMessage.displayOriginatingAddress                //mengambil pengirim sms
                    val message = currentMessage.displayMessageBody                         //mengambil isi sms
                    Log.d(TAG, "senderNum: $senderNum; Message: $message")

                    //mengirim data sms dengan intent ke SmsReceiver Acitivty
                    val showSmsIntent = Intent(context, SmsReceiverActivity::class.java)
                    /*kode flags akan menjalankan acitivty pada task yang berbeda.
                    Bila Activity tersebut sudah ada di dalam stack, maka ia akan ditampilkan ke layar
                    * */
                    showSmsIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    showSmsIntent.putExtra(SmsReceiverActivity.EXTRA_SMS_NO, senderNum)
                    showSmsIntent.putExtra(SmsReceiverActivity.EXTRA_SMS_MESSAGE, message)
                    context.startActivity(showSmsIntent)
                }
            }
        }catch (e: Exception) {
            Log.d(TAG, "Exception smsReceiver $e")
        }
    }

    /*
    Method ini mengembalikan currentMessage berdasarkan OS yang dijalankan perangkat android
    **/
    private fun getIncomeMessage(aObject: Any, bundle: Bundle): SmsMessage {
        val currentSMS: SmsMessage
        val format = bundle.getString("format")

        currentSMS = if (Build.VERSION.SDK_INT >= 23){
            SmsMessage.createFromPdu(aObject as ByteArray, format)
        }else SmsMessage.createFromPdu(aObject as ByteArray)
        return currentSMS
    }

    companion object {
        private val TAG = SmsReceiver::class.java.simpleName
    }
}