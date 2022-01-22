package com.todo.ui.createTodo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.todo.R
import com.todo.data.db.Todo
import com.todo.databinding.FragmentCreateUpdateBinding
import com.todo.utils.DATE_FORMAT
import com.todo.viewModels.TodoViewModel
import java.util.*

class CreateUpdateFragment : Fragment() {

    companion object {
        const val BUNDLE_KEY = "todo"
    }

    private var _isUpdate: Long? = null
    private val _viewModel: TodoViewModel by activityViewModels()
    private var _binding: FragmentCreateUpdateBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCreateUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = binding.title
        val description = binding.description
        val selectTime = binding.dateTimeSelect
        val selectType = binding.typeSelect
        val create = binding.create

        binding.create.setOnClickListener {
            val created = _viewModel.createTodo(
                title.text.toString(),
                description.text.toString(),
                selectType.checkedRadioButtonId == R.id.daily,
                _isUpdate
            )
            if(created){
                onTodoCreated()
            }
        }

        // checking if there's any todo to update
        if (arguments != null) {
            val todo = (arguments?.getSerializable(BUNDLE_KEY) as Todo)
            title.setText(todo.title)
            description.setText(todo.description)
            _viewModel.selectedTime.postValue(Calendar.getInstance().also {
                it.time = Date(todo.time)
            })
            if (todo.isDaily) {
                selectType.check(R.id.daily)
            } else {
                selectType.check(R.id.weekly)
            }
            create.text = getString(R.string.update)
            _isUpdate = todo.id
        } else {
            _isUpdate = null
            create.text = getString(R.string.create)
            _viewModel.resetForm()
        }

        _viewModel.createForm.observe(viewLifecycleOwner, {
            if (it == null) {
                return@observe
            }
            it.descriptionError?.let {
                description.error = getString(it)
            }
            it.titleError?.let {
                title.error = getString(it)
            }
            if (it.timeError != null) {
                Toast.makeText(requireContext(),it.timeError!!, Toast.LENGTH_LONG).show()
            }
        })

        _viewModel.selectedTime.observe(viewLifecycleOwner, {
            if (it != null) {
                selectTime.text = DATE_FORMAT.format(it.time)
            } else {
                selectTime.text = getString(R.string.select_time)
            }
        })

        selectTime.setOnClickListener {
            showDatePicker()
        }
        description.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val created = _viewModel.createTodo(
                    title.text.toString(),
                    description.text.toString(),
                    selectType.checkedRadioButtonId == R.id.daily,
                    _isUpdate
                )
                if(created){
                    onTodoCreated()
                }
            }
            false
        }
    }

    private fun showDatePicker() {
        val currentTime = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                showTimePicker(year, month, dayOfMonth)
            },
            currentTime.get(Calendar.YEAR),
            currentTime.get(Calendar.MONTH),
            currentTime.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun showTimePicker(year: Int, month: Int, dayOfMonth: Int) {
        val currentTime = Calendar.getInstance()
        TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                _viewModel.setSelectedTime(year, month, dayOfMonth, hourOfDay, minute)
            },
            currentTime.get(Calendar.HOUR_OF_DAY),
            currentTime.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun onTodoCreated() {
        Navigation.findNavController(binding.root).popBackStack()
    }
}