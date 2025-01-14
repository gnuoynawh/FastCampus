package com.gnuoynawh.part5.todo.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.gnuoynawh.part5.todo.R
import com.gnuoynawh.part5.todo.data.entity.TodoEntity
import com.gnuoynawh.part5.todo.databinding.ViewholderTodoItemBinding

class ToDoAdapter : RecyclerView.Adapter<ToDoAdapter.ToDoItemViewHolder>() {

    private var toDoList: List<TodoEntity> = listOf()
    private lateinit var toDoItemClickListener: (TodoEntity) -> Unit
    private lateinit var toDoCheckListener: (TodoEntity) -> Unit

    inner class ToDoItemViewHolder(
        private val binding: ViewholderTodoItemBinding,
        val toDoItemClickListener: (TodoEntity) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: TodoEntity) = with(binding) {
            checkBox.text = data.title
            checkToDoComplete(data.hasCompleted)
        }

        fun bindViews(data: TodoEntity) {
            binding.checkBox.setOnClickListener {
                toDoCheckListener(
                    data.copy(hasCompleted = binding.checkBox.isChecked)
                )
                checkToDoComplete(binding.checkBox.isChecked)
            }
            binding.root.setOnClickListener {
                toDoItemClickListener(data)
            }
        }

        private fun checkToDoComplete(isChecked: Boolean) = with(binding) {
            checkBox.isChecked = isChecked
            container.setBackgroundColor(
                ContextCompat.getColor(
                    root.context,
                    if (isChecked) {
                        R.color.gray_300
                    } else {
                        R.color.white
                    }
                )
            )
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoItemViewHolder {
        val view = ViewholderTodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDoItemViewHolder(view, toDoItemClickListener)
    }

    override fun onBindViewHolder(holder: ToDoItemViewHolder, position: Int) {
        holder.bindData(toDoList[position])
        holder.bindViews(toDoList[position])
    }

    override fun getItemCount(): Int = toDoList.size

    fun setToDoList(toDoList: List<TodoEntity>, toDoItemClickListener: (TodoEntity) -> Unit, toDoCheckListener: (TodoEntity) -> Unit) {
        this.toDoList = toDoList
        this.toDoItemClickListener = toDoItemClickListener
        this.toDoCheckListener = toDoCheckListener
        notifyDataSetChanged()
    }
}