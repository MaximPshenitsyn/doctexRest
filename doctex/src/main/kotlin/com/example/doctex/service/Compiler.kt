package com.example.doctex.service

import com.example.doctex.dto.TexDto
import com.example.doctex.dto.compilers.ROCompilerDto

interface Compiler {
    fun compileRO(dto: ROCompilerDto) : TexDto
}