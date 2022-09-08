package com.dicoding.myquote

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.myquote.databinding.ActivityMainBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.conn.scheme.Scheme
import cz.msebera.android.httpclient.conn.scheme.SchemeRegistry
import cz.msebera.android.httpclient.conn.ssl.SSLSocketFactory
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient
import org.json.JSONObject
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //fungsi mengambil random quote dari API
        getRandomQuote()

        binding.btnAllQuotes.setOnClickListener {
            startActivity(Intent(this@MainActivity, ListQuotesActivity::class.java))
        }
    }

    private fun getRandomQuote() {
        binding.progressBar.visibility = View.VISIBLE

        /*
        * Kode untuk mengatasi error certicate SSL cara di modul cuma bisa dipakai
        *  di real device bukan emulator*/
        val defClient = DefaultHttpClient()
        val hostnameVerifier: HostnameVerifier = SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER
        val registry = SchemeRegistry()
        val socketFactory: SSLSocketFactory = SSLSocketFactory.getSocketFactory()
        socketFactory.setHostnameVerifier(CustomHostnameVerifier())
        registry.register(Scheme("https", socketFactory, 443))
        val client = AsyncHttpClient(registry)
        HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier)

        val url = "https://quote-api.dicoding.dev/random"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                //if connection succes
                //parsing JSON data
                binding.progressBar.visibility = View.INVISIBLE

                val result = responseBody?.let { String(it) }
                if (result != null) {
                    Log.d(TAG, result)
                }

                try {
                    val responseObject = result?.let { JSONObject(it) }
                    val quote = responseObject?.getString("en")
                    val author = responseObject?.getString("author")

                    binding.tvQuote.text = quote
                    binding.tvAuthor.text = author
                }catch (e: Exception){
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                //if connetion error
                binding.progressBar.visibility = View.INVISIBLE

                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }

                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }

        })

    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}