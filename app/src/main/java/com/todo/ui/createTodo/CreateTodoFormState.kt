package com.todo.ui.createTodo

data class CreateTodoFormState(
    var titleError : Int? = null, var descriptionError : Int? = null, var timeError : Int? = null
)