package com.dicoding.mylivedata

import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class MainViewModel : ViewModel() {

    private val mInitialTime = SystemClock.elapsedRealtime()
    private val mElapsedTime = MutableLiveData<Long?>()

    init {
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val newValue = (SystemClock.elapsedRealtime() - mInitialTime) / 1000
                //posting tugas ke mainthread
                mElapsedTime.postValue(newValue)            //menyisipkan perubahan yang terjadi  ke live data
            }
        }, ONE_SECOND.toLong(), ONE_SECOND.toLong())
    }

    //fungsi untuk mengembalikan live data ke observer yang bersifat immutable
    fun getElapsedTime() : LiveData<Long?> {
        return mElapsedTime
    }

    companion object {
        private const val ONE_SECOND = 1000
    }
}