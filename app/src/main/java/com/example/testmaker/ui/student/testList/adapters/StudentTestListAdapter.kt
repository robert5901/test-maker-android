package com.example.testmaker.ui.student.testList.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.testmaker.core.Action
import com.example.testmaker.databinding.StudentTestListItemBinding
import com.example.testmaker.models.student.StudentTest

class StudentTestListAdapter: RecyclerView.Adapter<StudentTestListAdapter.StudentTestListViewHolder>() {
    private var items: List<StudentTest> = emptyList()

    var onSelected: Action<StudentTest>? = null

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

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: StudentTestListViewHolder, position: Int) {
        val item = items[position]
        val showDivider = position != items.lastIndex
        holder.onBind(item, showDivider)
    }

    fun set(list: List<StudentTest>) {
        items = list

        notifyDataSetChanged()
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

        fun onBind(item: StudentTest, showDivider: Boolean) {
            test = item
            with(binding) {
                title.text = item.name
                divider.isVisible = showDivider
            }
        }
    }
}