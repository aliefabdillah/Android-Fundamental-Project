package com.dicoding.myunittest

import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.mockito.Mockito.*

class MainViewModelTest {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var cuboidModel: CuboidModel

    //dummy variaeble untuk test
    private val dummyLength = 12.0
    private val dummyWidth = 7.0
    private val dummyHeight = 6.0

//    private val dummyVolume = 500.0         //akan menampilkan error karena hasilnya harusnya 504
    private val dummyVolume = 504.0
    private val dummyCircumference = 100.0
    private val dummySurfaceArea = 396.0

    @Before
    fun before(){
        cuboidModel = mock(CuboidModel::class.java)
        mainViewModel = MainViewModel(cuboidModel)
    }

    @Test
    fun getCircumference() {
        cuboidModel = CuboidModel()
        mainViewModel = MainViewModel(cuboidModel)
        mainViewModel.save(dummyWidth, dummyLength, dummyHeight)
        val circumference = mainViewModel.getCircumference()
        assertEquals(dummyCircumference, circumference, 0.0001)
    }

    @Test
    fun getSurfaceArea() {
        cuboidModel = CuboidModel()
        mainViewModel = MainViewModel(cuboidModel)
        mainViewModel.save(dummyWidth, dummyLength, dummyHeight)
        val surfaceArea = mainViewModel.getSurfaceArea()
        assertEquals(dummySurfaceArea, surfaceArea, 0.0001)
    }

    @Test
    fun getVolume() {
        cuboidModel = CuboidModel()
        mainViewModel = MainViewModel(cuboidModel)
        mainViewModel.save(dummyWidth, dummyLength, dummyHeight)
        val volume = mainViewModel.getVolume()
        assertEquals(dummyVolume, volume, 0.0001)
        /*
        * angka 0.0001 merupakan  angka delta yang merupakan selissih range dibelakagn
        * koma bilangan double*/
    }

    /* Pengujian Menggunakan Mock */
    @Test
    fun testMockVolume() {
        `when`(mainViewModel.getVolume()).thenReturn(dummyVolume)
        val volume = mainViewModel.getVolume()
        verify(cuboidModel).getVolume()
        assertEquals(dummyVolume, volume, 0.0001)
    }
    @Test
    fun testMockCircumference() {
        `when`(mainViewModel.getCircumference()).thenReturn(dummyCircumference)
        val circumference = mainViewModel.getCircumference()
        verify(cuboidModel).getCircumference()
        assertEquals(dummyCircumference, circumference, 0.0001)
    }
    @Test
    fun testMockSurfaceArea() {
        `when`(mainViewModel.getSurfaceArea()).thenReturn(dummySurfaceArea)
        val surfaceArea = mainViewModel.getSurfaceArea()
        verify(cuboidModel).getSurfaceArea()
        assertEquals(dummySurfaceArea, surfaceArea, 0.0001)
    }

    /*
    * Manfaat Penggunaan Mocking
    * - Menghindari Terlalu Banyak Dependency
    * - Mengurangi kelebihan beban (overload)
    * - Bypass kendala waktu dalam fungsi */

    /* Anonitation
    * @Before
        Fungsinya untuk menginisialisasi method sebelum melakukan test. Method yang diberi anotasi
        @Before ini akan dijalankan sebelum menjalankan semua method dengan anotasi @Test. Selain
        anotasi @Before, dalam melakukan Unit Test juga ada anotasi @After yang berfungsi sebaliknya
        dari anotasi @Before, yaitu untuk menginisialisai method yang akan dijalankan setelah
        method dengan anotasi @Test.
      @Test
        Anotasi ini digunakan pada method yang akan dites.
    */

    /*Fungsi Test Yang digunakan
    mock()
        Fungsinya untuk membuat obyek mock yang akan menggantikan obyek yang asli.
    when()
        Digunakan untuk menandakan event di mana Anda ingin memanipulasi behavior dari mock object.
    thenReturn()
        Digunakan untuk memanipulasi output dari mock object.
    verify()
        Digunakan untuk memeriksa metode dipanggil dengan arguman yang diberikan. Verify merupkan
        fungsi dari framework Mockito
    assertEquals()
        Fungsi ini merupakan fungsi dari JUnit yang digunakan untuk memvalidasi output yang
        diharapkan dan output yang sebenarnya.
    */
}