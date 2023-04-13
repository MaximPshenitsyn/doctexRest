package com.example.doctex.util

import org.springframework.http.ResponseEntity

internal fun <T> okResponse(entity: T): ResponseEntity<T> {
    return ResponseEntity.ok().body(entity)
}

internal fun <T> notFoundResponse(entity: T): ResponseEntity<T> {
    return ResponseEntity.notFound().build()
}