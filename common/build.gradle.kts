plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.kotlinPluginCompose)
}

android {
    namespace = "com.bluewhaleyt.codewhaleide.common"
}

dependencies {
    api(libs.bundles.kotlin)
    api(libs.bundles.androidx)
    api(libs.bundles.compose)
    api(kotlin("reflect"))
}