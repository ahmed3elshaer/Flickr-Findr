plugins {
    kotlin("plugin.serialization")
    id("com.android.library")
    kotlin("android")
}

apply("local.gradle")

if (!file("local.gradle").exists()) {
    exec {
        commandLine("sh")
        args = listOf("-c", "cp local.gradle.example local.gradle")
    }
}

android {
    namespace = "com.component.network"
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    compileSdk = (findProperty("android.compileSdk") as String).toInt()

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
    }
    compileOptions {
        // Flag to enable support for the new language APIs
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    dependencies {
        //Network
        implementation(libs.ktor.core)
        implementation(libs.ktor.logging)
        implementation(libs.ktor.client.okhttp)
        implementation(libs.ktor.content.negotiation)
        implementation(libs.ktor.json)
        //Coroutines
        implementation(libs.kotlinx.coroutines.core)
        //JSON
        implementation(libs.kotlinx.serialization.json)
        // DI
    }
}