plugins {
    id("library.multiplatform")
    id("library.publish")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libraries.kotlinx.coroutines.core)
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(libraries.kotlinx.coroutines.test)
            }
        }
    }
}
