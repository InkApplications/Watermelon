dependencyResolutionManagement {
    versionCatalogs {
        create("androidLibraries") {
            from(files("../gradle/versions/android.toml"))
        }
        create("inkLibraries") {
            from(files("../gradle/versions/ink.toml"))
        }
        create("kotlinLibraries") {
            from(files("../gradle/versions/kotlin.toml"))
        }
    }
}
