plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.kotlinPluginCompose)
}

android {
    namespace = "com.bluewhaleyt.codewhaleide"

    defaultConfig {
        applicationId = "com.bluewhaleyt.codewhaleide"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
}

dependencies {
    testImplementation(libs.bundles.androidxTest)
    androidTestImplementation(libs.bundles.androidxTest)
    coreLibraryDesugaring(libs.androidDesugarJdkLibs)

    implementation(project(":core"))
    implementation(project(":common"))
    implementation(project(":feature:editor"))
}