rootProject.name = "CustomWorldHeight"

include(":api")
include(":plugin")
include(":core")
include(":nms:nms_1_20_5")
include(":nms:nms_1_21")
include(":nms:nms_1_21_3")
include(":nms:nms_1_21_6")
include(":nms:nms_1_21_11")
include(":nms:nms_26_1")

pluginManagement {
    pluginManagement {
        repositories {
            gradlePluginPortal()
            maven("https://repo.papermc.io/repository/maven-public/")
        }
    }
}