package org.d3if43.jagakandang.ui.manageiot

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import org.d3if43.jagakandang.IotAdapter
import org.d3if43.jagakandang.IotViewModel
import org.d3if43.jagakandang.IotViewModelFactory
import org.d3if43.jagakandang.R
import org.d3if43.jagakandang.data.HomeDataClass
import org.d3if43.jagakandang.data.IotDataClass
import org.d3if43.jagakandang.data.IotDb
import org.d3if43.jagakandang.databinding.FragmentManageBinding
import org.d3if43.jagakandang.ui.home.DetailHomeActivity


class ManageFragment : Fragment() {
    private lateinit var binding: FragmentManageBinding
    private lateinit var myAdapter: IotAdapter
    private lateinit var data: IotDataClass
    private val viewModel: IotViewModel by lazy {
        val dataSource = IotDb.getInstance().dao
        val factory = IotViewModelFactory(dataSource)
        ViewModelProvider(this, factory).get(IotViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManageBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myAdapter = IotAdapter(handler)
        with(binding.iotRecyclerView){
            setHasFixedSize(true)
            adapter = myAdapter
        }

        binding.cameraButton.setOnClickListener{
            val namaPackage = "com.ezviz"
            var launchIntent: Intent? = null
            launchIntent = requireContext().packageManager.getLaunchIntentForPackage(namaPackage)
            if(launchIntent == null){
                launchIntent = Intent(Intent.ACTION_VIEW)
                launchIntent.data = Uri.parse("https://play.google.com/store/apps/details?id=" + namaPackage)
                startActivity(launchIntent)
            }
            else{
                startActivity(launchIntent)
            }
        }

        viewModel.data.observe(this, {
            myAdapter.submitList(it)
            binding.emptyManageTextView.visibility = if(it.isEmpty()) View.VISIBLE
            else View.GONE})
    }
    private val handler = object : IotAdapter.ClickHandler {
        override fun onClick(position: Int, iotDataClass: IotDataClass) {
            myAdapter.toggleSelection(position)
            val list = myAdapter.getSelection()[0]
            data = iotDataClass
            viewModel.switchLogic1(list, data)
            myAdapter.resetSelection()
        }

        override fun onClick2(position: Int, iotDataClass: IotDataClass) {
            myAdapter.toggleSelection(position)
            val list = myAdapter.getSelection()[0]
            data = iotDataClass
            viewModel.switchLogic2(list, data)
            myAdapter.resetSelection()
        }
        override fun onClick3(position: Int, iotDataClass: IotDataClass) {
            myAdapter.toggleSelection(position)
            val list = myAdapter.getSelection()[0]
            data = iotDataClass
            viewModel.switchLogic3(list, data)
            myAdapter.resetSelection()
        }

        override fun onClick4(position: Int, iotDataClass: IotDataClass) {
            myAdapter.toggleSelection(position)
            val list = myAdapter.getSelection()[0]
            data = iotDataClass
            viewModel.switchLogic4(list, data)
            myAdapter.resetSelection()
        }

        override fun onClick5(position: Int, iotDataClass: IotDataClass) {
            myAdapter.toggleSelection(position)
            val list = myAdapter.getSelection()[0]
            data = iotDataClass
            AlertDialog.Builder(activity).apply{
                setMessage(R.string.reset_message_iot)
                setPositiveButton(R.string.reset) { _, _ ->
                    viewModel.switchLogic5(list, data)
                    myAdapter.resetSelection()
                }
                setNegativeButton(R.string.batal) { dialog, _ ->
                    dialog.cancel()
                    myAdapter.resetSelection()
                }
                show()
                myAdapter.resetSelection()
            }
        }

        override fun onClick6(position: Int, iotDataClass: IotDataClass) {
            myAdapter.toggleSelection(position)
            val list = myAdapter.getSelection()[0]
            data = iotDataClass
            AlertDialog.Builder(activity).apply{
                setMessage(R.string.delete_message_iot)
                setPositiveButton(R.string.hapus) { _, _ ->
                    viewModel.deleteData(list)
                    myAdapter.resetSelection()
                }
                setNegativeButton(R.string.batal) { dialog, _ ->
                    dialog.cancel()
                    myAdapter.resetSelection()
                }
                show()
                myAdapter.resetSelection()
            }
        }

        override fun onClick7(position: Int, iotDataClass: IotDataClass) {
            data = iotDataClass
            if(myAdapter.getSelection().isNotEmpty()){
                return
            }
            else
                myAdapter.toggleSelection(position)
                myAdapter.getSelection().first()
                val intent = Intent(requireContext(), IotDetailActivity::class.java)
                intent.putExtra("IOT_DATA", data)
                startActivity(intent)
                myAdapter.resetSelection()
            return

        }

        override fun onClick8(position: Int, iotDataClass: IotDataClass) {
            myAdapter.toggleSelection(position)
            val list = myAdapter.getSelection()[0]
            data = iotDataClass
            AlertDialog.Builder(activity).apply{
                setMessage(R.string.automation_on_message)
                setPositiveButton(R.string.ya) { _, _ ->
                    viewModel.switchLogic6(list, data)
                    myAdapter.resetSelection()
                }
                setNegativeButton(R.string.batal) { dialog, _ ->
                    dialog.cancel()
                    myAdapter.resetSelection()
                }
                show()
                myAdapter.resetSelection()
            }
        }

        override fun onClick9(position: Int, iotDataClass: IotDataClass) {
            myAdapter.toggleSelection(position)
            val list = myAdapter.getSelection()[0]
            data = iotDataClass
            AlertDialog.Builder(activity).apply{
                setMessage(R.string.automation_off_message)
                setPositiveButton(R.string.ya) { _, _ ->
                    viewModel.switchLogic7(list, data)
                    myAdapter.resetSelection()
                }
                setNegativeButton(R.string.batal) { dialog, _ ->
                    dialog.cancel()
                    myAdapter.resetSelection()
                }
                show()
                myAdapter.resetSelection()
            }
        }


    }

}