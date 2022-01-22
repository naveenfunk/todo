package com.todo.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.todo.data.db.Todo
import com.todo.databinding.ItemTodoBinding

class TodoAdapter(val onTodoListItemClickListener: OnTodoListItemClickListener) :
    ListAdapter<Todo, TodoAdapter.TodoViewHolder>(TodoDiffCallback()) {

    class TodoViewHolder(
        private val binding: ItemTodoBinding,
        val onTodoListItemClickListener: OnTodoListItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(todo: Todo) {
            binding.todo = todo
            binding.edit.setOnClickListener {
                onTodoListItemClickListener.onEditClick(todo)
            }
            binding.root.setOnClickListener {
                onTodoListItemClickListener.onItemClick(todo)
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder =
        TodoViewHolder(
            ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onTodoListItemClickListener
        )

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class TodoDiffCallback : DiffUtil.ItemCallback<Todo>() {

        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }
    }

    interface OnTodoListItemClickListener {
        fun onEditClick(todo: Todo)
        fun onItemClick(todo: Todo)
    }
}
