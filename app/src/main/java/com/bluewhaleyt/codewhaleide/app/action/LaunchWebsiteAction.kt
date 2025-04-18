package com.bluewhaleyt.codewhaleide.app.action

import android.content.Intent
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import com.bluewhaleyt.codewhaleide.sdk.Action
import com.bluewhaleyt.codewhaleide.sdk.PluginContext
import androidx.core.net.toUri
import com.bluewhaleyt.codewhaleide.sdk.ui.quickpick.QuickPick
import com.bluewhaleyt.codewhaleide.sdk.ui.quickpick.QuickPickItem
import com.bluewhaleyt.codewhaleide.sdk.ui.quickpick.QuickPickListener
import com.bluewhaleyt.codewhaleide.sdk.ui.quickpick.QuickPickOptions

class LaunchWebsiteAction : Action {

    override fun onPerform(context: PluginContext) {
        context.showQuickPick(
            id = "quickPick.launch.website",
            items = mapOf(
                "Open with Browser" to null,
                "Open with Chrome Tabs" to "(Recommended)"
            ).map { CustomItem(
                label = it.key,
                description = it.value
            ) },
            options = QuickPickOptions(
                title = "Launch Website",
                placeholder = "Enter website URL",
                matchOnLabel = false
            ),
            listener = object : QuickPickListener<CustomItem> {
                override fun onItemSelected(
                    quickPick: QuickPick<CustomItem>,
                    value: String,
                    index: Int,
                    item: CustomItem
                ) {
                    if (value.isEmpty()) {
                        Toast.makeText(context, "URL is missing", Toast.LENGTH_SHORT).show()
                    } else {
                        try {
                            when (index) {
                                0 -> openWithBrowser(context, value)
                                1 -> openWithChromeTabs(context, value)
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
                        }
                        quickPick.dismiss()
                    }
                }
            }
        )
    }

    private fun openWithBrowser(context: PluginContext, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        context.startActivity(intent)
    }

    private fun openWithChromeTabs(context: PluginContext, url: String) {
        val tabsIntent = CustomTabsIntent.Builder().build()
        tabsIntent.launchUrl(context, url.toUri())
    }

    private data class CustomItem(
        override val label: String,
        override val description: String?
    ) : QuickPickItem

}