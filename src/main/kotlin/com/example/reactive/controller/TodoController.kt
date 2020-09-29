package com.example.reactive.controller

import com.example.reactive.dto.ResultOutput
import com.example.reactive.entity.Todo
import com.example.reactive.service.TodoService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

/**
 * TodoController
 *
 * @author archx
 * @since 2020/9/29 10:55
 */
@RestController
@RequestMapping("/todo")
@Api(tags = ["Todo APIs"])
class TodoController(val todoService: TodoService) {

    @ApiOperation("create", notes = "add todo")
    @ApiImplicitParam("Title", name = "title", paramType = "form", dataTypeClass = String::class)
    @PostMapping("/create")
    fun create(exchange: ServerWebExchange): Mono<ResultOutput<Todo>> {
        return exchange.formData
            .map { it.getFirst("title") ?: throw IllegalArgumentException("invalid title") }
            .flatMap { todoService.create(it) }
            .map { ResultOutput(0, "ok", it) }
            .onErrorResume { ex -> Mono.just(ResultOutput<Todo>(400, ex.message ?: "bad request", null)) }
    }

    @ApiOperation("delete", notes = "delete todo")
    @ApiImplicitParam("ID", name = "id", type = "path")
    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: String): Mono<ResultOutput<Void>> {
        return todoService.delete(id).map { ResultOutput<Void>(0, "ok", null) }
    }

    @ApiOperation("update", notes = "update title")
    @ApiImplicitParams(value = [
        ApiImplicitParam("ID", name = "id", paramType = "path", dataTypeClass = String::class),
        ApiImplicitParam("Title", name = "title", paramType = "form", dataTypeClass = String::class)
    ])
    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: String, exchange: ServerWebExchange): Mono<ResultOutput<Long>> {
        return exchange.formData.map { it.getFirst("title") ?: throw IllegalArgumentException("invalid title") }
            .flatMap { todoService.update(id, it) }
            .map { ResultOutput(0, "ok", it) }
            .onErrorResume { ex -> Mono.just(ResultOutput(400, ex.message ?: "bad request", -1L)) }
    }

    @ApiOperation("get", notes = "find by id")
    @ApiImplicitParam("ID", name = "id", type = "path")
    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: String): Mono<ResultOutput<Todo>> {
        return todoService.get(id).map { ResultOutput(0, "ok", it) }
    }

    @ApiOperation("findAll", notes = "return all of todo")
    @GetMapping("/findAll")
    fun findAll() = todoService.findAll().collectList().map { ResultOutput(0, "ok", it) }

}