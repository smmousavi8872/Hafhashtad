pluginManagement {
    repositories {
//        includeBuild("convention")
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

rootProject.name = "Hafhashtad"
include(":convention")
include(":app")
include(":benchmark")
include(":core:database")
include(":core:model")
include(":core:network")
include(":feature:store")
include(":feature:bookmarked")
include(":core:ui")
include(":core:test-doubles")
include(":sync:work")
include(":core:data:datasource")
include(":core:data:repository")
include(":core:domain")
