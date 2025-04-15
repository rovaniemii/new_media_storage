plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.rovaniemi.ui"
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
    // implementation(libs.bundles.compose)

    // Coil
    implementation(libs.bundles.coil)

    // Lottie
    implementation(libs.bundles.lottie)
}