plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm()
    js()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                api(Coroutines.common)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                api(Coroutines.core)
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
                api(Coroutines.js)
            }
        }
    }
}
