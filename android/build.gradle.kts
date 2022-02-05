plugins {
    id("com.android.library")
    kotlin("android")
    id("maven-publish")
    id("signing")
}

repositories {
    google()
}

android {
    compileSdkVersion(31)

    defaultConfig {
        minSdkVersion(16)
    }

    lintOptions {
        tasks.lint {
            enabled = false
        }
    }
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
    }
}
