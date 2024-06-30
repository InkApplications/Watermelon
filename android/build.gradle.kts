plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("ink.publishing")
}

android {
    namespace = "com.inkapplications.android"
    compileSdk = 34

    defaultConfig {
        minSdk = 16
    }
    lint {
        checkReleaseBuilds = false
    }
}

kotlin {
    androidTarget {
        publishAllLibraryVariants()
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.appcompat)
            implementation(libs.kotlinx.coroutines.core)
        }
    }
}
