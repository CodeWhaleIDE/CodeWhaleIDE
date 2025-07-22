plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.kotlinPluginCompose)
}

android {
    namespace = "com.bluewhaleyt.codewhaleide.feature.editor"

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
}

dependencies {
    coreLibraryDesugaring(libs.androidDesugarJdkLibs)
    api(libs.soraEditor)
    api(libs.soraEditorLanguageTextMate)

    implementation(project(":common"))
    implementation(project(":core"))
}