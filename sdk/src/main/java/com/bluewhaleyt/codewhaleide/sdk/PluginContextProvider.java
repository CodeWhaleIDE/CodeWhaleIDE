package com.bluewhaleyt.codewhaleide.sdk;

import androidx.annotation.NonNull;

public interface PluginContextProvider {

    @NonNull
    Workspace getWorkspace();

    @NonNull
    Editor getEditor();

}
