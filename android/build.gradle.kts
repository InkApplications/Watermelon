plugins {
    id("com.android.library")
    kotlin("android")
    id("library.publish")
}

repositories {
    google()
}

android {
    compileSdkVersion(31)

    defaultConfig {
        minSdkVersion(16)
    }

    lintOptions {
        tasks.lint {
            enabled = false
        }
    }
}
