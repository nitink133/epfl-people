package ch.epfl.people.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ch.epfl.people.databinding.ItemPeopleBinding
import ch.epfl.people.extension.makeVisible
import ch.epfl.people.extension.setUnderLineText
import ch.epfl.people.network.people.model.People
import coil.load


class PeoplesListAdapter(
    var items: ArrayList<People>,
    private val selectionCallback: ((position: Int) -> Unit)? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemPeopleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder !is Holder) return
        val context = viewHolder.binding.root.context
        val item = items[viewHolder.adapterPosition]
        viewHolder.binding.apply {
            tvName.text = item.name
            tvName.makeVisible(!item.name.isNullOrEmpty())

            tvEmail.setUnderLineText(item.email)
            tvEmail.makeVisible(!item.email.isNullOrEmpty())

            tvContactNumber.setUnderLineText(item.contact)
            tvContactNumber.makeVisible(!item.contact.isNullOrEmpty())

            ivImage.load(item.image)
            ivImage.makeVisible(!item.image.isNullOrEmpty())
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addAll(items: ArrayList<People>) {
        val diffResult = DiffUtil.calculateDiff(DiffCallbacks(this.items, items))
        this.items.clear()
        this.items.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class Holder(var binding: ItemPeopleBinding) :
        RecyclerView.ViewHolder(binding.root)

    class DiffCallbacks(
        oldList: List<People>,
        newList: List<People>
    ) : DiffUtil.Callback() {
        private val mOldItems: List<People> = oldList
        private val mNewItems: List<People> = newList

        override fun getOldListSize(): Int {
            return mOldItems.size
        }

        override fun getNewListSize(): Int {
            return mNewItems.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return mOldItems[oldItemPosition] == mNewItems[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem: People = mOldItems[oldItemPosition]
            val newItem: People = mNewItems[newItemPosition]
            return oldItem == newItem
        }

    }
}