import org.gradle.api.tasks.testing.logging.TestExceptionFormat

gradle.startParameter.excludedTaskNames.add("lint")

subprojects {
    repositories {
        mavenCentral()
        google()
    }

    tasks.withType(Test::class) {
        testLogging.exceptionFormat = TestExceptionFormat.FULL
        testLogging.showStandardStreams = true
    }
}

repositories {
    mavenCentral()
    google()
}

tasks.create("zipPublications", Zip::class) {
    from("math/build/repo/")
    from("measures/build/repo/")
    archiveFileName.set("publications.zip")
}
