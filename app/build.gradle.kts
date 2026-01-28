plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hiltAndroid)
}

android {
    namespace = "com.task.moviebrowser"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.task.moviebrowser"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":core:ui"))

    implementation(project(":core:network"))
    implementation(project(":core:model"))

    implementation(project(":data"))
    implementation(project(":feature:movies"))
    implementation(project(":feature:movie-details"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.material3.adaptive.navigation.suite)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.compose.ui)
    implementation(libs.compose.runtime)
    implementation(libs.compose.material3)
    implementation(libs.activity.compose)
    debugImplementation(libs.compose.ui.tooling)

    implementation(libs.navigation.compose)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}