package com.example.doctex.controller

import com.example.doctex.dto.PdfDto
import com.example.doctex.dto.TexDto
import com.example.doctex.service.Converter
import com.example.doctex.util.okResponse
import org.springframework.core.io.UrlResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.IOException
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.isRegularFile


@RestController
class TexController(private val service: Converter) {

    @GetMapping("/")
    fun index(): PdfDto {
        val result = try {
            ProcessBuilder("ipconfig")
                .redirectOutput(Path("convert", "out.txt").toFile())
                .redirectError(ProcessBuilder.Redirect.INHERIT)
                .start()
                .waitFor()
        } catch (e: IOException) {
            e.printStackTrace()
            return PdfDto("failed to start converting")
        }
        return PdfDto("http://localhost:8080/download/file/out.txt $result")
    }

    @PostMapping("upload/tex")
    fun uploadTex(@RequestBody tex: TexDto): ResponseEntity<PdfDto> {
        val path = service.convertTexToPdf(tex.text)
        return okResponse(PdfDto("http://localhost:8080/download/file/$path"))
    }

    @GetMapping("/download/file/{path}")
    fun downloadFile(@PathVariable path: String): ResponseEntity<Any> {
        val p = Path("convert", path)
        if (!p.exists() || !p.isRegularFile())
            return ResponseEntity.badRequest().body("no such file $path")

        val resource = UrlResource(p.toUri())
        return ResponseEntity.ok()
            .contentType(MediaType.TEXT_PLAIN)
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"${resource.filename}\""
            )
            .body(resource)
    }
}