package com.todo.ui.home

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.todo.R
import com.todo.data.db.Todo
import com.todo.databinding.FragmentHomeBinding
import com.todo.databinding.FragmentLoginBinding
import com.todo.ui.createTodo.CreateUpdateFragment.Companion.BUNDLE_KEY
import com.todo.viewModels.TodoViewModel

class HomeFragment : Fragment() {

    private val _viewModel: TodoViewModel by activityViewModels()
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val todoAdapter = TodoAdapter(object : TodoAdapter.OnTodoListItemClickListener {
            override fun onEditClick(todo: Todo) {
                goToEditView(todo)
            }

            override fun onItemClick(todo: Todo) {
                _viewModel.deleteTodo(todo)
            }

        })
        binding.todoList.adapter = todoAdapter
        binding.todoList.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )
        _viewModel.getAllTodoList().observe(viewLifecycleOwner, {
            todoAdapter.submitList(it)
        })
        setHasOptionsMenu(true)
    }

    private fun goToEditView(todo: Todo) {
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_homeFragment_to_createUpdateFragment, Bundle().apply {
                this.putSerializable(BUNDLE_KEY, todo)
            })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_home, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_todo -> addTodo()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addTodo() {
        // Navigating to Create Todo screen
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_homeFragment_to_createUpdateFragment)
    }
}