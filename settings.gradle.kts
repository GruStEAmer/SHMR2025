pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "SHMR"
include(":app")
include(":features")
include(":features:settings")
include(":features:income")
include(":features:expenses")
include(":features:categories")
include(":features:check")
include(":core")
include(":core:network")
include(":core:ui")
include(":core:local")
include(":core:data")
include(":core:domain")
