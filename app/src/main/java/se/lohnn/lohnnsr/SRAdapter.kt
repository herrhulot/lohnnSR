package se.lohnn.lohnnsr

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import se.lohnn.lohnnsr.data.Program
import se.lohnn.lohnnsr.databinding.ProgramItemBinding


class SRAdapter : RecyclerView.Adapter<SRAdapter.ViewHolder>() {
    private var items = listOf<Program>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding: ProgramItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.program_item, parent, false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.setVariable(BR.data, items[position])
        holder.binding.executePendingBindings()
    }

    fun updateItems(newList: List<Program>) {
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                    items[oldItemPosition].name == newList[newItemPosition].name

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                    items[oldItemPosition] == newList[newItemPosition]

            override fun getOldListSize() = items.size
            override fun getNewListSize() = newList.size
        }).also {
            items = newList
            it.dispatchUpdatesTo(this)
        }
    }

    class ViewHolder(val binding: ProgramItemBinding) : RecyclerView.ViewHolder(binding.root)
}