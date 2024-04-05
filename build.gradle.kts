plugins {
    id("java")
}

group = "io.github.lumine1909"
version = "1.1"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://repo.codemc.io/repository/nms-local/")

}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    compileOnly("io.papermc.paper:paper-server:1.20.4-R0.1-SNAPSHOT")
    implementation("com.mojang:datafixerupper:5.0.28")

}

tasks.test {
    useJUnitPlatform()
}