package com.bluewhaleyt.codewhaleide.feature.settings

import android.content.Context
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.datastore.core.DataStore
import com.bluewhaleyt.codewhaleide.common.extension.json5
import com.bluewhaleyt.codewhaleide.common.noLocalProvidedFor
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import java.io.File

class SettingsManager(
    private val context: Context,
    private val dataStore: DataStore<Settings>,
    private val fileName: String,
    val settings: Settings,
) {
    private val jsonFilePath
        get() = File(context.filesDir, "datastore/$fileName").absolutePath

    val jsonString
        get() = json5.encodeToString(settings)

    val defaultJsonString
        get() = json5 { encodeDefaults = true }.encodeToString(Settings())

    suspend fun update(transform: (Settings) -> Settings) =
        dataStore.updateData { transform(it) }

    suspend fun update(jsonString: String) =
        update { json5.decodeFromString(jsonString) }

    suspend inline fun <reified T> update(key: SettingKey<T>, value: T) =
        updateByPropertyPath(key.propertyPath, value)

    suspend fun reset() {
        update { Settings() }
        val file = File(jsonFilePath)
        if (file.exists() && file.canRead()) {
            file.delete()
        }
    }
}

val LocalSettingsManager = staticCompositionLocalOf<SettingsManager> {
    noLocalProvidedFor<SettingsManager>()
}

@PublishedApi
internal suspend inline fun <reified T> SettingsManager.updateByPropertyPath(
    propertyPath: String,
    value: T
) {
    val parts = propertyPath.split(".")
    if (parts.isEmpty()) error("Invalid property path: $propertyPath")

    update {
        val jsonObject = json5.encodeToJsonElement(it).jsonObject.toMutableMap()
        updateJsonPath(
            jsonObject = jsonObject,
            parts = parts,
            value = value,
            serializer = { json5.encodeToJsonElement(value) }
        )
        json5.decodeFromJsonElement(
            Settings.serializer(), buildJsonObject {
                jsonObject.forEach { put(it.key, it.value) }
            })
    }
}

@PublishedApi
internal fun <T> updateJsonPath(
    jsonObject: MutableMap<String, JsonElement>,
    parts: List<String>,
    value: T,
    serializer: () -> JsonElement
) {
    if (parts.size == 1) {
        jsonObject[parts[0]] = serializer()
    } else {
        val key = parts[0]
        val nestedObject = jsonObject[key]?.jsonObject?.toMutableMap()
            ?: mutableMapOf<String, JsonElement>().also {
                jsonObject[key] = buildJsonObject { it.forEach { put(it.key, it.value) } }
            }
        updateJsonPath(nestedObject, parts.drop(1), value, serializer)
        jsonObject[key] = buildJsonObject { nestedObject.forEach { put(it.key, it.value) } }
    }
}