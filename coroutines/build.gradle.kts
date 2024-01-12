plugins {
    id("multiplatform.tier3")
    id("ink.publishing")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(kotlinLibraries.kotlinx.coroutines.core)
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlinLibraries.kotlinx.coroutines.test)
            }
        }
    }
}
