package com.todo.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.todo.R
import com.todo.data.TodoRepository
import com.todo.data.db.Todo
import com.todo.ui.createTodo.CreateTodoFormState
import java.util.*

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    private val _repository: TodoRepository = TodoRepository(application)

    private val _allTodoList: LiveData<List<Todo>> = _repository.getAllTodoList()

    private val _createForm = MutableLiveData<CreateTodoFormState>()
    val createForm: LiveData<CreateTodoFormState> = _createForm

    var selectedTime = MutableLiveData<Calendar?>()

    private fun saveTodo(todo: Todo) {
        _repository.saveTodo(todo)
    }

    fun setSelectedTime(year: Int, month: Int, dayOfMonth: Int, hourOfDay: Int, minute: Int) {
        val time = Calendar.getInstance().apply {
            this.set(year, month, dayOfMonth, hourOfDay, minute)
        }
        val currentTime = Calendar.getInstance()
        if (currentTime.time.after(time.time)) {
            _createForm.value = CreateTodoFormState(timeError = R.string.time_before_current_error)
            selectedTime.value = null
        } else {
            _createForm.value = CreateTodoFormState(timeError = null)
            selectedTime.value = time
        }
    }

    fun validateTodoForm(title: String, description: String): Boolean {
        when {
            title.isBlank() -> {
                _createForm.value =
                    CreateTodoFormState(titleError = R.string.title_empty_error)
                return false
            }
            description.isBlank() -> {
                _createForm.value =
                    CreateTodoFormState(descriptionError = R.string.description_empty_error)
                return false
            }
            selectedTime.value == null -> {
                _createForm.value =
                    CreateTodoFormState(timeError = R.string.time_not_selected_error)
                return false
            }
            else -> {
                _createForm.value =
                    CreateTodoFormState()
                return true
            }
        }
    }

    fun updateTodo(todo: Todo) {
        _repository.updateTodo(todo)
    }

    fun deleteTodo(todo: Todo) {
        _repository.deleteTodo(todo)
    }

    fun getAllTodoList(): LiveData<List<Todo>> {
        return _allTodoList
    }

    fun createTodo(title: String, description: String, isDaily: Boolean, updateTodoId: Long?) : Boolean{
        if (!validateTodoForm(title, description)){
            return false
        }
        val todo = Todo(
            updateTodoId,
            title,
            description,
            selectedTime.value?.timeInMillis ?: -1,
            isDaily
        )
        if (updateTodoId != null) {
            updateTodo(todo)
        } else {
            saveTodo(todo)
        }
        return true
    }

    fun resetForm() {
        selectedTime.value = null
        _createForm.value = CreateTodoFormState()
    }
}
