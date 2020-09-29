package com.example.reactive.entity

import com.example.reactive.util.Utils
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Todo
 *
 * @author archx
 * @since 2020/9/29 10:31
 */
@Document("todo_list")
data class Todo(val id: String, val title: String, val completed: Boolean, val createdAt: Long, val updatedAt: Long) {
    constructor(title: String) : this("", title, false, Utils.timestamp(), 0L)
}