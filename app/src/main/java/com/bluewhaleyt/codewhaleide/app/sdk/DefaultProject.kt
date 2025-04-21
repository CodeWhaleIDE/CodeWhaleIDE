package com.bluewhaleyt.codewhaleide.app.sdk

import com.bluewhaleyt.codewhaleide.sdk.Project
import java.io.File

class DefaultProject(private val file: File) : Project {

    override fun getName(): String {
        return file.name
    }

    override fun getDirectory(): File {
        return file
    }

    override fun getFiles(): List<File> {
        return file.listFiles()?.toList() ?: emptyList()
    }

}