package com.example.doctex.controller

import com.example.doctex.dto.TexDto
import com.example.doctex.dto.compilers.ROCompilerDto
import com.example.doctex.service.Compiler
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/compile/")
class CompileController(private val service: Compiler) {

    @PostMapping("ro")
    fun compileRO(@RequestBody data: ROCompilerDto) : TexDto {
        return service.compileRO(data)
    }

}