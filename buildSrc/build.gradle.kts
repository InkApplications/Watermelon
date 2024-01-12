plugins {
    `kotlin-dsl`
}
repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
}

dependencies {
    implementation(kotlinLibraries.kotlin.gradle)
    implementation(androidLibraries.android.gradle)
    implementation(kotlinLibraries.dokka)
    implementation(kotlinLibraries.kotlinx.binary.compatibility)
}
