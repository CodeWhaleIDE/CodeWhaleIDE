import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.kotlinPluginCompose)
}

android {
    namespace = "com.bluewhaleyt.codewhaleide.sdk"
}

tasks.withType<KotlinCompile>().all {
    compilerOptions {
        freeCompilerArgs = listOf("-Xjvm-default=all")
    }
}

dependencies {
    implementation(libs.bundles.kotlin)
    implementation(libs.bundles.androidx)
    implementation(libs.bundles.compose)
}