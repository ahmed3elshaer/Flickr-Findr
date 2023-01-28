plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.flickr.findr"
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    defaultConfig {
        applicationId = "com.flickr.findr"
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
    }
    testOptions {
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(libs.kotlin.stdlib)
    //Compose
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.iconsExtended)
    //Compose Utils
    implementation(libs.glide.compose)
    implementation(libs.activity.compose)
    implementation(libs.accompanist.permissions)
    implementation(libs.androidx.viewmodel.compose)
    //Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    //DI

    //Navigation


    //test
    testImplementation(libs.junit)
    debugImplementation(libs.androidx.compose.test.manifest)
    androidTestImplementation(libs.androidx.compose.junit)
    androidTestImplementation(libs.ktor.mock)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test:core:1.4.0")
    // Kotlin extensions for androidx.test.core
    androidTestImplementation("androidx.test:core-ktx:1.4.0")


    // To use the JUnit Extension APIs
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    // Kotlin extensions for androidx.test.ext.junit
    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.3")
    androidTestImplementation("androidx.arch.core:core-testing:2.1.0")

    // To use the Truth Extension APIs
    androidTestImplementation("androidx.test.ext:truth:1.4.0")

    // To use the androidx.test.runner APIs
    androidTestImplementation("androidx.test:runner:1.4.0")

    // To use android test orchestrator
    androidTestUtil("androidx.test:orchestrator:1.4.2")
    androidTestImplementation("androidx.test:monitor:1.4.0")

}