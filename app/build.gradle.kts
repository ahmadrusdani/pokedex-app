plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.amd.pokedexapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.amd.pokedexapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.palette.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.dagger.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson) // Or your preferred converter (Gson, Moshi, etc.)
    implementation(libs.okhttp) // OkHttp is the underlying HTTP client for Retrofit
    implementation(libs.retrofit2.kotlinx.serialization.converter) // If using Kotlin Serialization with Retrofit

    // Coil
    implementation(libs.coil)
    implementation(libs.coil.compose)

    // Room
    implementation(libs.androidx.room.runtime)
    //noinspection KaptUsageInsteadOfKsp
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx) // Kotlin extensions for Room
    implementation(libs.androidx.room.paging)

    // Kotlin Serialization
    implementation(libs.kotlinx.serialization.json)

    // Paging 3
    implementation(libs.androidx.paging.runtime.ktx) // For Kotlin coroutines support
    implementation(libs.androidx.paging.compose) // For Compose

    // Data Store
    implementation(libs.androidx.datastore.preferences)

    // Timber
    implementation(libs.timber)

    // SplashScreen
    implementation(libs.splashscreen)
    implementation(libs.splashscreen)

    // Logger
    implementation(libs.logging.interceptor)
}