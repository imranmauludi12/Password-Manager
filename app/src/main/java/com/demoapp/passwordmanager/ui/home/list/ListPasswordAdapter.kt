package com.demoapp.passwordmanager.ui.home.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.demoapp.passwordmanager.core.data.password.PasswordEntity
import com.demoapp.passwordmanager.databinding.CardListAppsBinding

class ListPasswordAdapter(private val dataSet: List<PasswordEntity>, private val onClickListener: (PasswordEntity) -> Unit):
    RecyclerView.Adapter<ListPasswordAdapter.ViewHolder>() {



    inner class ViewHolder(private val binding: CardListAppsBinding): RecyclerView.ViewHolder(binding.root) {
        lateinit var getCredential: PasswordEntity
        var getIndexPosition: Int = -1

        fun bind(data: PasswordEntity, indexPosition: Int) {
            getIndexPosition = indexPosition
            getCredential = data
            binding.appName.text = data.appName
            binding.subDate.text = data.date
            itemView.setOnClickListener { onClickListener(data) }
        }

        fun insert(data: PasswordEntity, index: Int) {
            getIndexPosition = index
            getCredential = data
        }

        val appNameText = binding.appName
        val dateText = binding.subDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // this method use if view holder accept view type constructor
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_list_apps, parent, false)
        val dataBinding = CardListAppsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (dataSet.isNullOrEmpty()) return
//        holder.bind(dataSet[position], position)
        val item = dataSet.get(position)
        holder.appNameText.text = item.appName
        holder.dateText.text = item.date
        holder.insert(item, position)

//        holder.itemView.setOnClickListener {
//            onClickListener(dataSet[position])
//        }
    }

    override fun getItemCount(): Int = dataSet.size

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PasswordEntity>() {
            override fun areItemsTheSame(
                oldItem: PasswordEntity,
                newItem: PasswordEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: PasswordEntity,
                newItem: PasswordEntity
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}