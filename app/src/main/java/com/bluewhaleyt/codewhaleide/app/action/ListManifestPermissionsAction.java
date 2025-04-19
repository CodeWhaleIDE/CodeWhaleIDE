package com.bluewhaleyt.codewhaleide.app.action;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bluewhaleyt.codewhaleide.sdk.Action;
import com.bluewhaleyt.codewhaleide.sdk.PluginContext;
import com.bluewhaleyt.codewhaleide.sdk.ui.Icon;
import com.bluewhaleyt.codewhaleide.sdk.ui.quickpick.QuickPick;
import com.bluewhaleyt.codewhaleide.sdk.ui.quickpick.QuickPickItem;
import com.bluewhaleyt.codewhaleide.sdk.ui.quickpick.QuickPickListener;
import com.bluewhaleyt.codewhaleide.sdk.ui.quickpick.QuickPickOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListManifestPermissionsAction implements Action {

    @Override
    public void onPerform(@NonNull PluginContext context) {
        List<String> rawPermissions = Arrays.asList(listPermissions(context));
        List<PermissionItem> permissions = new ArrayList<>();

        for (int i = 0; i < rawPermissions.size(); i++) {
            PermissionItem item = new PermissionItem();
            item.setContext(context);
            item.setLabel(rawPermissions.get(i));
            item.setIcon(android.R.drawable.ic_input_get);
            permissions.add(item);
        }

        if (!permissions.isEmpty()) {
            context.showQuickPick(
                    "quickPick.list.manifest.permissions",
                    permissions,
                    new QuickPickOptions(
                            "Manifest Permissions",
                            "Search permissions"
                    ),
                    new QuickPickListener<PermissionItem>() {
                        @Override
                        public void onItemSelected(@NonNull QuickPick<PermissionItem> quickPick, @NonNull String value, int index, @NonNull PermissionItem item) {

                        }
                    }
            );
        }

    }

    public static String[] listPermissions(Context context) {
        try {
            return context
                    .getPackageManager()
                    .getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS)
                    .requestedPermissions;
        } catch (PackageManager.NameNotFoundException e) {
            return new String[0];
        }
    }

    public static class PermissionItem implements QuickPickItem {

        private Context mContext;

        private String mLabel;
        private int iconResId;

        public void setContext(Context context) {
            this.mContext = context;
        }

        public void setLabel(String label) {
            this.mLabel = label;
        }

        @NonNull
        @Override
        public String getLabel() {
            return mLabel;
        }

        public void setIcon(int resId) {
            this.iconResId = resId;
        }

        @Nullable
        @Override
        public Icon getIcon() {
            return new Icon.Drawable(
                    ContextCompat.getDrawable(mContext, iconResId), null
            );
        }

    }

}
