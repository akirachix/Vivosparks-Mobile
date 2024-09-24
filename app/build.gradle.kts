plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.services)
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
        dataBinding = true
    }
}


dependencies {
    // Core Android libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Firebase and Google Play services
    implementation(platform("com.google.firebase:firebase-bom:32.0.0")) // Use latest version
    implementation(libs.firebase.auth.ktx)
    implementation(libs.play.services.auth)

    // Retrofit for networking
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Image loading libraries
    implementation(libs.picasso)
    implementation(libs.glide)
    annotationProcessor(libs.compiler)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // Remove unnecessary dependency
    // implementation("com.typesafe.play:play_2.13:2.8.8") // Remove if not needed

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}


