package com.example.testmaker.ui.teacher.results.testList.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.testmaker.core.Action
import com.example.testmaker.databinding.TeacherResultsTestListItemBinding
import com.example.testmaker.models.test.Test

class TeacherResultsAdapter : RecyclerView.Adapter<TeacherResultsAdapter.TeacherResultsViewHolder>() {
    private var items: List<Test> = emptyList()
    var onTestSelected: Action<String>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherResultsViewHolder {
        val view = TeacherResultsViewHolder(
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

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: TeacherResultsViewHolder, position: Int) {
        val item = items[position]
        val showDivider = position != items.lastIndex
        holder.onBind(item, showDivider)
    }

    fun set(list: List<Test>) {
        items = list

        notifyDataSetChanged()
    }

    inner class TeacherResultsViewHolder(private val binding: TeacherResultsTestListItemBinding): RecyclerView.ViewHolder(binding.root) {
        private lateinit var testId: String
        var onTestSelected: Action<String>? = null

        init {
            binding.root.setOnClickListener {
                onTestSelected?.invoke(testId)
            }
        }

        fun onBind(item: Test, showDivider: Boolean) {
            testId = item.id

            binding.title.text = item.name
            binding.divider.isVisible = showDivider
        }
    }
}