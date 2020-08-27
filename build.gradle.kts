import org.gradle.api.tasks.testing.logging.TestExceptionFormat

subprojects {
    group = "com.inkapplications.watermelon"
    version = "1.0.0-SNAPSHOT"

    repositories {
        google()
        mavenCentral()
        jcenter()
    }

    tasks.withType(Test::class) {
        testLogging.exceptionFormat = TestExceptionFormat.FULL
    }
}
