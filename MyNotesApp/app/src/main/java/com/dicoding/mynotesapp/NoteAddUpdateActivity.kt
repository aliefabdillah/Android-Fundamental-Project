package com.dicoding.mynotesapp

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.dicoding.mynotesapp.databinding.ActivityNoteAddUpdateBinding
import com.dicoding.mynotesapp.db.DatabaseContract
import com.dicoding.mynotesapp.db.NoteHelper
import com.dicoding.mynotesapp.entity.Note
import java.text.SimpleDateFormat
import java.util.*
/*
-Menyediakan form untuk melakukan proses input data.

-Menyediakan form untuk melakukan proses pembaruan data.

-Jika pengguna berada pada proses pembaruan data maka setiap kolom pada form sudah terisi otomatis
dan ikon untuk hapus yang berada pada sudut kanan atas ActionBar ditampilkan dan berfungsi untuk
menghapus data.

-Sebelum proses penghapusan data, dialog konfirmasi akan tampil. Pengguna akan ditanya terkait
penghapusan yang akan dilakukan.

-Jika pengguna menekan tombol back (kembali) baik pada ActionBar maupun peranti, maka akan tampil
dialog konfirmasi sebelum menutup halaman.

Masih ingat materi di mana sebuah Activity menjalankan Activity lain dan menerima nilai balik pada
metode registerForActivityResult()? Tepatnya di Activity yang dijalankan dan ditutup dengan
menggunakan parameter RESULTCODE. Jika Anda lupa, baca kembali modul 1 tentang Intent ya!
* */

class NoteAddUpdateActivity : AppCompatActivity(), View.OnClickListener {
    private var isEdit = false
    private var note: Note? = null
    private var position: Int = 0
    private lateinit var noteHelper: NoteHelper
    private lateinit var binding: ActivityNoteAddUpdateBinding

    companion object {
        const val EXTRA_NOTE = "extra_note"
        const val EXTRA_POS = "extra_position"
        const val RESULT_ADD = 101
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteHelper = NoteHelper.getInstance(applicationContext)
        noteHelper.open()

        //cek apakah data yang dikirimkan dari mainactivity null atau tidak
        note = intent.getParcelableExtra(EXTRA_NOTE)
        //jika data dikirimkan ada dan ingin di edit atau hapus
        if (note != null){
            position = intent.getIntExtra(EXTRA_POS, 0)
            isEdit = true
        }else{
            //jika data tidak ada atau ingin tambah baru
            note = Note()
        }

        val actionBarTitle: String
        val btnTitle: String

        if (isEdit){
            actionBarTitle = "Ubah"
            btnTitle = "Update"

            note?.let {
                binding.editTitle.setText(it.title)
                binding.editDesc.setText(it.description)
            }
        }else{
            actionBarTitle = "Tambah"
            btnTitle = "Simpan"
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.btnSubmit.text = btnTitle
        binding.btnSubmit.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_submit){
            val title = binding.editTitle.text.toString().trim()
            val desc = binding.editDesc.text.toString().trim()

            if (title.isEmpty()){
                binding.editTitle.error = "Field Cannot Be Null"
                return
            }

            note?.title = title
            note?.description = desc

            val intent = Intent()
            intent.putExtra(EXTRA_NOTE, note)
            intent.putExtra(EXTRA_POS, position)

            /*
            untuk memasukan data ke database kita menggunakan pembungkus berupa ContentValues
            dengan tiper data key-value
             */
            val values = ContentValues()
            values.put(DatabaseContract.NoteColums.TITLE, title)
            values.put(DatabaseContract.NoteColums.DESCRIPTION, desc)

            if (isEdit){
                //mengedit data dengan query update
                val result = noteHelper.update(note?.id.toString(), values).toLong()
                if (result > 0){
                    setResult(RESULT_UPDATE, intent)
                    finish()
                }else{
                    Toast.makeText(this, "Gagal Mengupdate Data", Toast.LENGTH_SHORT).show()
                }
            }else{
                //mengambil tanggal saat ini
                note?.date = getCurrentDate()
                values.put(DatabaseContract.NoteColums.DATE, getCurrentDate())

                //memasukan data dengan query insert
                val result = noteHelper.insert(values)

                //cek apakah proses query berhasil atau tidak dengan mengecek nilai kembaliannya
                // yang bertipe long
                if (result > 0){
                    note?.id = result.toInt()
                    setResult(RESULT_ADD, intent)
                    finish()
                }else{
                    Toast.makeText(this, "Gagal Menambah Data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm::ss", Locale.getDefault())
        val date = Date()

        return dateFormat.format(date)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_form, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //actionBar Event
        when(item.itemId){
            //jika yang tambil adalah tombol action
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)      //jika meneka tombol x
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)        //jika menekan tombol back
        }
        return super.onOptionsItemSelected(item)
    }

    /*ketika meneka tombol back*/
    override fun onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE)
    }

    //method menampilakn alert Dialog
    private fun showAlertDialog(type: Int){
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String

        if (isDialogClose){
            dialogTitle = "Batal"
            dialogMessage = "Apakah anda ingin membatalkan Perubahan pada form?"
        }else{
            dialogMessage = "Apakah anda yakin ingin menghapus item ini?"
            dialogTitle = "Hapus Note"
        }

        val alertDialogBuilder = AlertDialog.Builder(this)

        alertDialogBuilder.setTitle(dialogTitle)
        alertDialogBuilder
            .setMessage(dialogMessage)
            .setCancelable(false)
            .setPositiveButton("Ya"){ _, _ ->
                if (isDialogClose){
                    finish()
                }else{
                    val result = noteHelper.delete(note?.id.toString()).toLong()
                    if (result > 0) {
                        val intent = Intent()
                        intent.putExtra(EXTRA_POS, position)
                        setResult(RESULT_DELETE, intent)
                        finish()
                    } else {
                        Toast.makeText(this@NoteAddUpdateActivity, "Gagal menghapus data", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("Tidak"){dialog, _ -> dialog.cancel()}
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}