plugins {
    id("library.multiplatform")
    id("com.inkapplications.publishing")
}

kotlin {
    sourceSets {
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}
