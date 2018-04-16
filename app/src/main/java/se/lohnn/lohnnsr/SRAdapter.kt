package se.lohnn.lohnnsr

import android.databinding.DataBindingUtil
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
        items = newList
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ProgramItemBinding) : RecyclerView.ViewHolder(binding.root)
}