package com.dicoding.myalarmmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.dicoding.myalarmmanager.databinding.ActivityMainBinding
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener, DatePickFragment.DialogDateListener, TimePickerFragment.DialogTimeListener {
    private var binding: ActivityMainBinding? = null
    private lateinit var alarmReceiver: AlarmReceiver

    companion object {
        private const val DATE_PICKER_TAG = "DatePicker"
        private const val TIME_PICKER_ONCE_TAG = "TimePickerOnce"
        private const val TIME_PICKER_REPEAT_TAG = "TimePickerRepeat"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        //listener one time alarm
        binding?.btnOnceDate?.setOnClickListener(this)
        binding?.btnOnceTime?.setOnClickListener(this)
        binding?.btnSetOnceAlarm?.setOnClickListener(this)

        alarmReceiver = AlarmReceiver()

        //listener repeating alarm
        binding?.btnRepeatingTime?.setOnClickListener(this)
        binding?.btnSetRepeatingAlarm?.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            //ketika tombol pilih tanggal ditekan
            R.id.btn_once_date -> {
                //menampilkan fragmet memilih tanggal
                val datePickerFragment = DatePickFragment()
                datePickerFragment.show(supportFragmentManager, DATE_PICKER_TAG)
            }
            //ketika tombol pilih waktu ditekan
            R.id.btn_once_time -> {
                //menampilkan fragment memilih waktu
                val timePickerFragment = TimePickerFragment()
                timePickerFragment.show(supportFragmentManager, TIME_PICKER_ONCE_TAG)
            }
            R.id.btn_set_once_alarm -> {
                val onceDate = binding?.tvOnceDate?.text.toString()
                val onceTime = binding?.tvOnceTime?.text.toString()
                val onceMessage = binding?.edtOnceMessage?.text.toString()

                //memanngil fungsi setOneTimeAlarm di class AlarmReceiver
                alarmReceiver.setOneTimeAlarm(this, AlarmReceiver.TYPE_ONE_TIME,
                    onceDate,
                    onceTime,
                    onceMessage)
            }
            R.id.btn_repeating_time -> {
                val timePickerFragment = TimePickerFragment()
                timePickerFragment.show(supportFragmentManager, TIME_PICKER_REPEAT_TAG)
            }
            R.id.btn_set_repeating_alarm -> {
                val repeatTime = binding?.tvRepeatingTime?.text.toString()
                val repeatMessage = binding?.edtRepeatingMessage?.text.toString()
                alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.TYPE_REPEATING, repeatTime, repeatMessage)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    //fungsi hasil implementasi DialogDatePicker
    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        //menyiapkan dateformatter terlebih dahulu
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)

        //format default date yang akan ditampilkan
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        //set Text dari texview once
        binding?.tvOnceDate?.text = dateFormat.format(calendar.time)
    }

    //fungsi hasil implemetnasi DialogTimePicker
    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        //siapkan time formatter terlebih dahulu
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)

        //format default time yang akan ditampilkan
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        //set text dari textview berdasarkan tag
        when(tag){
            TIME_PICKER_ONCE_TAG -> binding?.tvOnceTime?.text = dateFormat.format(calendar.time)
            TIME_PICKER_REPEAT_TAG -> binding?.tvRepeatingTime?.text = dateFormat.format(calendar.time)
            else -> {

            }
        }
    }
}