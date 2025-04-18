package com.bluewhaleyt.codewhaleide.app.sdk

import com.bluewhaleyt.codewhaleide.sdk.Manifest
import kotlinx.serialization.Serializable

@Serializable
data class ApplicationManifest(
    override val actions: List<Manifest.Action>
) : Manifest {

    override val name: String
        get() = "Application"

    override val description: String?
        get() = null

    override val author: String
        get() = "BlueWhaleYT"

}