plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.google.services) apply false
    id("com.google.devtools.ksp") version "1.5.30-1.0.0"

}
