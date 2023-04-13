package com.example.doctex.service.impl

import com.example.doctex.dto.TexDto
import com.example.doctex.dto.compilers.HeaderCompilerDto
import com.example.doctex.dto.compilers.ROCompilerDto
import com.example.doctex.service.Compiler
import org.springframework.stereotype.Service
import kotlin.io.path.Path
import kotlin.reflect.full.memberProperties

@Service
class CompilerImpl : Compiler {
    override fun compileRO(dto: ROCompilerDto): TexDto {
        var text = Path("templates", "tp.tex").toFile().readText(charset=Charsets.UTF_8)
        for (f in HeaderCompilerDto::class.memberProperties) {
            text = text.replace("[[${f.name}]]", f.getter.call(dto.headers).toString())
        }
        for (f in ROCompilerDto::class.memberProperties) {
            if (f.name == "headers") continue
            text = text.replace("[[${f.name}]]", f.getter.call(dto).toString())
        }
        return TexDto(text=text)
    }
}