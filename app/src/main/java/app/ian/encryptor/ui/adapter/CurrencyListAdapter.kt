package app.ian.encryptor.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import app.ian.encryptor.databinding.CurrencyItemBinding
import app.ian.encryptor.model.CurrencyInfo
import app.ian.encryptor.ui.listener.OnItemClickedListener
import app.ian.encryptor.ui.viewholder.CurrencyListViewHolder

class CurrencyListAdapter(private val itemClickedListener: OnItemClickedListener) :
    ListAdapter<CurrencyInfo, CurrencyListViewHolder>(CurrencyInfoDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyListViewHolder {
        return CurrencyListViewHolder(
            CurrencyItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CurrencyListViewHolder, position: Int) {
        val currencyInfo = getItem(position)
        if (currencyInfo != null) holder.bind(currencyInfo)
        holder.itemView.setOnClickListener {
            itemClickedListener.onItemClicked(currencyInfo)
        }
    }
}

private class CurrencyInfoDiffCallback : DiffUtil.ItemCallback<CurrencyInfo>() {
    override fun areItemsTheSame(oldItem: CurrencyInfo, newItem: CurrencyInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CurrencyInfo, newItem: CurrencyInfo): Boolean {
        return oldItem == newItem
    }
}