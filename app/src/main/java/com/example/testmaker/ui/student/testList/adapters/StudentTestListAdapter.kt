package com.example.testmaker.ui.student.testList.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testmaker.R
import com.example.testmaker.core.Action
import com.example.testmaker.databinding.StudentTestListItemBinding
import com.example.testmaker.models.student.StudentTest

class StudentTestListAdapter: RecyclerView.Adapter<StudentTestListAdapter.StudentTestListViewHolder>() {
    val differ = AsyncListDiffer(this, DiffUtilCallback())

    var onSelected: Action<StudentTest>? = null
    // TODO test data
    val list = listOf("11:00 01.02.2024", "13:30 02.02.2024")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentTestListViewHolder {
        val view = StudentTestListViewHolder(
            StudentTestListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        view.onTestClicked = { test ->
            onSelected?.invoke(test)
        }

        return view
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: StudentTestListViewHolder, position: Int) {
        val item = differ.currentList[position]
        val date = list[position]
        holder.onBind(item, date)
    }

    inner class StudentTestListViewHolder(private val binding: StudentTestListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var test: StudentTest

        var onTestClicked: Action<StudentTest>? = null

        init {
            binding.root.setOnClickListener {
                onTestClicked?.invoke(test)
            }
        }

        fun onBind(item: StudentTest, date: String) {
            test = item
            with(binding) {
                title.text = item.name
                available.text = binding.root.resources.getString(R.string.student_test_list_item_available, date)
            }
        }
    }
}

private class DiffUtilCallback : DiffUtil.ItemCallback<StudentTest>() {
    override fun areItemsTheSame(oldItem: StudentTest, newItem: StudentTest): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: StudentTest, newItem: StudentTest): Boolean {
        return oldItem == newItem
    }
}