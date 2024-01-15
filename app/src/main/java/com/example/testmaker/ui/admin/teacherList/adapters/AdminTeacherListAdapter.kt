package com.example.testmaker.ui.admin.teacherList.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testmaker.core.Action
import com.example.testmaker.databinding.ListItemAdminTeacherListBinding
import com.example.testmaker.models.users.Teacher

class AdminTeacherListAdapter: RecyclerView.Adapter<AdminTeacherListAdapter.AdminTeacherListViewHolder>() {
    val differ = AsyncListDiffer(this, DiffUtilCallback())

    var onChangeClicked: Action<Teacher>? = null
    var onDeleteClicked: Action<Teacher>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminTeacherListViewHolder {
        val view = AdminTeacherListViewHolder(
            ListItemAdminTeacherListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        view.onChangeClicked = { teacher ->
            onChangeClicked?.invoke(teacher)
        }
        view.onDeleteClicked = { teacher ->
            onDeleteClicked?.invoke(teacher)
        }
        return view
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: AdminTeacherListViewHolder, position: Int) {
        val item = differ.currentList[position]
        val showDivider = position != differ.currentList.lastIndex
        holder.onBind(item, showDivider)
    }

    inner class AdminTeacherListViewHolder(private val binding: ListItemAdminTeacherListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var teacher: Teacher

        var onChangeClicked: Action<Teacher>? = null
        var onDeleteClicked: Action<Teacher>? = null

        init {
            binding.change.setOnClickListener {
                onChangeClicked?.invoke(teacher)
            }
            binding.delete.setOnClickListener {
                onDeleteClicked?.invoke(teacher)
            }
        }

        fun onBind(item: Teacher, showDivider: Boolean) {
            teacher = item
            with(binding) {
                name.text = item.name
                divider.isVisible = showDivider
            }
        }
    }
}

private class DiffUtilCallback : DiffUtil.ItemCallback<Teacher>() {
    override fun areItemsTheSame(oldItem: Teacher, newItem: Teacher): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Teacher, newItem: Teacher): Boolean {
        return oldItem == newItem
    }
}