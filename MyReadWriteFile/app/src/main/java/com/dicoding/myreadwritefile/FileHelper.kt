package com.dicoding.myreadwritefile

import android.content.Context

internal object FileHelper {
    /*
    * Menulis Berkas ke file
    * Logika : Menyimpan data berupa string ke dalam sebuah berkas internal storage dengan komponen
    * FileOutputStream*/
    fun writeToFile(fileModel: FileModel, context: Context){
        /*
        * disini menggunakan methode openFileOutput, jika berkas belum ada akan otomatis dibuatkan
        * dalam penggunaan method ini harus mencatumkan context aplikasi yang digunakan sehingga
        * harus mencatunkan nya di parameter fungsi*/
        context.openFileOutput(fileModel.filename, Context.MODE_PRIVATE).use {
            //setelah dibuka file dapat dituliskan dalam bentuk ByteArray
            it.write(fileModel.data?.toByteArray())
        }
    }

    /*
    * Method untuk membuka file
    * menggunakan komponen FileInputStream dengan methode openFileInput, dan akan membaca berkas menggunaka
    * stream dan data tiap baris dalam berkas akan didapatakan dengan fungsi bufferedReader()*/
    fun readFromFile(context: Context, filename: String): FileModel{
        val fileModel = FileModel()
        fileModel.filename = filename
        fileModel.data = context.openFileInput(filename).bufferedReader().useLines { lines ->
            lines.fold("") {
                some, text ->
                text
            }
        }
        return fileModel
    }
}