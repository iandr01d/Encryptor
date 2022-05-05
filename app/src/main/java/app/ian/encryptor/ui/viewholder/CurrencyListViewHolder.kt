package app.ian.encryptor.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import app.ian.encryptor.databinding.CurrencyItemBinding
import app.ian.encryptor.model.CurrencyInfo

class CurrencyListViewHolder(v: CurrencyItemBinding) : RecyclerView.ViewHolder(v.root) {
    private var view: CurrencyItemBinding = v
    private var item: CurrencyInfo? = null

    fun bind(item: CurrencyInfo) {
        view.apply {
            name.text = item.name
            symbol.text = item.symbol
            letter.text = item.name.substring(0, 1)
        }
        this.item = item
    }
}