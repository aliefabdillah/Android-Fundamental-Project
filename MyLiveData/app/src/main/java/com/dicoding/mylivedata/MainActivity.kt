package com.dicoding.mylivedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dicoding.mylivedata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mLiveDataTimerViewModel: MainViewModel
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        //menghubungkan main activity dengan main view model
        mLiveDataTimerViewModel = ViewModelProvider(this@MainActivity)[MainViewModel::class.java]
        subscribe()
    }

    private fun subscribe(){
        val elapsedTimeObserver = Observer<Long?> {
            /*
            * variabel it akan diperarui secara realtime*/
            val newText = this@MainActivity.resources.getString(R.string.seconds, it)       //mengganti nilai yang ada pada string dengan variabel it
            activityMainBinding.timerTextview.text = newText
        }

        /*
        * nilai it akan selalu berubah jika elapsedTimeObserver selalu dipanggil disini
        *
        * cara mendapatkan value dari sebuah LiveData harus dilakukan dengan cara meng-observe
        * LiveData tersebut. Dan proses ini dilakukan secara asynchronous.*/
        mLiveDataTimerViewModel.getElapsedTime().observe(this@MainActivity, elapsedTimeObserver)
    }
}