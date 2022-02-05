plugins {
    id("library.multiplatform")
    id("library.publish")
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
