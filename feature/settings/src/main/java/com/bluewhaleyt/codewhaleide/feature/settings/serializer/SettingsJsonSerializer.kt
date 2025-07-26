package com.bluewhaleyt.codewhaleide.feature.settings.serializer

import androidx.datastore.core.Serializer
import com.bluewhaleyt.codewhaleide.common.extension.json5
import com.bluewhaleyt.codewhaleide.feature.settings.Settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.OutputStream

class SettingsJsonSerializer : Serializer<Settings> {
    private val json = json5 { coerceInputValues = true }
    private val serializer = Settings.serializer()
    override val defaultValue = Settings()

    override suspend fun readFrom(input: InputStream) = try {
            json.decodeFromString(serializer, input.readBytes().decodeToString())
        } catch (e: Exception) {
            json.decodeFromString(json.encodeToString(defaultValue))
        }

    override suspend fun writeTo(t: Settings, output: OutputStream) = withContext(Dispatchers.IO) {
        output.write(json.encodeToString(serializer, t).encodeToByteArray())
    }
}
