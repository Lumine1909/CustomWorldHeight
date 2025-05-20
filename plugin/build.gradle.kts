dependencies {
    implementation(project(":core"))
    implementation(project(":nms:nms_1_21_3"))
    implementation(project(":nms:nms_1_21"))
    implementation(project(":nms:nms_1_20_5"))
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
}

tasks {
    assemble {
        dependsOn(shadowJar)
    }
    shadowJar {
        archiveFileName.set("CustomWorldHeight-${version}-MC-1.20.5-1.21.5.jar")
        minimize()
    }
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name()
        val props = mapOf(
            "name" to rootProject.name,
            "version" to project.version,
            "description" to project.description,
            "apiVersion" to "1.20"
        )
        inputs.properties(props)
        filesMatching("plugin.yml") {
            expand(props)
        }
    }
}
