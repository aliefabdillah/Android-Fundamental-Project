package com.dicoding.mysharedpreferences

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.dicoding.mysharedpreferences.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var mUserPreference: UserPreference
    private var isPreferenceEmpty = false
    private lateinit var userModel: UserModel
    private lateinit var binding: ActivityMainBinding

    /*
    * Kita mendapatakn nilai result dari FormUserPreference yang menggunakan EXTRA RESULT dengan
    * menyamakan resultCodenya */
    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        result: ActivityResult ->
        if (result.data != null && result.resultCode == FormUserPreferenceActivity.RESULT_CODE){
            //mendapatkan data yang dikirimkan dari FormUserPreference
            userModel = result.data?.getParcelableExtra<UserModel>(FormUserPreferenceActivity.EXTRA_RESULT) as UserModel
            //dan langsung mengupdate data nya ke dalam view activity
            populateView(userModel)
            checkForm(userModel)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "My User Preference"
        mUserPreference = UserPreference(this)
        showExistingPreference()                        //menampilkan preferences saat ini

        binding.btnSave.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_save) {
            val intent = Intent(this@MainActivity, FormUserPreferenceActivity::class.java)
            when {
                //jika state bernilai true/data preference masih kosong
                isPreferenceEmpty -> {
                    //mengirim data ke form dengan tipe add
                    intent.putExtra(
                        FormUserPreferenceActivity.EXTRA_TYPE_FORM,
                        FormUserPreferenceActivity.TYPE_ADD
                    )
                    //mengirim dat usermodel saat itu
                    intent.putExtra("USER", userModel)
                }
                //jika state bernilai true/data preference tidak kosong
                else -> {
                    //mengirim data ke form dengan tipe edit
                    intent.putExtra(
                        FormUserPreferenceActivity.EXTRA_TYPE_FORM,
                        FormUserPreferenceActivity.TYPE_EDIT
                    )
                    intent.putExtra("USER", userModel)
                }
            }
            //berpindah activity menggunakan method lauch dari ActivityResultLauncher
            /* Perbedaannya registerForActivityResult tidak hanya berpindah Activity, namun
            juga mendapatkan result (hasil) dari Activity tersebut.*/
            resultLauncher.launch(intent)
        }
    }

    //menampilkan data preference ke layout view
    private fun showExistingPreference() {
        userModel = mUserPreference.getUser()       //mengambil data preferences
        populateView(userModel)                 //menyimpan nilai user model ke view
        checkForm(userModel)                    //method untuk check apakah data kosong atau tidak
    }

    //menyimpan nilai yang di ambil dari user model dan menyimpan ke dalam text view
    private fun populateView(userModel: UserModel) {
        binding.tvName.text = if (userModel.name.toString().isEmpty()) "Tidak Ada" else userModel.name
        binding.tvAge.text = if (userModel.age.toString().isEmpty()) "Tidak Ada" else userModel.age.toString()
        binding.tvIsLoveMu.text = if (userModel.isLove) "Ya" else "Tidak"
        binding.tvEmail.text = if (userModel.email.toString().isEmpty()) "Tidak Ada" else userModel.email
        binding.tvPhoneNumber.text = if (userModel.phoneNumber.toString().isEmpty()) "Tidak Ada" else userModel.phoneNumber
    }

    //method cehck apakah view masih kosong atau tidak
    private fun checkForm(userModel: UserModel) {
        when {
            userModel.name.toString().isNotEmpty() -> {
                binding.btnSave.text = getString(R.string.change)       //text pada btn  menjadi ubah
                isPreferenceEmpty = false                               //state isPrefreneceEmpty menjadi false
            }
            else -> {
                binding.btnSave.text = getString(R.string.add)          //text pada btn menjadi tambah
                isPreferenceEmpty = true                                //state isPrefreneceEmpty menjadi true
            }
        }
    }

}