plugins {
    `kotlin-dsl`
}
repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
}

dependencies {
    implementation(libraries.kotlin.gradle)
    implementation(libraries.android.gradle)
    implementation(libraries.ink.publishing)
}
