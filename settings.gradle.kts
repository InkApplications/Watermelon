enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "watermelon"

dependencyResolutionManagement {
    versionCatalogs {
        create("libraries") {
            from(files(
                "gradle/kotlin.versions.toml",
            ))
        }
    }
}
//include("android")
include("coroutines")
include("standard")
