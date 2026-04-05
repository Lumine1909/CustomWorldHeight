import kotlin.collections.map

plugins {
    java
    alias(libs.plugins.shadow)
    alias(libs.plugins.minotaur)
}

group = "io.github.lumine1909"
version = "2.1.0-SNAPSHOT"
description = "A plugin that allows you modify world's height"

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(project(":api"))
    implementation(project(":core"))
    implementation(project(":plugin"))

    implementation(project(":nms:nms_1_20_5"))
    implementation(project(":nms:nms_1_21"))
    implementation(project(":nms:nms_1_21_3"))
    implementation(project(":nms:nms_1_21_6"))
    implementation(project(":nms:nms_1_21_11"))
    implementation(project(":nms:nms_26_1"))
}


java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}

tasks {
    shadowJar {
        archiveVersion.set(version.toString())
        archiveFileName.set("CustomWorldHeight-${version}+1.20.5-26.1.1.jar")
        archiveClassifier.set("")
        mergeServiceFiles()

        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        subprojects.forEach { sub ->
            dependsOn(sub.tasks.jar)
            from(sub.tasks.jar.flatMap { it.archiveFile }.map { zipTree(it) })
        }
    }
    assemble {
        dependsOn(shadowJar)
    }
    project(":plugin") {
        tasks.withType<ProcessResources> {
            filteringCharset = Charsets.UTF_8.name()
            val props = mapOf(
                "version" to rootProject.version,
                "description" to rootProject.description,
                "apiVersion" to "1.20"
            )
            inputs.properties(props)
            filesMatching("plugin.yml") {
                expand(props)
            }
        }
    }
}

modrinth {
    token.set(project.findProperty("modrinthKey") as? String ?: "")
    projectId.set("customworldheight")
    versionNumber.set(version as String)
    versionName.set("CustomWorldHeight $version")
    versionType.set("beta")
    uploadFile.set(tasks.shadowJar)
    loaders.addAll("bukkit", "paper", "purpur", "folia")

    gameVersions.addAll(generateVersions("1.20", 5, 6))
    gameVersions.addAll(generateVersions("1.21", 0, 11))
    gameVersions.addAll(generateVersions("26.1", 0, 1))
}

fun generateVersions(mm: String, start: Int, end: Int): List<String> = (start..end).map { if (it == 0) mm else "$mm.$it" }

subprojects {
    plugins.apply("java")

    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(25))
    }
}