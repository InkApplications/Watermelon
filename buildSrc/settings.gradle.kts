dependencyResolutionManagement {
    versionCatalogs {
        create("androidLibraries") {
            from(files("../gradle/versions/android.toml"))
        }
        create("kotlinLibraries") {
            from(files("../gradle/versions/kotlin.toml"))
        }
    }
}
