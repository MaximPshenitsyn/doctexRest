package com.example.doctex.service

import java.io.File


interface Converter {
    fun convertTexToPdf(tex: String) : String
    fun convertTexToDoc(tex: String) : String
}