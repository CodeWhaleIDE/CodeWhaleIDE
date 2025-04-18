package com.bluewhaleyt.codewhaleide.sdk

import android.content.Context
import android.content.ContextWrapper

abstract class PluginContext(baseContext: Context) : ContextWrapper(baseContext)