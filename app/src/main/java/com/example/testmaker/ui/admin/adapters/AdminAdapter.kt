package com.example.testmaker.ui.admin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.testmaker.databinding.ListItemAdminBinding
import com.example.testmaker.models.admin.Teacher

class AdminAdapter: RecyclerView.Adapter<AdminAdapter.AdminViewHolder>() {
    private var items: List<Teacher> = emptyList()
    var onChangeClicked: ((teacher: Teacher) -> Unit)? = null
    var onDeleteClicked: ((teacher: Teacher) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminViewHolder {
        val view = AdminViewHolder(
            ListItemAdminBinding.inflate(
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

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: AdminViewHolder, position: Int) {
        val item = items[position]
        val showDivider = position != items.lastIndex
        holder.onBind(item, showDivider)
    }

    fun set(list: List<Teacher>) {
        items = list

        notifyDataSetChanged()
    }

    inner class AdminViewHolder(private val binding: ListItemAdminBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var teacher: Teacher

        var onChangeClicked: ((teacher: Teacher) -> Unit)? = null
        var onDeleteClicked: ((teacher: Teacher) -> Unit)? = null

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