package com.bluewhaleyt.codewhaleide.common

fun noLocalProvidedFor(name: String): Nothing =
    error("CompositionLocal $name not present")