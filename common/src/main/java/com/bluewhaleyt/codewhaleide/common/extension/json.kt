package com.bluewhaleyt.codewhaleide.common.extension

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonBuilder

val json = json()
val json5 = json5()

@OptIn(ExperimentalSerializationApi::class)
fun json5(builder: JsonBuilder.() -> Unit = {}) =
    json {
        isLenient = true
        allowComments = true
        allowTrailingComma = true
        builder()
    }

@OptIn(ExperimentalSerializationApi::class)
fun json(builder: JsonBuilder.() -> Unit = {}) =
    Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        prettyPrintIndent = indent(2)
        builder()
    }

@PublishedApi
internal fun indent(size: Int) = " ".repeat(size)