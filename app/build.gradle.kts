// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application)
    kotlin("android") version "1.9.21"
}

android {
    namespace = "com.akirachix.investikatrial"
    compileSdk = 34


    defaultConfig {
        applicationId = "com.akirachix.investikatrial"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.appcompat.v151)
    implementation(libs.androidx.core.ktx.v160)
    implementation(libs.androidx.constraintlayout.v210)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.activity)
    implementation(libs.material.v140)
    implementation(libs.material.v161)
    implementation(libs.material.v180)
    implementation(libs.retrofit.v290)
    implementation(libs.converter.gson.v290)
    implementation(libs.kotlinx.coroutines.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit.v113)
    androidTestImplementation(libs.androidx.espresso.core.v340)
}
