package org.d3if43.jagakandang

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import org.d3if43.jagakandang.data.IotDataClass
import org.d3if43.jagakandang.databinding.ListIotBinding
import java.util.ArrayList

class IotAdapter(private val handler: ClickHandler) :
    ListAdapter<IotDataClass, IotAdapter.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<IotDataClass>() {
            override fun areItemsTheSame(
                oldData: IotDataClass, newData: IotDataClass
            ): Boolean {
                return oldData.id == newData.id
            }

            override fun areContentsTheSame(
                oldData: IotDataClass, newData: IotDataClass
            ): Boolean {
                return oldData == newData
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IotAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListIotBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IotAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ListIotBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val mainActivity = MainActivity()

        fun bind(iotDataClass: IotDataClass) {
            binding.deviceTitleTextView.text = iotDataClass.nama_perangkat
            binding.lokasiTitleTextView.text = iotDataClass.lokasi_perangkat
            val pos = absoluteAdapterPosition
            itemView.isSelected = selectionIds.contains(iotDataClass.id)
            itemView.setOnClickListener { handler.onClick7(pos, iotDataClass) }

            if (iotDataClass.wifi_config == "1") {
                binding.statusKoneksiTextView.setText(R.string.online)
                binding.kelembabanTextView.text = iotDataClass.humidity
                binding.suhuTextView.text = iotDataClass.temperature
                binding.amoniaTextView.text = iotDataClass.amonia
                binding.amoniaTextView.setTextColor(Color.parseColor("#FFFFFF"))
                binding.satuanAmoniaTextView.setTextColor(Color.parseColor("#FFFFFF"))
                if (iotDataClass.humidity < iotDataClass.humidity_min) {
                    binding.kelembabanTextView.setTextColor(Color.parseColor("#a3524e"))
                    binding.satuanKelembabanTextView.setTextColor(Color.parseColor("#a3524e"))
                    mainActivity.createWorkRequest("Kelembaban saat ini dibawah minimal")
                } else if (iotDataClass.humidity > iotDataClass.humidity_max) {
                    binding.kelembabanTextView.setTextColor(Color.parseColor("#6F9FFF"))
                    binding.satuanKelembabanTextView.setTextColor(Color.parseColor("#6F9FFF"))
                    mainActivity.createWorkRequest("Kelembaban saat ini diatas maksimal")
                } else {
                    binding.kelembabanTextView.setTextColor(Color.parseColor("#FFFFFF"))
                    binding.satuanKelembabanTextView.setTextColor(Color.parseColor("#FFFFFF"))
                }
                if (iotDataClass.temperature < iotDataClass.temperature_min) {
                    binding.suhuTextView.setTextColor(Color.parseColor("#6F9FFF"))
                    binding.satuanSuhuTextView.setTextColor(Color.parseColor("#6F9FFF"))
                    mainActivity.createWorkRequest("Suhu saat ini dibawah minimal")
                } else if (iotDataClass.temperature > iotDataClass.temperature_max) {
                    binding.suhuTextView.setTextColor(Color.parseColor("#a3524e"))
                    binding.satuanSuhuTextView.setTextColor(Color.parseColor("#a3524e"))
                    mainActivity.createWorkRequest("Suhu saat ini diatas maksimal")
                } else {
                    binding.suhuTextView.setTextColor(Color.parseColor("#FFFFFF"))
                    binding.satuanSuhuTextView.setTextColor(Color.parseColor("#FFFFFF"))
                }

                if (iotDataClass.automation_status == "1") {
                    binding.layoutAutomation.setBackgroundResource(R.drawable.shape_on)
                    binding.automationTextView.setTextColor(Color.parseColor("#FFFFFF"))
                    binding.automationStatus.setTextColor(Color.parseColor("#FFFFFF"))
                    binding.automationStatus.setText(R.string.on)
                    binding.layoutAutomation.setOnClickListener {
                        handler.onClick9(pos, iotDataClass)
                    }
                    binding.layoutChannel1.setOnClickListener {
                        handler.onClick7(
                            pos,
                            iotDataClass
                        )
                    }
                    binding.layoutChannel2.setOnClickListener {
                        handler.onClick7(
                            pos,
                            iotDataClass
                        )
                    }
                    binding.layoutChannel1.setBackgroundResource(R.drawable.shape_on)
                    binding.channel1TextView.setText(R.string.blower)
                    binding.channel1TextView.setTextColor(Color.parseColor("#FFFFFF"))
                    binding.statusChannel1TextView.setTextColor(Color.parseColor("#FFFFFF"))
                    binding.statusChannel1TextView.setText(R.string.automasi)
                    binding.imageView5.setImageResource(R.drawable.channel_icon_light)
                    binding.layoutChannel2.setBackgroundResource(R.drawable.shape_on)
                    binding.channel2TextView.setText(R.string.heater)
                    binding.channel2TextView.setTextColor(Color.parseColor("#FFFFFF"))
                    binding.statusChannel2TextView.setTextColor(Color.parseColor("#FFFFFF"))
                    binding.statusChannel2TextView.setText(R.string.automasi)
                    binding.imageView6.setImageResource(R.drawable.channel_icon_light)
                } else {
                    binding.layoutAutomation.setBackgroundResource(R.drawable.shape_off)
                    binding.automationTextView.setTextColor(Color.parseColor("#000000"))
                    binding.automationStatus.setTextColor(Color.parseColor("#000000"))
                    binding.automationStatus.setText(R.string.off)
                    binding.channel1TextView.setText(R.string.channelOneTitle)
                    binding.channel2TextView.setText(R.string.channelTwoTitle)
                    binding.layoutAutomation.setOnClickListener {
                        handler.onClick8(pos, iotDataClass)
                    }
                    if (iotDataClass.channel_1 == "0") {
                        binding.layoutChannel1.setBackgroundResource(R.drawable.shape_off)
                        binding.channel1TextView.setTextColor(Color.parseColor("#000000"))
                        binding.statusChannel1TextView.setTextColor(Color.parseColor("#03DAC5"))
                        binding.statusChannel1TextView.setText(R.string.off)
                        binding.imageView5.setImageResource(R.drawable.channel_icon_dark)
                        binding.layoutChannel1.setOnClickListener {
                            handler.onClick(
                                pos,
                                iotDataClass
                            )
                        }
                    } else {
                        binding.layoutChannel1.setBackgroundResource(R.drawable.shape_on)
                        binding.channel1TextView.setTextColor(Color.parseColor("#FFFFFF"))
                        binding.statusChannel1TextView.setTextColor(Color.parseColor("#FFFFFF"))
                        binding.statusChannel1TextView.setText(R.string.on)
                        binding.imageView5.setImageResource(R.drawable.channel_icon_light)
                        binding.layoutChannel1.setOnClickListener {
                            handler.onClick2(
                                pos,
                                iotDataClass
                            )
                        }
                    }

                    if (iotDataClass.channel_2 == "0") {
                        binding.layoutChannel2.setBackgroundResource(R.drawable.shape_off)
                        binding.channel2TextView.setTextColor(Color.parseColor("#000000"))
                        binding.statusChannel2TextView.setTextColor(Color.parseColor("#03DAC5"))
                        binding.statusChannel2TextView.setText(R.string.off)
                        binding.imageView6.setImageResource(R.drawable.channel_icon_dark)
                        binding.layoutChannel2.setOnClickListener {
                            handler.onClick3(
                                pos,
                                iotDataClass
                            )
                        }
                    } else {
                        binding.layoutChannel2.setBackgroundResource(R.drawable.shape_on)
                        binding.channel2TextView.setTextColor(Color.parseColor("#FFFFFF"))
                        binding.statusChannel2TextView.setTextColor(Color.parseColor("#FFFFFF"))
                        binding.statusChannel2TextView.setText(R.string.on)
                        binding.imageView6.setImageResource(R.drawable.channel_icon_light)
                        binding.layoutChannel2.setOnClickListener {
                            handler.onClick4(
                                pos,
                                iotDataClass
                            )
                        }
                    }
                }

                binding.button.setText(R.string.reset)
                binding.button.setTextColor(Color.parseColor("#FFFFFF"));
                binding.button.setBackgroundColor(Color.parseColor("#00A8FF"))
                binding.button.setOnClickListener { handler.onClick5(pos, iotDataClass) }
            } else {
                binding.statusKoneksiTextView.setText(R.string.offline)
                binding.kelembabanTextView.text = "0"
                binding.suhuTextView.text = "0"
                binding.amoniaTextView.text = "0"
                binding.channel1TextView.setTextColor(Color.parseColor("#FFFFFF"))
                binding.statusChannel1TextView.setText(R.string.offline)
                binding.statusChannel1TextView.setTextColor(Color.parseColor("#FFFFFF"))
                binding.layoutChannel1.setBackgroundResource(R.drawable.shape_offline)
                binding.channel2TextView.setTextColor(Color.parseColor("#FFFFFF"))
                binding.statusChannel2TextView.setText(R.string.offline)
                binding.statusChannel2TextView.setTextColor(Color.parseColor("#FFFFFF"))
                binding.layoutChannel2.setBackgroundResource(R.drawable.shape_offline)
                binding.button.setText(R.string.hapus)
                binding.button.setTextColor(Color.parseColor("#FFFFFF"));
                binding.button.setBackgroundColor(Color.parseColor("#a3524e"))
                binding.button.setOnClickListener { handler.onClick6(pos, iotDataClass) }


            }


        }
    }

    interface ClickHandler {
        fun onClick(position: Int, iotDataClass: IotDataClass)
        fun onClick2(position: Int, iotDataClass: IotDataClass)
        fun onClick3(position: Int, iotDataClass: IotDataClass)
        fun onClick4(position: Int, iotDataClass: IotDataClass)
        fun onClick5(position: Int, iotDataClass: IotDataClass)
        fun onClick6(position: Int, iotDataClass: IotDataClass)
        fun onClick7(position: Int, iotDataClass: IotDataClass)
        fun onClick8(position: Int, iotDataClass: IotDataClass)
        fun onClick9(position: Int, iotDataClass: IotDataClass)
    }


    private val selectionIds = ArrayList<String>()

    fun toggleSelection(pos: Int) {
        val id = getItem(pos).id
        if (selectionIds.contains(id))
            selectionIds.remove(id)
        else
            selectionIds.add(id)
        notifyDataSetChanged()
    }

    fun getSelection(): List<String> {
        return selectionIds
    }

    fun resetSelection() {
        selectionIds.clear()
        notifyDataSetChanged()
    }


}
