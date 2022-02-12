enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "watermelon"

dependencyResolutionManagement {
    versionCatalogs {
        create("libraries") {
            from(files(
                "gradle/android.versions.toml",
                "gradle/ink.versions.toml",
                "gradle/kotlin.versions.toml",
            ))
        }
    }
}
include("android")
include("coroutines")
include("standard")
