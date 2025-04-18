import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinAndroid) apply false
}

private fun BaseExtension.configureBaseExtension() {
    compileSdkVersion(libs.versions.compileSdk.get().toInt())
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

fun Project.configureAppExtension() {
    extensions.findByType<AppExtension>()?.run {
        configureBaseExtension()
    }
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
}

fun Project.configureLibraryExtension() {
    extensions.findByType<LibraryExtension>()?.run {
        configureBaseExtension()
        buildTypes {
            release {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
    }
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
}

fun Project.configureCompose() {
    extensions.findByType<BaseExtension>()?.run {
        buildFeatures.compose = true
    }
}

subprojects {
    plugins.withId("com.android.application") { configureAppExtension() }
    plugins.withId("com.android.library") { configureLibraryExtension() }
    plugins.withId("org.jetbrains.kotlin.plugin.compose") { configureCompose() }
}