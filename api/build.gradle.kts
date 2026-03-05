plugins {
    alias(libs.plugins.publish)
}

dependencies {
    compileOnly(libs.paper.api)
}

mavenPublishing {
    coordinates(
        groupId = rootProject.group as String,
        artifactId = "CustomWorldHeight-API",
        version = "0.0.1"
    )
    pom {
        name.set("CustomWorldHeight-API")
        description.set("API for CustomWorldHeight plugin.")
        url.set("https://github.com/Lumine1909/CustomWorldHeight")
        licenses {
            license {
                name.set("LGPL License")
                url.set("https://github.com/Lumine1909/CustomWorldHeight/blob/main/LICENSE")
            }
        }

        developers {
            developer {
                id.set("Lumine1909")
                name.set("Lumine1909")
                email.set("133463833+Lumine1909@users.noreply.github.com")
            }
        }

        scm {
            connection.set("scm:git:git://github.com/Lumine1909/CustomWorldHeight.git")
            developerConnection.set("scm:git:ssh://github.com/Lumine1909/CustomWorldHeight.git")
            url.set("https://github.com/Lumine1909/CustomWorldHeight")
        }
    }

    publishToMavenCentral(true)
    signAllPublications()
}
