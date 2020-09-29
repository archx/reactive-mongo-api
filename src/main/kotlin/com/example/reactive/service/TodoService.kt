package com.example.reactive.service

import com.example.reactive.entity.Todo
import com.example.reactive.repository.TodoRepository
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * TodoService
 *
 * @author archx
 * @since 2020/9/29 10:39
 */
interface TodoService {

    fun create(title: String): Mono<Todo>

    fun delete(id: String): Mono<Void>

    fun update(id: String, title: String): Mono<Long>

    fun get(id: String): Mono<Todo>

    fun findAll(): Flux<Todo>
}

@Service
class TodoServiceImpl(val todoRepository: TodoRepository, val mongoTemplate: ReactiveMongoTemplate) : TodoService {

    override fun create(title: String) = todoRepository.save(Todo(title))

    override fun delete(id: String) = todoRepository.deleteById(id)

    override fun update(id: String, title: String): Mono<Long> {
        val query = Query.query(Criteria.where("_id").isEqualTo(id))
        val update = Update.update("title", title)
        return mongoTemplate.updateFirst(query, update, Todo::class.java)
            .map { it.modifiedCount }
    }

    override fun get(id: String) = todoRepository.findById(id)

    override fun findAll(): Flux<Todo> = todoRepository.findAll()
}