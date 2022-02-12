enableFeaturePreview("VERSION_CATALOGS")

dependencyResolutionManagement {
    versionCatalogs {
        create("libraries") {
            from(files(
                "../gradle/android.versions.toml",
                "../gradle/ink.versions.toml",
                "../gradle/kotlin.versions.toml",
            ))
        }
    }
}
