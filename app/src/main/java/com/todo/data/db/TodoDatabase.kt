package com.todo.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Todo::class], version = 2, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao

    companion object {
        private var INSTANCE: TodoDatabase? = null

        fun getInstance(context: Context): TodoDatabase? {
            if (INSTANCE == null) {
                synchronized(TodoDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context,
                        TodoDatabase::class.java,
                        "todo_db")
                        .addMigrations(MIGRATION_1_2)
                        .build()
                }
            }
            return INSTANCE
        }
    }
}