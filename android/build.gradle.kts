plugins {
    id("com.android.library")
    kotlin("android")
    id("inkapplications.publishing")
}

android {
    compileSdkVersion(29)

    defaultConfig {
        minSdkVersion(16)
    }

    lintOptions {
        tasks.lint {
            enabled = false
        }
    }
}

dependencies {
    implementation(kotlin("stdlib"))
}
