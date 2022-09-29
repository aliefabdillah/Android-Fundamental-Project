package com.dicoding.myworkmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.work.*
import com.dicoding.myworkmanager.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var workManager: WorkManager
    private lateinit var binding: ActivityMainBinding
    private lateinit var periodicWorkRequest: PeriodicWorkRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //instance class workmanager
        workManager = WorkManager.getInstance(this)
        binding.btnOneTimeTask.setOnClickListener(this)
        binding.btnPeriodicTask.setOnClickListener(this)
        binding.btnCancelTask.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btnOneTimeTask -> startOneTimeTask()
            R.id.btnPeriodicTask -> startPeriodicTast()
            R.id.btnCancelTask -> cancelPeriodicTask()
        }
    }

    private fun startOneTimeTask(){
        binding.textStatus.text = getString(R.string.status)

        //variabel yang berisi object Data beruba Key-Value
        val data = Data.Builder()
            .putString(MyWorker.EXTRA_CITY, binding.editCity.text.toString())       //key-value data
            .build()
        //constarints/kondisi device ketika ingin menjalankan task
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        //menggunakanan Class OneTimeWorkRequest untuk menjalankan Task sekali saja
        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setInputData(data)             //mengirimkan data ke workmanager
            .setConstraints(constraints)
            .build()

        //membaca stasu workmanager
        workManager.enqueue(oneTimeWorkRequest)
        //method getWorkInfoByIdLiveData untuk mendapatkan status seara live
        workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.id)
            .observe(this) { workInfo ->
                val status = workInfo.state.name                //workInfoState merupakan state workManager Saat itu
                binding.textStatus.append("\n" + status)
            }
    }

    private fun startPeriodicTast() {
        binding.textStatus.text = getString(R.string.status)
        val data = Data.Builder()
            .putString(MyWorker.EXTRA_CITY, binding.editCity.text.toString())
            .build()
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        //menggunakanan Class PeriodicWorkRequest untuk menjalankan Task secara berkala
        /*
        * repeat interval merupakan jumlah nilai kapan task akan dijalankan kembali
        * dan TimeUnit adalah satuan waktu
        *
        * Batas Minimal Interval adalah 15 menit sama dengan JobScheduler*/
        periodicWorkRequest = PeriodicWorkRequest.Builder(MyWorker::class.java, 15, TimeUnit.MINUTES)
            .setInputData(data)
            .setConstraints(constraints)
            .build()
        workManager.enqueue(periodicWorkRequest)
        workManager.getWorkInfoByIdLiveData(periodicWorkRequest.id)
            .observe(this){ workInfo ->
                val status = workInfo.state.name
                binding.textStatus.append("\n" + status)
                binding.btnCancelTask.isEnabled = false
                if (workInfo.state == WorkInfo.State.ENQUEUED) {
                    binding.btnCancelTask.isEnabled = true
                }
            }
    }

    //method cancel workManager berdasarkan Id
    private fun cancelPeriodicTask() {
        workManager.cancelWorkById(periodicWorkRequest.id)
    }


}