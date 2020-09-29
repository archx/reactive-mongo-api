package com.example.reactive.controller

import com.example.reactive.dto.ResultOutput
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * IndexController
 *
 * @author archx
 * @since 2020/9/29 10:28
 */
@RestController
class IndexController {

    @GetMapping
    fun index() = ResultOutput<Void>(0, "ok", null)

}