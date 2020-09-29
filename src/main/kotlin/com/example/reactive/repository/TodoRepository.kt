package com.example.reactive.repository

import com.example.reactive.entity.Todo
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

/**
 * TodoRepository
 *
 * @author archx
 * @since 2020/9/29 10:37
 */
interface TodoRepository : ReactiveMongoRepository<Todo, String>