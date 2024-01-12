plugins {
    id("multiplatform.tier3")
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
