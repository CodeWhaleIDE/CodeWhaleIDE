package com.bluewhaleyt.codewhaleide.sdk;

import androidx.annotation.NonNull;

public interface PluginContextProvider {

    @NonNull
    Manifest getManifest();

    @NonNull
    Workspace getWorkspace();

    @NonNull
    Editor getEditor();

}
