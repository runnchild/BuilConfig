package com.rongc.plugin


import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Delete

class RootPlugin implements Plugin<Project> {

    @Override
    void apply(Project target) {
        target.with {
            def configFile = file("${rootProject.projectDir}/config.gradle")
            if (!configFile.exists()) {
                configFile.createNewFile()
                configFile << "ext {\n" +
                        "    kotlin_version = \"1.3.72\"\n" +
                        "    gradle_version = '4.2.0-alpha07'\n" +
                        "}"
            }
            apply from: configFile.toString()

            repositories {
                maven {
                    url = uri("${rootProject.projectDir.parent}/repo/")
                }
                mavenLocal()
                google()
                jcenter()
            }

            if (target.name == target.rootProject.name) {
                tasks.create("clean", Delete.class) {
                    delete rootProject.buildDir
                }
            }
        }
    }
}