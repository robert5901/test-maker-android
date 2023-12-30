package com.example.testmaker.ui.teacher.results.testResults.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.testmaker.databinding.TeacherResultsTestResultsItemBinding
import com.example.testmaker.models.teacher.StudentTestResult

class TeacherResultsTestResultsAdapter: RecyclerView.Adapter<TeacherResultsTestResultsAdapter.TeacherResultsTestResultsViewHolder>() {
    private var items: List<StudentTestResult> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherResultsTestResultsAdapter.TeacherResultsTestResultsViewHolder {
        return TeacherResultsTestResultsViewHolder(
            TeacherResultsTestResultsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TeacherResultsTestResultsAdapter.TeacherResultsTestResultsViewHolder, position: Int) {
        val item = items[position]
        val showDivider = position != items.lastIndex
        holder.onBind(item, showDivider)
    }

    override fun getItemCount() = items.size

    fun set(list: List<StudentTestResult>) {
        items = list

        notifyDataSetChanged()
    }

    inner class TeacherResultsTestResultsViewHolder(
        private val binding: TeacherResultsTestResultsItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: StudentTestResult, showDivider: Boolean) {
            with(binding) {
                name.text = item.studentName
                results.text = item.result
                divider.isVisible = showDivider
            }
        }
    }
}