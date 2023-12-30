package com.example.testmaker.ui.teacher.testList.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.testmaker.core.Action
import com.example.testmaker.databinding.TeacherTestListItemBinding
import com.example.testmaker.models.test.Test

class TeacherTestListAdapter: RecyclerView.Adapter<TeacherTestListAdapter.TeacherTestListViewHolder>() {
    private var items: List<Test> = emptyList()

    var onChangeClicked: Action<Test>? = null
    var onDeleteClicked: Action<Test>? = null
    var onSelected: Action<Test>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherTestListViewHolder {
        val view = TeacherTestListViewHolder(
            TeacherTestListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        view.onChangeClicked = { test ->
            onChangeClicked?.invoke(test)
        }
        view.onDeleteClicked = { test ->
            onDeleteClicked?.invoke(test)
        }
        view.onTestClicked = { test ->
            onSelected?.invoke(test)
        }

        return view
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: TeacherTestListViewHolder, position: Int) {
        val item = items[position]
        val showDivider = position != items.lastIndex
        holder.onBind(item, showDivider)
    }

    fun set(list: List<Test>) {
        items = list

        notifyDataSetChanged()
    }

    inner class TeacherTestListViewHolder(private val binding: TeacherTestListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var test: Test

        var onChangeClicked: Action<Test>? = null
        var onDeleteClicked: Action<Test>? = null
        var onTestClicked: Action<Test>? = null

        init {
            binding.change.setOnClickListener {
                onChangeClicked?.invoke(test)
            }
            binding.delete.setOnClickListener {
                onDeleteClicked?.invoke(test)
            }
            binding.root.setOnClickListener {
                onTestClicked?.invoke(test)
            }
        }

        fun onBind(item: Test, showDivider: Boolean) {
            test = item
            with(binding) {
                divider.isVisible = showDivider
                name.text = item.name
                availableGroups.text = item.groups.joinToString(", ") { it.title }
            }
        }
    }
}