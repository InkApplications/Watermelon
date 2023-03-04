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
    implementation(inkLibraries.ink.publishing)
}
