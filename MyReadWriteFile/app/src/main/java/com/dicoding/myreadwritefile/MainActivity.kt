package com.dicoding.myreadwritefile

import android.icu.text.CaseMap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.dicoding.myreadwritefile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNew.setOnClickListener(this)
        binding.btnOpen.setOnClickListener(this)
        binding.btnSave.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.btn_new -> newFile()
            R.id.btn_open -> showList()
            R.id.btn_save -> saveFile()

        }
    }

    //method ketika btn_new ditekan
    private fun newFile(){
        binding.editTitle.setText("")
        binding.editFile.setText("")
        Toast.makeText(this@MainActivity, "Clearing File", Toast.LENGTH_SHORT).show()
    }

    //methdo ketika btn_open ditekan akan menampilkan sebuah list data dengan menggunakan alertDialog
    private fun showList(){
        //dengan fileList() akan mendapatkan seluruh file yang ada di App Storage
        val items = fileList()
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("Pilih File yang diinginkan")

        //setelah satu berkas dipilih maka aplikasi akan meload file menggunakan fugngsi loadData
        builder.setItems(items) { dialog, item -> loadData(items[item].toString())}
        val alert = builder.create()
        alert.show()
    }

    //method untuk menampilkan data file
    private fun loadData(title: String){
        //menggunakan method pada FileHelper untuk membaca file
        val fileModel = FileHelper.readFromFile(this, title)
        binding.editTitle.setText(fileModel.filename)
        binding.editFile.setText(fileModel.data)
        Toast.makeText(this, "Loading " + fileModel.filename + " data", Toast.LENGTH_SHORT).show()
    }

    private fun saveFile(){
        when{
            //check apakah judul dan isi data kosong
            binding.editTitle.text.isEmpty() -> Toast.makeText(this, "Title Cannot Be Empty", Toast.LENGTH_SHORT).show()
            binding.editFile.text.isEmpty() -> Toast.makeText(this, "Conten harus di isi terlebih dahulu", Toast.LENGTH_SHORT).show()
            else -> {
                //mengambil data pada view
                val title = binding.editTitle.text.toString()
                val text = binding.editFile.text.toString()

                //menyimpan data pada properti fileModel
                val fileModel = FileModel()
                fileModel.filename = title
                fileModel.data = text

                //menuliskan data ke file menggunakan method pada FileHelper
                FileHelper.writeToFile(fileModel, this)
                Toast.makeText(this, "Saving " + fileModel.filename + " file", Toast.LENGTH_SHORT).show()
            }

        }
    }
}