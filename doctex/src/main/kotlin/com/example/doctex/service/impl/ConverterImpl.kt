package com.example.doctex.service.impl

import com.example.doctex.service.Converter
import org.springframework.stereotype.Service
import java.io.File
import java.io.IOException
import kotlin.io.path.Path
import kotlin.io.path.deleteExisting
import kotlin.io.path.deleteIfExists

@Service
class ConverterImpl : Converter {

    private fun generateFile(tex: String) : Int {
        val r = (0..Int.MAX_VALUE).random()
        val f = Path("convert", "$r.tex").toFile()
        f.writeText(tex)
        return r
    }

    private fun clearFiles(r: Int) {
        for (f in Path("convert").toFile().listFiles()!!) {
            if (f.isFile && !f.name.endsWith("pdf")) {
                f.delete()
            }
        }
    }

    override fun convertTexToPdf(tex: String): String {
        val source = generateFile(tex)
        val r = try {
            ProcessBuilder("pdflatex", "-output-directory=convert", "convert/$source.tex")
                .redirectOutput(Path("convert", "logs", "$source.log").toFile())
                .redirectError(ProcessBuilder.Redirect.INHERIT)
                .start()
                .waitFor()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            clearFiles(source)
        }
        return "${source}.pdf"
    }

    override fun convertTexToDoc(tex: String): String {
        TODO("Not yet implemented")
    }
}