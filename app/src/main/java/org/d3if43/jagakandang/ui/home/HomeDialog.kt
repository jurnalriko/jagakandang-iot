package org.d3if43.jagakandang.ui.home

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import org.d3if43.jagakandang.R
import org.d3if43.jagakandang.data.HomeDataClass
import org.d3if43.jagakandang.databinding.DialogHomeBinding

class HomeDialog : DialogFragment() {
    private lateinit var binding : DialogHomeBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(requireContext())
        binding = DialogHomeBinding.inflate(inflater, null, false)

        val builder = AlertDialog.Builder(activity).apply {
            setTitle(R.string.tambah_data_kandang)
            setView(binding.root)
            setPositiveButton(R.string.simpan) { _, _ ->
                val homeDataClass = getData() ?: return@setPositiveButton
                val listener = parentFragment as DialogListener
                listener.processDialog(homeDataClass)
            }
            setNegativeButton(R.string.batal) { _, _ -> dismiss() }

        }
        return builder.create()
    }

    interface DialogListener {
        fun processDialog(homeDataClass: HomeDataClass)
    }


    private fun showMessage(messageResId: Int) {
        Toast.makeText(requireContext(), messageResId, Toast.LENGTH_LONG).apply {
            setGravity(Gravity.CENTER, 0, 0)
            show()
        }
    }


    private fun getData(): HomeDataClass? {
        if(binding.editTextNamaKandang.text.isEmpty()) {
            showMessage(R.string.nama_kandang_wajib_diisi)
            return null
        }
        if(binding.editTextLokasi.text.isEmpty()){
            showMessage(R.string.lokasi_wajib_diisi)
            return null
        }
        if(binding.editTextPopulasi.text.isEmpty()){
            showMessage(R.string.populasi_wajib_diisi)
            return null
        }
        if(binding.editTextKematian.text.isEmpty()){
            return HomeDataClass(
                namaKandang = binding.editTextNamaKandang.text.toString(),
                lokasi = binding.editTextLokasi.text.toString(),
                populasi = binding.editTextPopulasi.text.toString(),
                kematian = "0",
                jumlahPakan = binding.editTextPakan.text.toString(),
                tglChickIn = binding.editTextTanggal.text.toString(),
                keterangan = binding.editTextKeterangan.text.toString()
            )
        }
        if(binding.editTextPakan.text.isEmpty()){
            showMessage(R.string.pakan_wajib_diisi)
            return null
        }
        if(binding.editTextTanggal.text.isEmpty()){
            showMessage(R.string.tanggal_wajib_diisi)
            return null
        }
        return HomeDataClass(
            namaKandang = binding.editTextNamaKandang.text.toString(),
            lokasi = binding.editTextLokasi.text.toString(),
            populasi = binding.editTextPopulasi.text.toString(),
            kematian = binding.editTextKematian.text.toString(),
            jumlahPakan = binding.editTextPakan.text.toString(),
            tglChickIn = binding.editTextTanggal.text.toString(),
            keterangan = binding.editTextKeterangan.text.toString()
        )
    }
}