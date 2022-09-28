package org.d3if43.jagakandang.ui.home

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import org.d3if43.jagakandang.HomeViewModel
import org.d3if43.jagakandang.HomeViewModelFactory
import org.d3if43.jagakandang.R
import org.d3if43.jagakandang.data.HomeDataClass
import org.d3if43.jagakandang.data.HomeDb
import org.d3if43.jagakandang.databinding.ActivityDetailHomeBinding
import org.d3if43.jagakandang.databinding.DialogHomeBinding

class DetailHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailHomeBinding
    private lateinit var dialogBinding : DialogHomeBinding
    private val viewModel: HomeViewModel by lazy {
        val dataSource = HomeDb.getInstance(this).dao
        val factory = HomeViewModelFactory(dataSource)
        ViewModelProvider(this, factory).get(HomeViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHomeBinding.inflate(layoutInflater)
        val inflater = LayoutInflater.from(this)
        dialogBinding = DialogHomeBinding.inflate(inflater, null, false)
        setContentView(binding.root)
        supportActionBar?.hide()
        fetchList()

    }
    private fun fetchList(){
        val homeData = intent.getParcelableExtra<HomeDataClass>("HOME_DATA")
        binding.detailNamaKandangTextView.text = homeData?.namaKandang
        binding.detailLokasiKandangTitleTextView.text = homeData?.lokasi
        binding.infoNamaKandangTextView.text = homeData?.namaKandang
        binding.infoLokasiTextView.text = homeData?.lokasi
        binding.infoPopulasiTextView.text = homeData?.populasi
        binding.infoPakanTextView.text = homeData?.jumlahPakan
        binding.infoKematianTextView.text = homeData?.kematian
        binding.infoTanggalTextView.text = homeData?.tglChickIn
        binding.infoKeteranganTextView.text = homeData?.keterangan

        binding.editDetailHomeButton.setOnClickListener{
                AlertDialog.Builder(this).apply {
                setTitle(R.string.update_data_kandang)
                setView(dialogBinding.root)
                dialogBinding.editTextNamaKandang.setText(homeData?.namaKandang)
                dialogBinding.editTextLokasi.setText(homeData?.lokasi)
                dialogBinding.editTextTanggal.setText(homeData?.tglChickIn)
                dialogBinding.editTextPopulasi.setText(homeData?.populasi)
                dialogBinding.editTextKematian.setText(homeData?.kematian)
                dialogBinding.editTextPakan.setText(homeData?.jumlahPakan)
                dialogBinding.editTextKeterangan.setText(homeData?.keterangan)
                    setPositiveButton(R.string.simpan) { _, _ ->
                        val homeDataClass = getData() ?: return@setPositiveButton
                        homeDataClass.id = homeData!!.id
                        binding.detailNamaKandangTextView.setText(homeDataClass.namaKandang)
                        binding.detailLokasiKandangTitleTextView.setText(homeDataClass.lokasi)
                        binding.infoNamaKandangTextView.setText(homeDataClass.namaKandang)
                        binding.infoLokasiTextView.setText(homeDataClass.lokasi)
                        binding.infoPopulasiTextView.setText(homeDataClass.populasi)
                        binding.infoPakanTextView.setText(homeDataClass.jumlahPakan)
                        binding.infoKematianTextView.setText(homeDataClass.kematian)
                        binding.infoTanggalTextView.setText(homeDataClass.tglChickIn)
                        binding.infoKeteranganTextView.setText(homeDataClass.keterangan)
                        viewModel.updateData(homeDataClass)
                    }
                    setNegativeButton(R.string.batal) { dialog, _ ->
                        dialog.cancel()
                    }
                }.create().show()
            }
    }

    private fun showMessage(messageResId: Int) {
        Toast.makeText(this, messageResId, Toast.LENGTH_LONG).apply {
            setGravity(Gravity.CENTER, 0, 0)
            show()
        }
    }

    private fun getData(): HomeDataClass? {
        if(dialogBinding.editTextNamaKandang.text.isEmpty()) {
            showMessage(R.string.nama_kandang_wajib_diisi)
            return null
        }
        if(dialogBinding.editTextLokasi.text.isEmpty()){
            showMessage(R.string.lokasi_wajib_diisi)
            return null
        }
        if(dialogBinding.editTextPopulasi.text.isEmpty()){
            showMessage(R.string.populasi_wajib_diisi)
            return null
        }
        if(dialogBinding.editTextKematian.text.isEmpty()){
            showMessage(R.string.kematian_wajib_diisi)
            return null
        }
        if(dialogBinding.editTextPakan.text.isEmpty()){
            showMessage(R.string.pakan_wajib_diisi)
            return null
        }
        if(dialogBinding.editTextTanggal.text.isEmpty()){
            showMessage(R.string.tanggal_wajib_diisi)
            return null
        }
        return HomeDataClass(
            namaKandang = dialogBinding.editTextNamaKandang.text.toString(),
            lokasi = dialogBinding.editTextLokasi.text.toString(),
            populasi = dialogBinding.editTextPopulasi.text.toString(),
            kematian = dialogBinding.editTextKematian.text.toString(),
            jumlahPakan = dialogBinding.editTextPakan.text.toString(),
            tglChickIn = dialogBinding.editTextTanggal.text.toString(),
            keterangan = dialogBinding.editTextKeterangan.text.toString()
        )
    }
}