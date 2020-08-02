import com.jfrog.bintray.gradle.BintrayExtension

plugins {
    id("project-params")
    id("org.gradle.maven-publish")
    id("com.jfrog.bintray")
}

val isReleaseBuild = !project.version.toString().endsWith("-SNAPSHOT")
val targetRepo = if (isReleaseBuild) "oss-snapshot-local" else "oss-release-local"

val bintrayUsername: String by project
val bintrayPassword: String by project

publishing {
    publications {
        val kotlinComponent = components.findByName("kotlin")
        if (kotlinComponent != null) {
            create<MavenPublication>(project.name) {
                from(kotlinComponent)
                createPom(project.name)
            }
        } else {
            afterEvaluate {
                create<MavenPublication>(project.name) {
                    from(components["release"])
                    createPom(project.name)
                }
            }
        }
    }

    repositories {
        maven {
            url = uri("https://oss.jfrog.org/artifactory/$targetRepo")
            credentials {
                username = bintrayUsername
                password = bintrayPassword
            }
        }
    }
}

bintray {
    user = bintrayUsername
    key = bintrayPassword

    pkg(closureOf<BintrayExtension.PackageConfig> {
        repo = "kotlin"
        name = "kotlin-mindfreak"
        userOrg = "inkapplications"
        vcsUrl = "https://github.com/InkApplications/kotlin-mindfreak.git"
        setLicenses("MIT")
    })
}

fun MavenPublication.createPom(projectName: String) {
    pom {
        name.set(projectName)
        url.set("https://github.com/InkApplications/Extensions")

        licenses {
            license {
                name.set("MIT License")
                url.set("https://opensource.org/licenses/MIT")
            }
        }
    }
}
