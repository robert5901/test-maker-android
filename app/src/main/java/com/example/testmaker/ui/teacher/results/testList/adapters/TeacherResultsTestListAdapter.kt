package com.example.testmaker.ui.teacher.results.testList.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testmaker.core.Action
import com.example.testmaker.databinding.TeacherResultsTestListItemBinding
import com.example.testmaker.models.test.Test

class TeacherResultsTestListAdapter : RecyclerView.Adapter<TeacherResultsTestListAdapter.TeacherResultsTestListViewHolder>() {
    val differ = AsyncListDiffer(this, DiffUtilCallback())

    var onTestSelected: Action<Test>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherResultsTestListViewHolder {
        val view = TeacherResultsTestListViewHolder(
            TeacherResultsTestListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

        view.onTestSelected = {
            onTestSelected?.invoke(it)
        }

        return view
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: TeacherResultsTestListViewHolder, position: Int) {
        val item = differ.currentList[position]
        val showDivider = position != differ.currentList.lastIndex
        holder.onBind(item, showDivider)
    }

    inner class TeacherResultsTestListViewHolder(private val binding: TeacherResultsTestListItemBinding): RecyclerView.ViewHolder(binding.root) {
        private lateinit var test: Test
        var onTestSelected: Action<Test>? = null

        init {
            binding.root.setOnClickListener {
                onTestSelected?.invoke(test)
            }
        }

        fun onBind(item: Test, showDivider: Boolean) {
            test = item

            binding.title.text = item.name
            binding.divider.isVisible = showDivider
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