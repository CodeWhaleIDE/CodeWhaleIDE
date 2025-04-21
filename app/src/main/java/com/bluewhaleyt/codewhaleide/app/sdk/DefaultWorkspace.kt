package com.bluewhaleyt.codewhaleide.app.sdk

import android.os.Environment
import com.bluewhaleyt.codewhaleide.sdk.Project
import com.bluewhaleyt.codewhaleide.sdk.Workspace
import java.io.File

class DefaultWorkspace : Workspace {

    override fun getProject(): Project {
        return DefaultProject(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS))
    }

}