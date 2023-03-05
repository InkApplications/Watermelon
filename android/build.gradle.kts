plugins {
    id("com.android.library")
    kotlin("android")
    id("maven-publish")
    id("signing")
    id("org.jetbrains.kotlinx.binary-compatibility-validator")
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 16
    }
    lint {
        checkReleaseBuilds = false
    }
    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

project.tasks.whenTaskAdded {
    if (name == "lint") {
        enabled = false
    }
}

repositories {
    google()
}

dependencies {
    implementation(androidLibraries.androidx.appcompat)
    implementation(kotlinLibraries.kotlinx.coroutines.core)
}

afterEvaluate {
    publishing {
        publications {

            val versionArg = when (project.properties["version"]?.toString()) {
                null, "unspecified", "" -> "1.0-SNAPSHOT"
                else -> project.properties["version"].toString()
            }

            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "com.inkapplications.watermelon"
                artifactId = "android"
                version = versionArg

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
        repositories {
            maven {
                name = "Build"
                url = uri(layout.buildDirectory.dir("repo"))
            }

            val mavenUser: String? by project
            val mavenPassword: String? by project
            if (mavenUser != null && mavenPassword != null) {
                maven {
                    name = "MavenCentral"
                    url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
                    credentials {
                        username = mavenUser
                        password = mavenPassword
                    }
                }
            }
        }
        signing {
            val signingKeyId: String? by project
            val signingKey: String? by project
            val signingPassword: String? by project

            if (signingKey != null) {
                if (signingKeyId != null) {
                    useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)
                } else {
                    useInMemoryPgpKeys(signingKey, signingPassword)
                }
                sign(publishing.publications["release"])
                sign(configurations["archives"])
            }
        }
    }
}
