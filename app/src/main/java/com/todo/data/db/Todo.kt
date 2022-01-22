package com.todo.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "todo")
data class Todo(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "time") val time: Long,
    @ColumnInfo(name = "isDaily") val isDaily: Boolean,
    @ColumnInfo(name = "migrationTestCol") val migrationTestCol: String? = ""
): Serializable