package com.bluewhaleyt.codewhaleide.app.action;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bluewhaleyt.codewhaleide.sdk.Action;
import com.bluewhaleyt.codewhaleide.sdk.PluginContext;

public class ToastAction implements Action {

    @Override
    public void onPerform(@NonNull PluginContext context) {
        Toast.makeText(context, "Hello Toast", Toast.LENGTH_SHORT).show();
    }
}
