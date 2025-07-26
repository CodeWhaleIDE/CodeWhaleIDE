package com.bluewhaleyt.codewhaleide.feature.settings

object SettingKeys {
    val EDITOR_LINE_NUMBER =
        SettingKey(SettingsGroup.EDITOR, "lineNumber", true)

    val EDITOR_FONT_SIZE =
        SettingKey(SettingsGroup.EDITOR, "fontSize", 18f)
}

data class SettingKey<T> @PublishedApi internal constructor(
    val group: SettingsGroup,
    val serialName: String,
    val defaultValue: T
) {
    val propertyPath
        get() = "${group.id}.$serialName"
}

enum class SettingsGroup(val id: String) {
    EDITOR("editor")
}