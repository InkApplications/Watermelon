enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "watermelon"

dependencyResolutionManagement {
    versionCatalogs {
        create("libraries") {
            from(fileTree("gradle/versions").matching {
                include("*.toml")
            })
        }
    }
}
include("android")
include("coroutines")
include("standard")
