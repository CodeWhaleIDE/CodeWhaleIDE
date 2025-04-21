package com.bluewhaleyt.codewhaleide.app.sdk

import com.bluewhaleyt.codewhaleide.sdk.Editor
import io.github.rosemoe.sora.widget.CodeEditor

class DefaultEditor(private val editor: CodeEditor) : Editor {

    override fun setText(text: CharSequence?) {
        editor.setText(text)
    }

    override fun getText(): CharSequence {
        return editor.text
    }

}