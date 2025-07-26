package com.bluewhaleyt.codewhaleide.feature.settings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Settings(
    @SerialName("editor")
    val editor: Editor = Editor()
) {
    @Serializable
    data class Editor(
        @SerialName("lineNumber")
        val lineNumberEnabled: Boolean = SettingKeys.EDITOR_LINE_NUMBER.defaultValue,

        @SerialName("fontSize")
        val fontSize: Float = SettingKeys.EDITOR_FONT_SIZE.defaultValue
    )
}