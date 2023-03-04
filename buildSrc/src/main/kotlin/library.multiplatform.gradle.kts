plugins {
    kotlin("multiplatform")
    id("maven-publish")
}

kotlin {
    jvm()

    iosArm32()
    iosArm64()
    iosX64()
    iosSimulatorArm64()

    watchosArm32()
    watchosArm64()
    watchosX86()
    watchosX64()
    watchosSimulatorArm64()

    tvosArm64()
    tvosX64()
    tvosSimulatorArm64()

    macosX64()
    macosArm64()

    linuxX64()

    mingwX64()

    js(BOTH) {
        nodejs()
        browser()
    }
}

project.extensions.configure(PublishingExtension::class.java) {
    publications {
        withType<MavenPublication> {
            pom {
                name.set("Watermelon ${project.name}")
                description.set("Kotlin Multiplatform Tools")
                url.set("https://github.com/inkapplications/watermelon")
                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://choosealicense.com/licenses/mit/")
                    }
                }
                developers {
                    developer {
                        id.set("reneevandervelde")
                        name.set("Renee Vandervelde")
                        email.set("Renee@InkApplications.com")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/InkApplications/watermelon.git")
                    developerConnection.set("scm:git:ssh://git@github.com:InkApplications/watermelon.git")
                    url.set("https://github.com/InkApplications/watermelon")
                }
            }
        }
    }
}
