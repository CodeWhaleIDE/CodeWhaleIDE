@file:JvmMultifileClass
@file:JvmName("ContextUtilsKt")

package com.bluewhaleyt.codewhaleide.common.extension

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity

tailrec fun Context.findActivity(): ComponentActivity = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> error("No activities can be found")
}