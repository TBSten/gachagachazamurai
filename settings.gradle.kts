pluginManagement {
    repositories {
        google()
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

rootProject.name = "GachaGachaZamurai"
include(":app")
include(":ui")
include(":database")
include(":data:prizeitem")
include(":domain")
include(":data:thanks")
include(":file")
include(":feature:settings")
include(":feature:gacha")
include(":data:topImage")
