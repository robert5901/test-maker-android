package com.example.testmaker.ui.student.results.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.testmaker.databinding.StudentResultsListItemBinding
import com.example.testmaker.models.student.StudentResult
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

class StudentResultsAdapter: RecyclerView.Adapter<StudentResultsAdapter.StudentResultsViewHolder>() {
    private var items: List<StudentResult> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentResultsViewHolder {
        return StudentResultsViewHolder(
            StudentResultsListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: StudentResultsViewHolder, position: Int) {
        val item = items[position]
        val showDivider = position != items.lastIndex
        holder.onBind(item, showDivider)    }

    fun set(list: List<StudentResult>) {
        items = list

        notifyDataSetChanged()
    }

    inner class StudentResultsViewHolder(private val binding: StudentResultsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: StudentResult, showDivider: Boolean) {
            with(binding) {
                val instant = Instant.parse(item.doneTime)
                val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
                val formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy", Locale.getDefault())
                val formattedString = localDateTime.format(formatter)

                doneTime.text = formattedString
                title.text = item.name
                result.text = item.result
                divider.isVisible = showDivider
            }
        }
    }
}