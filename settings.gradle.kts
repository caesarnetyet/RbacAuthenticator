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

rootProject.name = "Rbac Authenticator"
include(":app")
include(":data")
include(":data:repositories")
include(":data:models")
include(":data:datasources")
include(":data:repositories:remote")
include(":data:repositories:local")
include(":data:datasources:remote")
include(":data:datasources:local")
include(":data:datasources:remote:dto")
include(":core")
include(":core:networking")
include(":core:local")
include(":app:viewmodel")
include(":app:ui")
