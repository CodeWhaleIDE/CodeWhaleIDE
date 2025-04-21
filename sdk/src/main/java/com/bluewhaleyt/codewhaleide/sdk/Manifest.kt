package com.bluewhaleyt.codewhaleide.sdk

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import java.io.InputStream

interface Manifest {

    val name: String
    val description: String?
    val author: String?
    val actions: List<Action>

    fun getAvailableActions() = actions
        .filter {
            try { true }
            catch (e: ClassNotFoundException) { false }
        }
        .sortedBy { it.label }

    @Serializable
    data class Action(
        val id: String,
        val className: String,
        val label: String,
        val description: String? = null
    ) {
        fun asAction() = Class.forName(className)
            .getConstructor()
            .newInstance() as com.bluewhaleyt.codewhaleide.sdk.Action
    }

    companion object {
        @OptIn(ExperimentalSerializationApi::class)
        val json = Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            allowTrailingComma = true
            allowComments = true
        }

        inline fun <reified T : Manifest> fromInputStream(inputStream: InputStream) =
            json.decodeFromString<T>(inputStream.bufferedReader().use { it.readText() })

    }

}