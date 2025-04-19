plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.rovaniemi.main"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))

    // Androidx Lifecycle
    implementation(libs.bundles.androidx.lifecycle)

    // Androidx Material3
    implementation(libs.bundles.androidx.material3)

    // Androidx-navigation
    implementation(libs.bundles.androidx.navigation)

    // Compose
    implementation(libs.bundles.androidx.compose)

    // Kotlin
    implementation(libs.bundles.kotlin)

    // Androidx
    implementation(libs.bundles.androidx)

    // Compose
    implementation(platform(libs.compose.bom))

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // Paging3
    implementation(libs.bundles.androidx.paging3)

    // Util
    implementation(libs.bundles.util)
}