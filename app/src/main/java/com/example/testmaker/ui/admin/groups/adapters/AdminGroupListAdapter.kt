package com.example.testmaker.ui.admin.groups.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testmaker.core.Action
import com.example.testmaker.databinding.ListItemAdminGroupListBinding
import com.example.testmaker.models.student.Group

class AdminGroupListAdapter: RecyclerView.Adapter<AdminGroupListAdapter.AdminGroupListViewHolder>() {
    val differ = AsyncListDiffer(this, DiffUtilCallback())

    var onDeleteClicked: Action<Group>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminGroupListViewHolder {
        val view = AdminGroupListViewHolder(
            ListItemAdminGroupListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        view.onDeleteClicked = { group ->
            onDeleteClicked?.invoke(group)
        }
        return view
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: AdminGroupListViewHolder, position: Int) {
        val item = differ.currentList[position]
        val showDivider = position != differ.currentList.lastIndex
        holder.onBind(item, showDivider)
    }

    inner class AdminGroupListViewHolder(private val binding: ListItemAdminGroupListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var group: Group

        var onDeleteClicked: Action<Group>? = null

        init {
            binding.delete.setOnClickListener {
                onDeleteClicked?.invoke(group)
            }
        }

        fun onBind(item: Group, showDivider: Boolean) {
            group = item
            with(binding) {
                title.text = item.title
                divider.isVisible = showDivider
            }
        }
    }
}

private class DiffUtilCallback : DiffUtil.ItemCallback<Group>() {
    override fun areItemsTheSame(oldItem: Group, newItem: Group): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Group, newItem: Group): Boolean {
        return oldItem == newItem
    }
}