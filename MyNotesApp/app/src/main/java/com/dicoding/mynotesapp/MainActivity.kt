package com.dicoding.mynotesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mynotesapp.adapter.NoteAdapter
import com.dicoding.mynotesapp.databinding.ActivityMainBinding
import com.dicoding.mynotesapp.db.NoteHelper
import com.dicoding.mynotesapp.entity.Note
import com.dicoding.mynotesapp.helper.MappingHelper
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/*
* Tugas utama MainActivity ada dua. Pertama, menampilkan data dari database pada tabel Note secara
* ascending. Kedua, menerima nilai balik dari setiap aksi dan proses yang dilakukan
* di NoteAddUpdateActivity.
* */

/*
Catatan :
Jika terjadi error dan kemudian Anda mengubah nama di bagian DatabaseContract atau query
di DatabaseHelper pastikan Anda meng-uninstall dulu apk yang lama/meng-clear cache terlebih dahulu.
Karena proses create table hanya dilakukan sekali saat di awal.
* */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NoteAdapter

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    /*
    * Callback dari proses add/update/delete pada Note Form*/
    val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result ->
        if (result.data != null){
            when(result.resultCode){
                //akan dipanggil jika request add
                NoteAddUpdateActivity.RESULT_ADD -> {
                    val note = result.data?.getParcelableExtra<Note>(NoteAddUpdateActivity.EXTRA_NOTE) as Note
                    adapter.addItem(note)               //add item digunakan untuk menambah data baru di paling bawah recyclerView
                    binding.rvNotes.smoothScrollToPosition(adapter.itemCount - 1)
                    showSnackbarMessage("Satu Item Berhasil Ditambahkan")
                }
                //akan dipanggil jika request update
                NoteAddUpdateActivity.RESULT_UPDATE -> {
                    val note = result.data?.getParcelableExtra<Note>(NoteAddUpdateActivity.EXTRA_NOTE) as Note
                    val position = result.data?.getIntExtra(NoteAddUpdateActivity.EXTRA_POS, 0) as Int
                    adapter.updateItem(position, note)      //method digunakan untuk mengedit data pada rv di posisi yang telah ditentukan
                    binding.rvNotes.smoothScrollToPosition(position)
                    showSnackbarMessage("Satu Item Berhasil Diubah")
                }
                //akan dipanggil jika request delete
                NoteAddUpdateActivity.RESULT_DELETE -> {
                    val position = result.data?.getIntExtra(NoteAddUpdateActivity.EXTRA_POS, 0) as Int
                    adapter.removeItem(position)            //menghapous data pada RecyclerView
                    showSnackbarMessage("Satu Item Berhasil Dihapus")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Notes"

        binding.rvNotes.layoutManager = LinearLayoutManager(this)
        binding.rvNotes.setHasFixedSize(true)

        adapter = NoteAdapter(object : NoteAdapter.OnItemClickCallBack {
            /*Prose update dilakukan dengan melakukan klik pada item recyclerview*/
            override fun onItemClickCallBack(selectedNote: Note?, position: Int) {
                //mengirimkan data awal pada main activity ke form update
                val intent = Intent(this@MainActivity, NoteAddUpdateActivity::class.java)
                intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, selectedNote)
                intent.putExtra(NoteAddUpdateActivity.EXTRA_POS, position)
                resultLauncher.launch(intent)
            }
        })
        binding.rvNotes.adapter = adapter

        //floating action button event handler untuk menambah data
        binding.fabAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, NoteAddUpdateActivity::class.java)
            resultLauncher.launch(intent)
        }

        if (savedInstanceState != null){
            loadNotesAsync()
        }else {
            val list = savedInstanceState?.getParcelableArrayList<Note>(EXTRA_STATE)
            if (list != null) {
                adapter.listNotes = list
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listNotes)
    }

    //method load data dari database secara async
    private fun loadNotesAsync() {
        /*lifecycleScope merupakan scope yang dibuat khusus untuk Android agar dapat menjalankan
        Coroutine yang aware dengan state pada lifecycle.*/
        lifecycleScope.launch{
            binding.prgoressBar.visibility = View.VISIBLE
            val noteHelper = NoteHelper.getInstance(applicationContext)
            noteHelper.open()       //open koneksi database

            /*
            * Menggunakan fungsi async karena kita menginginkan nilai kembalian dari fungsi berupa
            * data listNotes*/
            val defferedNotes = async(Dispatchers.IO) {
                val cursor = noteHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }
            binding.prgoressBar.visibility = View.INVISIBLE
            val notes = defferedNotes.await()               //mengambil data dari async

            if (notes.size > 0){
                adapter.listNotes = notes
            }else{
                adapter.listNotes = ArrayList()
                showSnackbarMessage("Tidak Ada Data Saat Ini")
            }
            noteHelper.close()      //tutup koneksi database ketika sudah selesai mengambil data
        }
    }

    private fun showSnackbarMessage(message: String){
        Snackbar.make(binding.rvNotes, message, Snackbar.LENGTH_SHORT).show()
    }
}