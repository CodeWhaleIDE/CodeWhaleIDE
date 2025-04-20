package com.bluewhaleyt.codewhaleide.app.action;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bluewhaleyt.codewhaleide.sdk.Action;
import com.bluewhaleyt.codewhaleide.sdk.PluginContext;
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.input.InputPanel;
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.input.InputPanelListener;
import com.bluewhaleyt.codewhaleide.sdk.ui.panel.input.InputPanelOptions;

public class JavaTestAction implements Action {

    @Override
    public void onPerform(@NonNull PluginContext context) {
        showInput(context, "Input One", "Enter value for Input One", new Runnable() {
            @Override
            public void run() {
                showInput(context, "Input Two", "Enter value for Input Two", new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Finish", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void showInput(PluginContext context, String title, String placeholder, Runnable onConfirm) {
        InputPanel panel = context.createInputPanel();

        panel.options = new InputPanelOptions();
        panel.options.setPlaceholder(placeholder);
        panel.options.setTitle(title);

        panel.listener = new InputPanelListener() {
            @Override
            public void onConfirm(@NonNull InputPanel panel, @NonNull String value) {
                Toast.makeText(context, "Entered value '" + value + "' for " + panel.options.getTitle(), Toast.LENGTH_SHORT).show();
                panel.dismiss();
                onConfirm.run();
            }
        };
        panel.show();
    }

}
