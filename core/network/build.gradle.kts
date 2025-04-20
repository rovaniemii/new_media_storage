import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.rovaniemi.network"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        val localProperties = Properties().apply {
            load(rootProject.file("local.properties").inputStream())
        }

        val kakaoBaseUrl = localProperties.getProperty("KAKAO_BASE_URL") ?: ""
        val kakaoAuthKey = localProperties.getProperty("KAKAO_API_AUTH_KEY") ?: ""

        buildConfigField("String", "KAKAO_BASE_URL", "\"$kakaoBaseUrl\"")
        buildConfigField("String", "KAKAO_API_AUTH_KEY", "\"$kakaoAuthKey\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":core:model"))

    // Kotlin
    implementation(libs.bundles.kotlin)

    // Retrofit
    implementation(libs.bundles.retrofit)

    // Util
    implementation(libs.bundles.util)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
}