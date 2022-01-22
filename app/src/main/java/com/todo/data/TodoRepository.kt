package com.todo.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.todo.data.db.Todo
import com.todo.data.db.TodoDao
import com.todo.data.db.TodoDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TodoRepository(application: Application) {

    private val todoDao: TodoDao
    private val allTodos: LiveData<List<Todo>>

    init {
        val database = TodoDatabase.getInstance(application.applicationContext)
        todoDao = database!!.todoDao()
        allTodos = todoDao.getAllTodoList()
    }

    fun saveTodo(todo: Todo) = runBlocking {
        this.launch(Dispatchers.IO) {
            todoDao.saveTodo(todo)
        }
    }

    fun updateTodo(todo: Todo) = runBlocking {
        this.launch(Dispatchers.IO) {
            todoDao.updateTodo(todo)
        }
    }


    fun deleteTodo(todo: Todo) {
        runBlocking {
            this.launch(Dispatchers.IO) {
                todoDao.deleteTodo(todo)
            }
        }
    }

    fun getAllTodoList(): LiveData<List<Todo>> {
        return allTodos
    }
}