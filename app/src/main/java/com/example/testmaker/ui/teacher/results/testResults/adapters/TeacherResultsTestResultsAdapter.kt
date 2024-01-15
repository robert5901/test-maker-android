package com.example.testmaker.ui.teacher.results.testResults.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testmaker.R
import com.example.testmaker.databinding.TeacherResultsTestResultsItemBinding
import com.example.testmaker.models.teacher.StudentTestResult

class TeacherResultsTestResultsAdapter: RecyclerView.Adapter<TeacherResultsTestResultsAdapter.TeacherResultsTestResultsViewHolder>() {
    val differ = AsyncListDiffer(this, DiffUtilCallback())

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
        val item = differ.currentList[position]
        val showDivider = position != differ.currentList.lastIndex
        holder.onBind(item, showDivider)
    }

    override fun getItemCount() = differ.currentList.size

    inner class TeacherResultsTestResultsViewHolder(
        private val binding: TeacherResultsTestResultsItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: StudentTestResult, showDivider: Boolean) {
            with(binding) {
                val fullName = item.studentName
                val parts = fullName.split(" ")
                val shortName = parts[0] + " " + parts[1][0] + "." + parts[2][0] + "."

                name.text = name.context.resources.getString(R.string.teacher_test_results_name, shortName, item.studentGroup.title)
                results.text = item.result
                divider.isVisible = showDivider
            }
        }
    }
}

private class DiffUtilCallback : DiffUtil.ItemCallback<StudentTestResult>() {
    override fun areItemsTheSame(oldItem: StudentTestResult, newItem: StudentTestResult): Boolean {
        return oldItem.studentName == newItem.studentName
    }

    override fun areContentsTheSame(oldItem: StudentTestResult, newItem: StudentTestResult): Boolean {
        return oldItem == newItem
    }
}