import org.gradle.api.tasks.testing.logging.TestExceptionFormat

subprojects {
    repositories {
        google()
        jcenter()
    }

    tasks.withType(Test::class) {
        testLogging.exceptionFormat = TestExceptionFormat.FULL
    }
}
