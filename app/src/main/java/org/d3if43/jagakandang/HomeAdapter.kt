package org.d3if43.jagakandang

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.d3if43.jagakandang.data.HomeDataClass
import org.d3if43.jagakandang.databinding.ListHomeBinding
import java.util.ArrayList

class HomeAdapter (private val handler: ClickHandler): ListAdapter<HomeDataClass, HomeAdapter.ViewHolder>(DIFF_CALLBACK){
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HomeDataClass>() {
            override fun areItemsTheSame(
                oldData: HomeDataClass, newData: HomeDataClass
            ): Boolean {
                return oldData.id == newData.id
            }
            override fun areContentsTheSame(
                oldData: HomeDataClass, newData: HomeDataClass
            ): Boolean {
                return oldData == newData
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListHomeBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    inner class ViewHolder(
        private val binding: ListHomeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(homeDataClass: HomeDataClass) {
            binding.namaKandangList.text = homeDataClass.namaKandang
            binding.lokasiList.text = homeDataClass.lokasi
            binding.chickinList.text = homeDataClass.tglChickIn
            val pos = absoluteAdapterPosition
            itemView.isSelected = selectionIds.contains(homeDataClass.id)
            itemView.setOnClickListener { handler.onClick(pos, homeDataClass) }
            itemView.setOnLongClickListener{ handler.onLongClick(pos, homeDataClass)}

        }
    }
    interface ClickHandler {
        fun onClick(position: Int, homeDataClass: HomeDataClass)
        fun onLongClick(position: Int, homeDataClass: HomeDataClass) : Boolean
    }

    private val selectionIds = ArrayList<Int>()

    fun toggleSelection(pos: Int) {
        val id = getItem(pos).id
        if (selectionIds.contains(id))
            selectionIds.remove(id)
        else
            selectionIds.add(id)
        notifyDataSetChanged()
    }
    fun getSelection(): List<Int> {
        return selectionIds
    }
    fun resetSelection() {
        selectionIds.clear()
        notifyDataSetChanged()
    }
}