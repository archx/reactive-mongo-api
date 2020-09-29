package com.example.reactive.dto

/**
 * ResultOutput
 *
 * @author archx
 * @since 2020/9/29 10:29
 */
data class ResultOutput<T>(val code: Int, val message: String, val result: T?)