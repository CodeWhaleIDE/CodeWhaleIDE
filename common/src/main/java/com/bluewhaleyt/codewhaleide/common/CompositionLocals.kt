package com.bluewhaleyt.codewhaleide.common

inline fun <reified T> noLocalProvidedFor(): Nothing =
    noLocalProvidedFor(T::class.java.simpleName)

@PublishedApi
internal fun noLocalProvidedFor(name: String): Nothing =
    error("CompositionLocal $name not present")