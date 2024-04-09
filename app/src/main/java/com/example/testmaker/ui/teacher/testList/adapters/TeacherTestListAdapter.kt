package com.example.testmaker.ui.teacher.testList.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testmaker.R
import com.example.testmaker.core.Action
import com.example.testmaker.databinding.TeacherTestListItemBinding
import com.example.testmaker.models.test.Test

class TeacherTestListAdapter: RecyclerView.Adapter<TeacherTestListAdapter.TeacherTestListViewHolder>() {
    val differ = AsyncListDiffer(this, DiffUtilCallback())

    var onChangeClicked: Action<String>? = null
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
        view.onChangeClicked = { testId ->
            onChangeClicked?.invoke(testId)
        }
        view.onDeleteClicked = { test ->
            onDeleteClicked?.invoke(test)
        }
        view.onTestClicked = { test ->
            onSelected?.invoke(test)
        }

        return view
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: TeacherTestListViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.onBind(item)
    }

    inner class TeacherTestListViewHolder(private val binding: TeacherTestListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var test: Test

        var onChangeClicked: Action<String>? = null
        var onDeleteClicked: Action<Test>? = null
        var onTestClicked: Action<Test>? = null

        init {
            binding.change.setOnClickListener {
                onChangeClicked?.invoke(test.id)
            }
            binding.delete.setOnClickListener {
                onDeleteClicked?.invoke(test)
            }
            binding.root.setOnClickListener {
                onTestClicked?.invoke(test)
            }
        }

        fun onBind(item: Test) {
            test = item
            with(binding) {
                val groups = item.groups.joinToString(", ") { it.title }
                if (groups.isNotBlank()) {
                    availableGroups.text = availableGroups.context.resources.getString(
                        R.string.teacher_test_list_item_available_for_groups,
                        groups
                    )
                    availableGroups.setCompoundDrawablesWithIntrinsicBounds(R.drawable.test_available_indicator, 0, 0, 0)
                } else {
                    availableGroups.text = availableGroups.context.resources.getString(R.string.teacher_test_list_item_unavailable)
                    availableGroups.setCompoundDrawablesWithIntrinsicBounds(R.drawable.test_unavailable_indicator, 0, 0, 0)
                }

                name.text = item.name + " (4 б)"
            }
        }
    }
}

private class DiffUtilCallback : DiffUtil.ItemCallback<Test>() {
    override fun areItemsTheSame(oldItem: Test, newItem: Test): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Test, newItem: Test): Boolean {
        return oldItem == newItem
    }
}