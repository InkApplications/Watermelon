plugins {
    `kotlin-dsl`
}
repositories {
    mavenCentral()
    google()
    jcenter()
}

dependencies {
    implementation("com.android.tools.build:gradle:4.0.0")
    implementation(kotlin("gradle-plugin", "1.3.72"))
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:0.10.1")
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}
