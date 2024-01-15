package com.example.testmaker.ui.teacher.testQuestionList.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.testmaker.core.Action
import com.example.testmaker.databinding.TeacherTestQuestionListItemBinding
import com.example.testmaker.models.teacher.TeacherTestQuestion

class TeacherTestQuestionListAdapter: RecyclerView.Adapter<TeacherTestQuestionListAdapter.TeacherTestQuestionListViewHolder>() {
    private var items: List<TeacherTestQuestion> = emptyList()

    var onChangeClicked: Action<TeacherTestQuestion>? = null
    var onDeleteClicked: Action<TeacherTestQuestion>? = null
    var onAddImageClicked: Action<TeacherTestQuestion>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherTestQuestionListAdapter.TeacherTestQuestionListViewHolder {
        val view = TeacherTestQuestionListViewHolder(
            TeacherTestQuestionListItemBinding.inflate(
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
        view.onAddImageClicked = { test ->
            onAddImageClicked?.invoke(test)
        }

        return view
    }

    override fun onBindViewHolder(holder: TeacherTestQuestionListAdapter.TeacherTestQuestionListViewHolder, position: Int) {
        val item = items[position]
        val showDivider = position != items.lastIndex
        holder.onBind(item, showDivider)
    }

    override fun getItemCount() = items.size

    fun set(list: List<TeacherTestQuestion>) {
        items = list

        notifyDataSetChanged()
    }

    inner class TeacherTestQuestionListViewHolder(val binding: TeacherTestQuestionListItemBinding):
    RecyclerView.ViewHolder(binding.root){
        private lateinit var question: TeacherTestQuestion

        var onChangeClicked: Action<TeacherTestQuestion>? = null
        var onDeleteClicked: Action<TeacherTestQuestion>? = null
        var onAddImageClicked: Action<TeacherTestQuestion>? = null

        init {
            binding.root.setOnClickListener {
                onChangeClicked?.invoke(question)
            }
            binding.delete.setOnClickListener {
                onDeleteClicked?.invoke(question)
            }
            binding.addImage.setOnClickListener {
                onAddImageClicked?.invoke(question)
            }
        }

        fun onBind(item: TeacherTestQuestion, showDivider: Boolean) {
            question = item

            with(binding) {
                question.text = item.question
                divider.isVisible = showDivider
            }
        }
    }
}