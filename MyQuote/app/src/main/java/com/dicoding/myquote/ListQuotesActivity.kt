package com.dicoding.myquote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.myquote.databinding.ActivityListQuotesBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.conn.scheme.Scheme
import cz.msebera.android.httpclient.conn.scheme.SchemeRegistry
import cz.msebera.android.httpclient.conn.ssl.SSLSocketFactory
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient
import org.json.JSONArray
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection

class ListQuotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListQuotesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListQuotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "List of Quotes"

        //mengatur layout ListQuoteACtivity
        val layoutManager = LinearLayoutManager(this@ListQuotesActivity)
        binding.listQuotes.layoutManager = layoutManager

        //mengatur dekorasi orientsasi di ListQuoteActivity
        val itemDecoration = DividerItemDecoration(this@ListQuotesActivity, layoutManager.orientation)
        binding.listQuotes.addItemDecoration(itemDecoration)

        getListQuotes()
    }

    private fun getListQuotes() {
        binding.progressBar.visibility = View.VISIBLE

        /*
        * Kode untuk mengatasi error certicate SSL cara dimodul cuma bisa dipakai di real device bukan emulator*/
        val defClient = DefaultHttpClient()
        val hostnameVerifier: HostnameVerifier = SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER
        val registry = SchemeRegistry()
        val socketFactory: SSLSocketFactory = SSLSocketFactory.getSocketFactory()
        socketFactory.setHostnameVerifier(CustomHostnameVerifier())
        registry.register(Scheme("https", socketFactory, 443))
        val client = AsyncHttpClient(registry)
        HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier)
        val url = "https://quote-api.dicoding.dev/list"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                // Jika koneksi berhasil
                binding.progressBar.visibility = View.INVISIBLE

                //variabel menampung array list
                val listQuote = ArrayList<String>()

                //cek apakah mngambil data berhasil dan menampilkan proses di LOG
                val result = responseBody?.let { String(it) }
                if (result != null) {
                    Log.d(TAG, result)
                }

                try {
                    //mengambil data dari JSON
                    val jsonArray = JSONArray(result)
                    //mengambil array JSON
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val quote = jsonObject.getString("en")
                        val author = jsonObject.getString("author")
                        listQuote.add("\n$quote\n — $author\n")
                    }

                    //memasukan data dari array list ke recycler view
                    val adapter = QuoteAdapter(listQuote)
                    binding.listQuotes.adapter = adapter

                } catch (e: Exception) {
                    Toast.makeText(this@ListQuotesActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                // Jika koneksi gagal
                binding.progressBar.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(this@ListQuotesActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        private val TAG = ListQuotesActivity::class.java.simpleName
    }

}