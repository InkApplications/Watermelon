import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    kotlin("jvm") version "1.3.72"
}
repositories {
    mavenCentral()
    google()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("gradle-plugin", "1.3.72"))
    implementation("com.android.tools.build:gradle:4.0.1")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:0.10.1")
    implementation("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.5")
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}
