package com.dicoding.restoranreview

/*
* Class wrapper untuk menghandler snackbar event pada live data*/
open class Event<out T>(private val content: T) {

    @Suppress("MemberVisibilityCanBePrivate")
    var hasBeenHandled = false
        private set     // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     * fungsi utama dari kelas ini yaitu terdapat pada fungsi getContentIfNotHandled().
     * Fungsi tersebut akan memeriksa apakah aksi ini pernah dieksekusi sebelumnya.
     * Caranya yaitu dengan memanipulasi variabel hasBeenHandled.
     *
     * Awalnya variabel hasBeenHandled bernilai false. Kemudian ketika aksi pertama kali dilakukan
     * nilai hasBeenHandled akan diubah menjadi true. Sedangkan pada aksi selanjutnya ia akan
     * mengembalikan null karena hasBeenHandled telah bernilai true.
     *
     * Selain itu, ada juga fungsi peekContent yang bisa Anda gunakan untuk melihat nilai dari
     * content walaupun aksi event sudah dilakukan.
     */
    fun getContentIfNotHandled() : T? {
        return if  (hasBeenHandled) {
            null
        }else{
            hasBeenHandled = true
            content
        }
    }
}