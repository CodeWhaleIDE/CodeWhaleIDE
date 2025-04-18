package com.bluewhaleyt.codewhaleide.app.action;

import android.view.View;

import androidx.annotation.NonNull;

import com.bluewhaleyt.codewhaleide.common.extension.ContextUtilsKt;
import com.bluewhaleyt.codewhaleide.sdk.Action;
import com.bluewhaleyt.codewhaleide.sdk.PluginContext;
import com.google.android.material.snackbar.Snackbar;

public class SnackbarAction implements Action {

    @Override
    public void onPerform(@NonNull PluginContext context) {
        View rootView = ContextUtilsKt.findActivity(context).findViewById(android.R.id.content);
        Snackbar.make(rootView, "Hello Snackbar", Snackbar.LENGTH_SHORT).show();
    }
}
