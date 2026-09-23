dependencies {
    implementation(project(":api"))
    implementation(project(":core"))
    implementation(libs.reflexion)
    compileOnly(libs.paper.api)
    compileOnly(libs.log4j.api)
    compileOnly(libs.log4j.core)
    compileOnly(libs.log4j.impl)
}