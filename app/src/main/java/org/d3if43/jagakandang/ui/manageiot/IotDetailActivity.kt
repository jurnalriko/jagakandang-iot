package org.d3if43.jagakandang.ui.manageiot

import android.app.AlertDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import org.d3if43.jagakandang.IotViewModel
import org.d3if43.jagakandang.IotViewModelFactory
import org.d3if43.jagakandang.R
import org.d3if43.jagakandang.data.HomeDataClass
import org.d3if43.jagakandang.data.IotDataClass
import org.d3if43.jagakandang.data.IotDb
import org.d3if43.jagakandang.databinding.ActivityIotDetailBinding
import org.d3if43.jagakandang.databinding.DialogIotBinding

class IotDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityIotDetailBinding
    private lateinit var dialogBinding: DialogIotBinding
    private val viewModel: IotViewModel by lazy {
        val dataSource = IotDb.getInstance().dao
        val factory = IotViewModelFactory(dataSource)
        ViewModelProvider(this, factory).get(IotViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIotDetailBinding.inflate(layoutInflater)
        val inflater = LayoutInflater.from(this)
        dialogBinding = DialogIotBinding.inflate(inflater, null, false)
        setContentView(binding.root)
        supportActionBar?.hide()
        fetchList()
    }
    private fun fetchList() {
        val iotData = intent.getParcelableExtra<IotDataClass>("IOT_DATA")
        binding.infoPerangkatIdTextView.text = iotData?.id
        binding.detailNamaAlatTitleTextView.text = iotData?.nama_perangkat
        binding.infoNamaAlatTextView.text = iotData?.nama_perangkat
        binding.infoLokasiAlatTextView.text = iotData?.lokasi_perangkat
        binding.infoLuasLokasiTextView.text = iotData?.luas_lokasi
        binding.infoSuhuMinimalTextView.text = iotData?.temperature_min
        binding.infoSuhuMaksimalTextView.text = iotData?.temperature_max
        binding.infoKelembabanMinimalTextView.text = iotData?.humidity_min
        binding.infoKelembabanMaksimalTextView.text = iotData?.humidity_max
        binding.infoAmoniaMaksimalTextView.text = iotData?.amonia_max
        if(iotData?.wifi_config == "1"){
            binding.detailStatusConnectionTitleTextView.setText(R.string.online)
        }
        else
            binding.detailStatusConnectionTitleTextView.setText(R.string.offline)

        binding.iotEditButton.setOnClickListener{
            AlertDialog.Builder(this).apply {
                setTitle(R.string.update_data_perangkat)
                setView(dialogBinding.root)
                dialogBinding.editTextNamaPerangkat.setText(iotData?.nama_perangkat)
                dialogBinding.editTextLokasiPerangkat.setText(iotData?.lokasi_perangkat)
                dialogBinding.editTextLuasLokasi.setText(iotData?.luas_lokasi)
                dialogBinding.editTextSuhuMin.setText(iotData?.temperature_min)
                dialogBinding.editTextSuhuMaks.setText(iotData?.temperature_max)
                dialogBinding.editTextKelembabanMin.setText(iotData?.humidity_min)
                dialogBinding.editTextKelembabanMaks.setText(iotData?.humidity_max)
                dialogBinding.editTextAmoniaMaks.setText(iotData?.amonia_max)
                setPositiveButton(R.string.simpan) { _, _ ->
                    val iotDataClass = getData() ?: return@setPositiveButton
                    iotDataClass.id = iotData!!.id
                    iotDataClass.humidity = iotData.humidity
                    iotDataClass.temperature = iotData.temperature
                    iotDataClass.amonia = iotData.amonia
                    iotDataClass.wifi_config = iotData.wifi_config
                    iotDataClass.channel_1 = iotData.channel_1
                    iotDataClass.channel_2 = iotData.channel_2
                    iotDataClass.automation_status = iotData.automation_status
                    binding.infoPerangkatIdTextView.text = iotDataClass.id
                    binding.detailNamaAlatTitleTextView.text = iotDataClass.nama_perangkat
                    binding.infoNamaAlatTextView.text = iotDataClass.nama_perangkat
                    binding.infoLokasiAlatTextView.text = iotDataClass.lokasi_perangkat
                    binding.infoLuasLokasiTextView.text = iotDataClass.luas_lokasi
                    binding.infoSuhuMinimalTextView.text = iotDataClass.temperature_min
                    binding.infoSuhuMaksimalTextView.text = iotDataClass.temperature_max
                    binding.infoKelembabanMinimalTextView.text = iotDataClass.humidity_min
                    binding.infoKelembabanMaksimalTextView.text = iotDataClass.humidity_max
                    binding.infoAmoniaMaksimalTextView.text = iotDataClass.amonia_max
                    viewModel.updateData(iotDataClass)
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

    private fun getData(): IotDataClass? {
        if(dialogBinding.editTextNamaPerangkat.text.isEmpty()) {
            showMessage(R.string.nama_perangkat_wajib_diisi)
            return null
        }
        if(dialogBinding.editTextLokasiPerangkat.text.isEmpty()){
            showMessage(R.string.lokasi_perangkat_wajib_diisi)
            return null
        }
        if(dialogBinding.editTextLuasLokasi.text.isEmpty()){
            showMessage(R.string.luas_lokasi_wajib_diisi)
            return null
        }
        if(dialogBinding.editTextSuhuMin.text.isEmpty()){
            showMessage(R.string.suhu_min_wajib_diisi)
            return null
        }
        if(dialogBinding.editTextSuhuMaks.text.isEmpty()){
            showMessage(R.string.suhu_maks_wajib_diisi)
            return null
        }
        if(dialogBinding.editTextSuhuMaks.text.toString().toFloat() <= dialogBinding.editTextSuhuMin.text.toString().toFloat()){
            showMessage(R.string.suhu_maks_harus_diatas_suhu_min)
            return null
        }

        if(dialogBinding.editTextKelembabanMin.text.isEmpty()){
            showMessage(R.string.kelembaban_min_wajib_diisi)
            return null
        }
        if(dialogBinding.editTextKelembabanMaks.text.isEmpty()){
            showMessage(R.string.kelembaban_max_wajib_diisi)
            return null
        }
        if(dialogBinding.editTextKelembabanMaks.text.toString().toFloat() <= dialogBinding.editTextKelembabanMin.text.toString().toFloat()){
            showMessage(R.string.kelembaban_max_harus_diatas_kelembaban_min)
            return null
        }
        if(dialogBinding.editTextAmoniaMaks.text.isEmpty()){
            showMessage(R.string.amonia_max_wajib_diisi)
            return null
        }
        return IotDataClass(
            nama_perangkat = dialogBinding.editTextNamaPerangkat.text.toString(),
            lokasi_perangkat = dialogBinding.editTextLokasiPerangkat.text.toString(),
            luas_lokasi = dialogBinding.editTextLuasLokasi.text.toString(),
            temperature_min = dialogBinding.editTextSuhuMin.text.toString(),
            temperature_max = dialogBinding.editTextSuhuMaks.text.toString(),
            humidity_min = dialogBinding.editTextKelembabanMin.text.toString(),
            humidity_max = dialogBinding.editTextKelembabanMaks.text.toString(),
            amonia_max = dialogBinding.editTextAmoniaMaks.text.toString()
        )
    }
}