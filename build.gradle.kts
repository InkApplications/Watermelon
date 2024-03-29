import org.gradle.api.tasks.testing.logging.TestExceptionFormat

subprojects {
    repositories {
        mavenCentral()
    }

    tasks.withType(Test::class) {
        testLogging.exceptionFormat = TestExceptionFormat.FULL
        testLogging.showStandardStreams = true
    }
}

repositories {
    mavenCentral()
}

tasks.create("zipPublications", Zip::class) {
    from("math/build/repo/")
    from("measures/build/repo/")
    archiveFileName.set("publications.zip")
}
